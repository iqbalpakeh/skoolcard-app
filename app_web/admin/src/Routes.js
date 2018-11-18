import React from "react";
import { Route, Switch } from "react-router-dom";
import AppliedRoute from "./components/appliedroute/AppliedRoute";
import NotFound from "./containers/notfound/NotFound";
import Login from "./containers/login/Login";
import HomePage from "./containers/homepage/HomePage";

export default ({ childProps }) => (
  <Switch>
    <AppliedRoute path="/" exact component={Login} props={childProps} />
    <AppliedRoute
      path="/homepage"
      exact
      component={HomePage}
      props={childProps}
    />
    {/* Finally, catch all unmatched routes */}
    <Route component={NotFound} />
  </Switch>
);
