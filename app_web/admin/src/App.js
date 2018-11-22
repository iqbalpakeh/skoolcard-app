/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import * as api from "./Api";
import Login from "./containers/Login";
import HomePage from "./containers/HomePage";
import Loading from "./containers/Loading";

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoggedIn: false,
      isLoading: true
    };
  }

  componentDidMount() {
    api.checkLoggedIn(user => {
      this.setState({ isLoading: false });
      if (user) {
        console.log("Logged in: " + user);
        this.setState({ isLoggedIn: true });
      } else {
        console.log("Not logged in");
        this.setState({ isLoggedIn: false });
      }
    });
  }

  render() {
    return this.state.isLoading ? (
      <Loading />
    ) : (
      <div>{this.state.isLoggedIn ? <HomePage /> : <Login />}</div>
    );
  }
}
