import React, { Component } from "react";
import Login from "./containers/login/Login";
import firebase from "firebase";
import HomePage from "./containers/homepage/HomePage";

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoggedIn: false
    };
  }

  componentDidMount() {
    firebase.auth().onAuthStateChanged(user => {
      if (user) {
        console.log(user);
        this.setState({ isLoggedIn: true });
      } else {
        console.log("Not logged in");
      }
    });
  }

  render() {
    return <div>{this.state.isLoggedIn ? <HomePage /> : <Login />}</div>;
  }
}

export default App;
