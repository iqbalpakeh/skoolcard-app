/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import * as api from "../Api";
import Table from "./Table";
import { Line } from "react-chartjs-2";

const tableOptions = {
  label: "My First dataset",
  fill: false,
  lineTension: 0.1,
  backgroundColor: "rgba(75,192,192,0.4)",
  borderColor: "rgba(75,192,192,1)",
  borderCapStyle: "butt",
  borderDash: [],
  borderDashOffset: 0.0,
  borderJoinStyle: "miter",
  pointBorderColor: "rgba(75,192,192,1)",
  pointBackgroundColor: "#fff",
  pointBorderWidth: 1,
  pointHoverRadius: 5,
  pointHoverBackgroundColor: "rgba(75,192,192,1)",
  pointHoverBorderColor: "rgba(220,220,220,1)",
  pointHoverBorderWidth: 2,
  pointRadius: 1,
  pointHitRadius: 10
};

export default class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      datas: [],
      chardData: {}
    };
  }

  componentDidMount() {
    api
      .getTransactionHistory()
      .then(arr => {
        let datas = this.extractTableData(arr);
        this.setState({
          datas: arr,
          chardData: {
            labels: datas.dates
              .map(data => {
                return new Date(data).toLocaleDateString();
              })
              .reverse(),
            datasets: [{ ...tableOptions, ...{ data: datas.totals.reverse() } }]
          }
        });
      })
      .catch(err => {
        console.log("Error getting documents", err);
      });
  }

  extractTableData(arr) {
    let amount = 0;
    let previousDate = 0;
    let dates = [];
    let tableDatas = { dates: [], totals: [] };
    arr.forEach(data => {
      var current = new Date(Number(data.timestamp)).setHours(0, 0, 0, 0);
      var previous = new Date(previousDate).setHours(0, 0, 0, 0);
      if (current === previous) {
        amount += Number(data.amount);
        if (!dates.includes(current)) {
          dates.push(current);
          // tableDatas.push({ date: current, total: amount });
          tableDatas.dates.push(current);
          tableDatas.totals.push(amount);
        }
      }
      previousDate = new Date(Number(data.timestamp));
    });
    return tableDatas;
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
        <h2>Transaction activity</h2>
        <Line data={this.state.chardData} width="900" height="380" />
        <Table title="Transaction History" datas={this.state.datas} />
      </div>
    );
  }
}
