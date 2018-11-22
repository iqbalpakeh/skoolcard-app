/**
 * Copyright (c) 2018, Progrema Studio. All rights reserved.
 */

import React, { Component } from "react";

export default class LoaderButton extends Component {
  render() {
    return (
      <button className="btn btn-lg btn-primary btn-block" type="submit">
        {this.props.isLoading ? "Loading..." : "Sign In"}
      </button>
    );
  }
}
