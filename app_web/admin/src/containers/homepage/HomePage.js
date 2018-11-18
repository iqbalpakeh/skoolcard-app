import React, { Component } from "react";
import { Home, File, ShoppingCart, Users, BarChart2 } from "react-feather";
import "./HomePage.css";
import firebase from "firebase";
import Dashboard from "../dashboard/Dashboard";

class HomePage extends Component {
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
      <div>
        <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
          <a className="navbar-brand col-sm-3 col-md-2 mr-0" href="#top">
            Company name
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
        <div className="container-fluid">
          <div className="row">
            <nav className="col-md-2 d-none d-md-block bg-light sidebar">
              <div className="sidebar-sticky">
                <ul className="nav flex-column">
                  <li className="nav-item">
                    <a className="nav-link active" href="#top">
                      <span>
                        <Home className="feather" />
                      </span>
                      Dashboard
                      <span className="sr-only">(current)</span>
                    </a>
                  </li>
                  <li className="nav-item">
                    <a className="nav-link" href="#top">
                      <span>
                        <File className="feather" />
                      </span>
                      Orders
                    </a>
                  </li>
                  <li className="nav-item">
                    <a className="nav-link" href="#top">
                      <span>
                        <ShoppingCart className="feather" />
                      </span>
                      Products
                    </a>
                  </li>
                  <li className="nav-item">
                    <a className="nav-link" href="#top">
                      <span>
                        <Users className="feather" />
                      </span>
                      Customers
                    </a>
                  </li>
                  <li className="nav-item">
                    <a className="nav-link" href="#top">
                      <span>
                        <BarChart2 className="feather" />
                      </span>
                      Reports
                    </a>
                  </li>
                </ul>
              </div>
            </nav>
            <main
              role="main"
              className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4"
            >
              <Dashboard />
            </main>
          </div>
        </div>
      </div>
    );
  }
}

export default HomePage;
