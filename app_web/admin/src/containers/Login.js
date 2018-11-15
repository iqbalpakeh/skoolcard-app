/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";

import "./Login.css";
import LoaderButton from "../components/LoaderButton";

/**
 * Login component to handle user login request
 */
export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: false,
      email: "",
      password: ""
    };
  }

  /**
   * Validate user input before continuing with login process
   */
  validateForm() {
    return this.state.email.length > 0 && this.state.password.length > 0;
  }

  /**
   * Handle any change on user input
   */
  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  /**
   * Handle information submission to firebase backend
   */
  handleSubmit = event => {
    event.preventDefault();

    console.log("email = " + this.state.email);
    console.log("password = " + this.state.password);

    this.setState({ isLoading: true });

    // todo: handle submission to firebase login here
    this.dummyLoginProcess().then(() => {
      this.props.userHasAuthenticated(true);
      this.props.history.push("/");
      this.setState({ isLoading: true });
    });
  };

  /**
   * Dummy process to simulate delay time for login process. It simulate
   * 3 second of login process
   */
  dummyLoginProcess() {
    return new Promise(resolve => {
      setTimeout(() => {
        resolve("resolved");
      }, 3000);
    });
  }

  /**
   * Render the component of this component
   */
  render() {
    return (
      <div className="Login">
        <form onSubmit={this.handleSubmit}>
          <FormGroup controlId="email" bsSize="large">
            <ControlLabel>Email</ControlLabel>
            <FormControl
              autoFocus
              type="email"
              value={this.state.email}
              onChange={this.handleChange}
            />
          </FormGroup>
          <FormGroup controlId="password" bsSize="large">
            <ControlLabel>Password</ControlLabel>
            <FormControl
              value={this.state.password}
              onChange={this.handleChange}
              type="password"
            />
          </FormGroup>
          <LoaderButton
            block
            bsSize="large"
            disabled={!this.validateForm()}
            type="submit"
            isLoading={this.state.isLoading}
            text="Login"
            loadingText="Logging in..."
          >
            Login
          </LoaderButton>
        </form>
      </div>
    );
  }
}
