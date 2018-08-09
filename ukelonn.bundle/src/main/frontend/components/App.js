import React, { Component } from 'react';
import { connect, Provider } from 'react-redux';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import Home from "./Home";
import Login from "./Login";
import User from "./User";
import PerformedJobs from "./PerformedJobs";
import PerformedPayments from "./PerformedPayments";
import Admin from "./Admin";
import 'material-design-lite/dist/material.indigo-pink.min.css';
import 'material-design-lite/material.min.js';
import 'material-design-icons/iconfont/material-icons.css';
import '../ukelonn.css';


class App extends Component {
    constructor(props) {
        super(props);
        this.state = {...props};
    }

    componentDidMount() {
        this.props.initialLoginStateRequest();
    }

    render() {
        return(
            <Provider store={this.state.store}>
                <Router>
                    <Switch>
                        <Route exact path="/ukelonn/" component={Home} />
                        <Route path="/ukelonn/login" component={Login} />
                        <Route path="/ukelonn/user" component={User} />
                        <Route path="/ukelonn/performedjobs" component={PerformedJobs} />
                        <Route path="/ukelonn/performedpayments" component={PerformedPayments} />
                        <Route path="/ukelonn/admin" component={Admin} />
                    </Switch>
                </Router>
            </Provider>
        );
    }
}

const mapStateToProps = (state) => {
    return {...state};
};

const mapDispatchToProps = dispatch => {
    return {
        initialLoginStateRequest: () => dispatch({ type: 'INITIAL_LOGIN_STATE_REQUEST' })
    };
};

App = connect(mapStateToProps, mapDispatchToProps)(App);

export default App;
