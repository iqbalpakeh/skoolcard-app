import React, { Component } from "react";
import "./Table.css";

class Table extends Component {
  constructor(props) {
    super(props);
    this.state = {
      datas: [
        {
          amount: "150",
          consumer: "consumer_1",
          merchant: "merchant_1",
          invoice: "1",
          timestamp: "999999"
        },
        {
          amount: "250",
          consumer: "consumer_1",
          merchant: "merchant_1",
          invoice: "2",
          timestamp: "999999"
        },
        {
          amount: "250",
          consumer: "consumer_1",
          merchant: "merchant_1",
          invoice: "3",
          timestamp: "999999"
        }
      ]
    };
  }
  render() {
    const datas = this.state.datas.map(data => (
      <tr key={data.invoice}>
        <td>{data.amount}</td>
        <td>{data.consumer}</td>
        <td>{data.merchant}</td>
        <td>{data.invoice}</td>
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
                <th>#</th>
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
