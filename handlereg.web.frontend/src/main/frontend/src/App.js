import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import {
    LOGIN_STATUS,
} from './actiontypes';
import Home from './components/Home';
import NyButikk from './components/NyButikk';
import Login from './components/Login';

class App extends Component {
    componentDidMount() {
        const { sjekkLoginStatus } = this.props;
        sjekkLoginStatus();
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route exact path="/handlereg/" component={Home} />
                    <Route exact path="/handlereg/nybutikk" component={NyButikk} />
                    <Route exact path="/handlereg/login" component={Login} />
                </Switch>
            </Router>
        );
    }
}


const mapDispatchToProps = dispatch => {
    return {
        sjekkLoginStatus: () => dispatch(LOGIN_STATUS()),
    };
};

App = connect(null, mapDispatchToProps)(App);
export default App;
