import React, { Component } from "react";
import firebase from "firebase";

class Navbar extends Component {
  constructor(props) {
    super(props);
    this.handleSignout = this.handleSignout.bind(this);
  }

  handleSignout() {
    firebase
      .auth()
      .signOut()
      .then(() => {})
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a className="navbar-brand col-sm-3 col-md-2 mr-0" href="#top">
          Skoolcard Admin
        </a>
        <input
          className="form-control form-control-dark w-100"
          type="text"
          placeholder="Search"
          aria-label="Search"
        />
        <ul className="navbar-nav px-3">
          <li className="nav-item text-nowrap">
            <a className="nav-link" href="#top" onClick={this.handleSignout}>
              Sign out
            </a>
          </li>
        </ul>
      </nav>
    );
  }
}

export default Navbar;
