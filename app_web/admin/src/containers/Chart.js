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
    let tableDatas = { dates: [], totals: [] };
    let amount = 0;
    let iteration = -1;
    arr.forEach(data => {
      let date = new Date(Number(data.timestamp)).setHours(0, 0, 0, 0);
      if (!tableDatas.dates.includes(date)) {
        tableDatas.dates.push(date);
        tableDatas.totals.push(amount);
        amount = 0;
        iteration++;
      }
      amount += Number(data.amount);
      tableDatas.totals[iteration] = amount;
      console.log(
        "amount = " + amount + ", date = " + new Date(date).toLocaleDateString()
      );
    });
    console.log(tableDatas);
    return tableDatas;
  }

  render() {
    let chartWidth = 900;
    let chartHeight = 380;
    let datas = this.extractTableData(this.props.datas);
    let chartData = {
      labels: datas.dates.map(data => {
        return new Date(data).toLocaleDateString();
      }),
      datasets: [{ ...tableOptions, ...{ data: datas.totals } }]
    };
    return <Line data={chartData} width={chartWidth} height={chartHeight} />;
  }
}
