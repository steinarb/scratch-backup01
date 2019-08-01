import React, { Component } from 'react';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import Home from './components/Home';
import Statistikk from './components/Statistikk';
import StatistikkSumbutikk from './components/StatistikkSumbutikk';
import StatistikkHandlingerbutikk from './components/StatistikkHandlingerbutikk';
import StatistikkSistehandel from './components/StatistikkSistehandel';
import StatistikkSumyear from './components/StatistikkSumyear';
import StatistikkSumyearmonth from './components/StatistikkSumyearmonth';
import NyButikk from './components/NyButikk';
import EndreButikk from './components/EndreButikk';
import Login from './components/Login';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route exact path="/handlereg/" component={Home} />
                    <Route exact path="/handlereg/statistikk/sumbutikk" component={StatistikkSumbutikk} />
                    <Route exact path="/handlereg/statistikk/handlingerbutikk" component={StatistikkHandlingerbutikk} />
                    <Route exact path="/handlereg/statistikk/sistehandel" component={StatistikkSistehandel} />
                    <Route exact path="/handlereg/statistikk/sumyearmonth" component={StatistikkSumyearmonth} />
                    <Route exact path="/handlereg/statistikk/sumyear" component={StatistikkSumyear} />
                    <Route exact path="/handlereg/statistikk" component={Statistikk} />
                    <Route exact path="/handlereg/nybutikk" component={NyButikk} />
                    <Route exact path="/handlereg/endrebutikk" component={EndreButikk} />
                    <Route exact path="/handlereg/login" component={Login} />
                </Switch>
            </Router>
        );
    }
}

export default App;
