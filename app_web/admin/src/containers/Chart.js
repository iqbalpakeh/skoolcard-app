/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import { Line } from "react-chartjs-2";

const options = {
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
  getAxis(arr) {
    let axis = { dates: [], totals: [] };
    let amount = 0;
    let dayIterator = -1;
    arr.forEach(data => {
      let date = new Date(Number(data.timestamp)).setHours(0, 0, 0, 0);
      if (!axis.dates.includes(date)) {
        axis.dates.push(date);
        axis.totals.push(amount);
        amount = 0;
        dayIterator++;
      }
      amount += Number(data.amount);
      axis.totals[dayIterator] = amount;
    });
    return axis;
  }

  render() {
    let width = 900;
    let height = 380;
    let axis = this.getAxis(this.props.datas);
    let data = {
      labels: axis.dates.reverse().map(data => {
        return new Date(data).toLocaleDateString();
      }),
      datasets: [{ ...options, ...{ data: axis.totals.reverse() } }]
    };
    return <Line data={data} width={width} height={height} />;
  }
}
