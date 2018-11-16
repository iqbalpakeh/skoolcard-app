import React, { Component } from "react";
import Login from "./containers/login/Login";
import Dashboard from "./containers/dashboard/Dashboard";

class App extends Component {
  render() {
    return (
      <div>
        <Dashboard />
      </div>
    );
  }
}

export default App;
