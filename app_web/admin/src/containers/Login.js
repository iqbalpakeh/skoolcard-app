/**
 *
 */
import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";

import "./Login.css";
import LoaderButton from "../components/LoaderButton";

/**
 *
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
   *
   */
  validateForm() {
    return this.state.email.length > 0 && this.state.password.length > 0;
  }

  /**
   *
   */
  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  /**
   *
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
   *
   */
  dummyLoginProcess() {
    return new Promise(resolve => {
      setTimeout(() => {
        resolve("resolved");
      }, 5000);
    });
  }

  /**
   *
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
