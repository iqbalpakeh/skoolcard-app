import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const settings = { timestampsInSnapshots: true };

admin.initializeApp();
admin.firestore().settings(settings);

const db = admin.firestore();

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
 * 3. > doPayment({"uid":"kjypVYRbNIP6jqGONdDaNDzRNb02","amount":"50"})
 *
 */
export const doPayment = functions.https.onCall((input, context) => {
  const userRef = db.doc("consumers/" + input.uid);
  const amount = input.amount;
  const token = input.token;

  return db
    .runTransaction(t => {
      return t.get(userRef).then(doc => {
        console.log(doc.data());

        const balanceStart = doc.data().balance;
        const limit = doc.data().limit;

        let value = Number(doc.data().balance);
        let outcome = "AAC";

        if (Number(amount) + Number(balanceStart) <= Number(limit)) {
          outcome = "TC";
          value += Number(amount);
        }

        const balanceEnd = String(value);

        t.update(userRef, {
          balance: balanceEnd
        });
        // todo : update another location here!!!

        console.log("Token => " + token);
        console.log("Transaction amount => " + amount);
        console.log("Transaction limit => " + limit);
        console.log("Transaction outcome => " + outcome);
        console.log("Start balance updated => " + balanceStart);
        console.log("End balance updated => " + balanceEnd);

        return Promise.resolve({
          trans_result: outcome
        });
      });
    })
    .then(result => {
      console.log("Transaction successfull");
      notifyUser(result.trans_result, token);
      return result;
    })
    .catch(error => {
      console.log(error);
    });
});

/**
 * Send notification to client device based on the
 * transaction outcome
 *
 * @param outcome of transaction
 * @param token of device client
 */
function notifyUser(outcome, token) {
  let message = "Your transaction is rejected";
  if (outcome === "TC") {
    message = "Your transaction is approved";
  }
  admin
    .messaging()
    .sendToDevice(token, {
      data: {
        title: message,
        body: "Check your transaction details"
      }
    })
    .then(function(response) {
      console.log("Successfully sent message:", response);
    })
    .catch(function(error) {
      console.log("Error sending message:", error);
    });
}
