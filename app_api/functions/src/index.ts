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
  const amount = input.amount;
  const path = "consumers/" + uid;
  return admin
    .firestore()
    .doc(path)
    .get()
    .then(snapshot => {
      const data = snapshot.data();
      const limit = data.limit;

      console.log("uid = " + uid);
      console.log("amount = " + amount);
      console.log("limit = " + limit);
      console.log("path = " + path);

      let outcome = "AAC";
      if (Number(amount) <= Number(limit)) {
        outcome = "TC";
      }

      return { trans_result: outcome };
    })
    .catch(error => {
      console.log(error);
    });
});
