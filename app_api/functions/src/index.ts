import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
import { DataSnapshot } from "firebase-functions/lib/providers/database";

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
      }

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
