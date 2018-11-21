/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import "./Table.css";

class Table extends Component {
  timeFormat(timestamp) {
    let time = new Date(Number(timestamp));
    return time.toLocaleDateString() + ", " + time.toLocaleTimeString();
  }

  render() {
    const datas = this.props.datas.map(data => {
      let time = this.timeFormat(data.timestamp);
      return (
        <tr key={data.invoice}>
          <td>{time}</td>
          <td>{data.amount}</td>
          <td>{data.consumer}</td>
          <td>{data.merchant}</td>
          <td>{data.invoice}</td>
        </tr>
      );
    });
    return (
      <>
        <h2>{this.props.title}</h2>
        <div className="table-responsive">
          <table className="table table-striped table-sm">
            <thead>
              <tr>
                <th>Time</th>
                <th>Amount</th>
                <th>Consumer</th>
                <th>Merchant</th>
                <th>Invoice</th>
              </tr>
            </thead>
            <tbody>{datas}</tbody>
          </table>
        </div>
      </>
    );
  }
}

export default Table;
