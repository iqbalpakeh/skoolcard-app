import React, { Component } from "react";
import "./Loading.css";

class Loading extends Component {
  render() {
    return (
      <div className="wrap">
        <div className="loading">
          <div className="bounceball" />
          <div className="text">NOW LOADING</div>
        </div>
      </div>
    );
  }
}

export default Loading;
