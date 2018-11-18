import React, { Component } from "react";
import "./LoaderButton.css";

class LoaderButton extends Component {
  render() {
    return (
      <button className="btn btn-lg btn-primary btn-block" type="submit">
        Sign In
      </button>
    );
  }
}

export default LoaderButton;
