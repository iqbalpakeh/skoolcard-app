import * as functions from "firebase-functions";

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
});

export const helloAgain = functions.https.onRequest((request, response) => {
  response.send("Hello again from Firebase!");
});

export const doPayment = functions.https.onCall((data, context) => {
  console.log("doPayment() is called");
  console.log("data = " + data.text);
  return {
    response: "response from doPayment()"
  };
});
