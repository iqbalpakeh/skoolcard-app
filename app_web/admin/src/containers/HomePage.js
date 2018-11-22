/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import { Home, File, ShoppingCart, Users, BarChart2 } from "react-feather";

import "./css/HomePage.css";

import Dashboard from "./Dashboard";
import Orders from "./Orders";
import Navbar from "./Navbar";

class HomePage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isDashboard: true,
      isOrders: false,
      statusDashboard: "nav-link active",
      statusOrders: "nav-link"
    };

    this.showDashboard = this.showDashboard.bind(this);
    this.showOrders = this.showOrders.bind(this);
  }

  showDashboard() {
    this.setState({
      isDashboard: true,
      isOrders: false,
      statusDashboard: "nav-link active",
      statusOrders: "nav-link"
    });
  }

  showOrders() {
    this.setState({
      isDashboard: false,
      isOrders: true,
      statusDashboard: "nav-link",
      statusOrders: "nav-link active"
    });
  }

  render() {
    let main;
    if (this.state.isDashboard) {
      main = <Dashboard />;
    } else if (this.state.isOrders) {
      main = <Orders />;
    } else {
      console.log("Not defined");
    }
    return (
      <div>
        <Navbar />
        <div className="container-fluid">
          <div className="row">
            <nav className="col-md-2 d-none d-md-block bg-light sidebar">
              <div className="sidebar-sticky">
                <ul className="nav flex-column">
                  <li className="nav-item">
                    <a
                      className={this.state.statusDashboard}
                      href="#top"
                      onClick={this.showDashboard}
                    >
                      <span>
                        <Home className="feather" />
                      </span>
                      Dashboard
                      <span className="sr-only">(current)</span>
                    </a>
                  </li>
                  <li className="nav-item">
                    <a
                      className={this.state.statusOrders}
                      href="#top"
                      onClick={this.showOrders}
                    >
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
              {main}
            </main>
          </div>
        </div>
      </div>
    );
  }
}

export default HomePage;
