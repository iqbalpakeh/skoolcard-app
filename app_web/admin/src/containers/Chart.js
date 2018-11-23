/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import { Line } from "react-chartjs-2";

const tableOptions = {
  label: "Sales transactions",
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

export default class Chart extends Component {
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
          tableDatas.dates.push(current);
          tableDatas.totals.push(amount);
        }
      }
      previousDate = new Date(Number(data.timestamp));
    });
    return tableDatas;
  }

  render() {
    let datas = this.extractTableData(this.props.datas);
    let chartData = {
      labels: datas.dates
        .map(data => {
          return new Date(data).toLocaleDateString();
        })
        .reverse(),
      datasets: [{ ...tableOptions, ...{ data: datas.totals.reverse() } }]
    };
    return <Line data={chartData} width="900" height="380" />;
  }
}
