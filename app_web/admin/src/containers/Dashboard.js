/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";

import firebase from "firebase/app";
import "firebase/firestore";

import Table from "./Table";

class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = { datas: [] };
  }

  componentDidMount() {
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
        this.setState({ datas: arr });
      })
      .catch(err => {
        console.log("Error getting documents", err);
      });
  }

  render() {
    return (
      <div>
        <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
          <h1 className="h2">Dashboard</h1>
          <div className="btn-toolbar mb-2 mb-md-0">
            <div className="btn-group mr-2">
              <button className="btn btn-sm btn-outline-secondary">
                Share
              </button>
              <button className="btn btn-sm btn-outline-secondary">
                Export
              </button>
            </div>
            <button className="btn btn-sm btn-outline-secondary dropdown-toggle">
              <span data-feather="calendar" />
              This week
            </button>
          </div>
        </div>
        <Table title="Transaction History" datas={this.state.datas} />
      </div>
    );
  }
}

export default Dashboard;
