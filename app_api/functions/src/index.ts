import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const settings = { timestampsInSnapshots: true };

admin.initializeApp();
admin.firestore().settings(settings);

// How to debug:
// 1. $ cd functions
// 2. $ npm run-script shell
// 3. > doPayment({"uid":"GdFOBGtAVfWmlhlCW7fBu2FrTRm1","amount":"50"})

export const doPayment = functions.https.onCall((input, context) => {
  const uid = input.uid;
  const path = "consumers/" + uid;

  return admin
    .firestore()
    .doc(path)
    .get()
    .then(snapshot => {
      const data = snapshot.data();
      const limit = data.limit;
      const balanceStart = data.balance;
      const amount = input.amount;

      let balanceEnd = Number(data.balance);
      let outcome = "AAC";

      if (Number(amount) + Number(balanceStart) <= Number(limit)) {
        outcome = "TC";
        balanceEnd += Number(amount);
        admin
          .firestore()
          .doc(path)
          .update({ balance: balanceEnd })
          .then(res => {
            console.log("New balance updated => " + balanceEnd);
          })
          .catch(error => {
            console.log(error);
          });
      }
      console.log("uid = " + uid);
      console.log("amount = " + amount);
      console.log("limit = " + limit);
      console.log("path = " + path);
      console.log("balance (before) = " + balanceStart);
      console.log("balance (After) = " + balanceEnd);

      return { trans_result: outcome };
    })
    .catch(error => {
      console.log(error);
    });
});
