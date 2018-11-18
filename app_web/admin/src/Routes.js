import React from "react";
import { Route, Switch } from "react-router-dom";
import AppliedRoute from "./components/appliedroute/AppliedRoute";
import NotFound from "./containers/notfound/NotFound";
import Login from "./containers/login/Login";
import Home from "./containers/home/Home";

export default ({ childProps }) => (
  <Switch>
    <AppliedRoute path="/" exact component={Login} props={childProps} />
    <AppliedRoute path="/home" exact component={Home} props={childProps} />
    {/* Finally, catch all unmatched routes */}
    <Route component={NotFound} />
  </Switch>
);
