import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const settings = { timestampsInSnapshots: true };

admin.initializeApp();
admin.firestore().settings(settings);

/**
 * Function handling payment request from client. Input contain information of
 * transaction amount and consumer id.
 *
 * Every transaction will trigger the update of consumer balance. Balance is total accumulated
 * amount of transaction. Transaction is approved is current balance + current amount is equal less
 * then the transaction limit. Otherwise, transaction is rejected.
 *
 * If transaction is approved, function return transaction outcome code as "TC". Otherwise,
 * if transaction is rejected, function return transaction outcome code as "AAC".*
 *
 * How to debug this function:
 * 1. $ cd functions
 * 2. $ npm run-script shell
 * 3. > doPayment({"uid":"GdFOBGtAVfWmlhlCW7fBu2FrTRm1","amount":"50"})
 *
 */
export const doPayment = functions.https.onCall((input, context) => {
  const uid = input.uid;
  const path = "consumers/" + uid;

  // Get consumer id balance:
  return admin
    .firestore()
    .doc(path)
    .get()
    .then(dataSnapshot => {
      const data = dataSnapshot.data();
      const limit = data.limit;
      const balanceStart = data.balance;
      const amount = input.amount;

      let balanceEnd = Number(data.balance);
      let outcome = "AAC";

      if (Number(amount) + Number(balanceStart) <= Number(limit)) {
        outcome = "TC";
        balanceEnd += Number(amount);
      }

      // Update consumer id balance:
      return admin
        .firestore()
        .doc(path)
        .update({ balance: balanceEnd })
        .then(res => {
          console.log("Transaction amount => " + amount);
          console.log("Transaction limit => " + limit);
          console.log("Transaction outcome => " + outcome);
          console.log("Start balance updated => " + balanceStart);
          console.log("End balance updated => " + balanceEnd);
          return { trans_result: outcome };
        })
        .catch(error => {
          console.log("Error while updating new balance: " + error);
          return { trans_result: "AAC" };
        });
    })
    .catch(error => {
      console.error("Error while retrieving user data: " + error);
      return { trans_result: "AAC" };
    });
});
