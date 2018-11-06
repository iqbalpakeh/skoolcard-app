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

  console.log("uid = " + uid);
  console.log("amount = " + amount);
  console.log("path = " + path);

  return admin
    .firestore()
    .doc(path)
    .get()
    .then(snapshot => {
      const data = snapshot.data();
      return { data };
    })
    .catch(error => {
      console.log(error);
    });
});
