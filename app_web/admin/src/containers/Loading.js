/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";
import "./css/Loading.css";

export default class Loading extends Component {
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
