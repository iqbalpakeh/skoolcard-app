/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React from "react";
import ReactDOM from "react-dom";

import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";

import firebase from "firebase/app";
import "firebase/firestore";

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

ReactDOM.render(<App />, document.getElementById("root"));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
