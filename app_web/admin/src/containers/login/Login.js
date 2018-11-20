import React, { Component } from "react";
import logo from "./logo.svg";
import "./Login.css";
import firebase from "firebase/app";
import "firebase/auth";
import LoaderButton from "../loaderbutton/LoaderButton";

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      isLoading: false
    };

    this.handleChangeEmail = this.handleChangeEmail.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChangeEmail(event) {
    this.setState({ email: event.target.value });
  }

  handleChangePassword(event) {
    this.setState({ password: event.target.value });
  }

  handleSubmit(event) {
    console.log(
      "email = " + this.state.email + ", password = " + this.state.password
    );

    this.setState({ isLoading: true });

    firebase
      .auth()
      .signInWithEmailAndPassword(this.state.email, this.state.password)
      .catch(error => {
        console.log("Login error");
        console.log(error);
        this.setState({ isLoading: false });
      });

    event.preventDefault();
  }

  render() {
    return (
      <div className="text-center comp-root">
        <form className="form-signin" onSubmit={this.handleSubmit}>
          <img
            className="mb-4 App-logo"
            src={logo}
            width="200"
            height="200"
            alt="Empty"
          />
          <h1 className="h3 mb-3 font-weight-normal">Please Sign in</h1>
          <label htmlFor="inputEmail" className="sr-only">
            Email address
          </label>
          <input
            type="email"
            id="inputEmail"
            className="form-control"
            placeholder="Email address"
            required
            autoFocus
            value={this.state.email}
            onChange={this.handleChangeEmail}
          />
          <label htmlFor="inputPassword" className="sr-only">
            Password
          </label>
          <input
            type="password"
            id="inputPassword"
            className="form-control"
            placeholder="Password"
            required
            value={this.state.password}
            onChange={this.handleChangePassword}
          />
          <div className="checkbox mb-3">
            <label>
              <input type="checkbox" value="remember-me" /> Remember me
            </label>
          </div>
          <LoaderButton isLoading={this.state.isLoading} />
          <p className="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
        </form>
      </div>
    );
  }
}

export default Login;
