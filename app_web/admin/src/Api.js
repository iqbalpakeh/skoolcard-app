/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import firebase from "firebase/app";
import "firebase/firestore";

function init() {
  // Should we hide the apiKey?
  firebase.initializeApp({
    apiKey: "AIzaSyDrMRJb5J6967gVLmRdgMAnSRPaPZZCBYs",
    authDomain: "project-skoolcard-1-0.firebaseapp.com",
    databaseURL: "https://project-skoolcard-1-0.firebaseio.com",
    projectId: "project-skoolcard-1-0",
    storageBucket: "project-skoolcard-1-0.appspot.com",
    messagingSenderId: "737903700469"
  });
  firebase.firestore().settings({ timestampsInSnapshots: true });
}

function getTransactionHistory() {
  return new Promise(function(resolve, reject) {
    firebase
      .firestore()
      .collection("admin")
      .doc("log")
      .collection("transactions")
      .orderBy("timestamp", "desc")
      .get()
      .then(snapshot => {
        let arr = [];
        snapshot.forEach(doc => {
          console.log(doc.id, "=>", doc.data());
          arr.push(doc.data());
        });
        resolve(arr);
      })
      .catch(err => {
        console.log("Error getting documents", err);
        reject(Error);
      });
  });
}

export { init, getTransactionHistory };
