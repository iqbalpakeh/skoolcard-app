/**
 *
 */
import { LinkContainer } from "react-router-bootstrap";
import React, { Component, Fragment } from "react";
import { Link, withRouter } from "react-router-dom";
import { Nav, Navbar, NavItem } from "react-bootstrap";
import Routes from "./Routes";
import "./App.css";

/**
 *
 */
class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isAuthenticated: false,
      isAuthenticating: true
    };
  }

  /**
   *
   */
  userHasAuthenticated = authenticated => {
    this.setState({ isAuthenticated: authenticated });
  };

  /**
   *
   */
  handleLogout = event => {
    // todo: handle firebase logout here
    this.userHasAuthenticated(false);
    this.props.history.push("/login");
  };

  /**
   *
   */
  async componentDidMount() {
    // todo: check if user is logged here with firebase
    this.setState({ isAuthenticating: false });
  }

  /**
   *
   */
  render() {
    const childProps = {
      isAuthenticated: this.state.isAuthenticated,
      userHasAuthenticated: this.userHasAuthenticated
    };

    return (
      !this.isAuthenticating && (
        <div className="App container">
          <Navbar fluid collapseOnSelect>
            <Navbar.Header>
              <Navbar.Brand>
                <Link to="/">Scracth</Link>
              </Navbar.Brand>
              <Navbar.Toggle />
            </Navbar.Header>
            <Navbar.Collapse>
              <Nav pullRight>
                {this.state.isAuthenticated ? (
                  <NavItem onClick={this.handleLogout}>Logout</NavItem>
                ) : (
                  <Fragment>
                    <LinkContainer to="/signup">
                      <NavItem>Signup</NavItem>
                    </LinkContainer>
                    <LinkContainer to="/login">
                      <NavItem>Login</NavItem>
                    </LinkContainer>
                  </Fragment>
                )}
              </Nav>
            </Navbar.Collapse>
          </Navbar>
          <Routes childProps={childProps} />
        </div>
      )
    );
  }
}

export default withRouter(App);
