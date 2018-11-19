import React, { Component } from "react";
import "./Table.css";

class Table extends Component {
  render() {
    const datas = this.props.datas.map(data => (
      <tr key={data.invoice}>
        <td>{data.invoice}</td>
        <td>{data.amount}</td>
        <td>{data.consumer}</td>
        <td>{data.merchant}</td>
        <td>{data.timestamp}</td>
      </tr>
    ));
    return (
      <>
        <h2>{this.props.title}</h2>
        <div className="table-responsive">
          <table className="table table-striped table-sm">
            <thead>
              <tr>
                <th>Invoice</th>
                <th>Amount</th>
                <th>Consumer</th>
                <th>Merchant</th>
                <th>Timestamp</th>
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
