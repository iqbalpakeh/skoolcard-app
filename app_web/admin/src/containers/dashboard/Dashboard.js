import React, { Component } from "react";
import "./Dashboard.css";
import firebase from "firebase/app";
import Table from "../table/Table";

class Dashboard extends Component {
  componentDidMount() {
    console.log("componentDidMount()");
    firebase
      .firestore()
      .collection("admin")
      .doc("log")
      .collection("transactions")
      .get()
      .then(snapshot => {
        snapshot.forEach(doc => {
          console.log(doc.id, "=>", doc.data());
        });
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
        <canvas class="my-4" id="myChart" width="900" height="380" />
        <Table />
      </div>
    );
  }
}

export default Dashboard;
