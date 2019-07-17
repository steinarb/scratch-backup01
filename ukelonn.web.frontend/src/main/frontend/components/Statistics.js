import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
import {
    LOGOUT_REQUEST,
} from '../actiontypes';

class Statistics extends Component {
    render() {
        let { haveReceivedResponseFromLogin, loginResponse, onLogout } = this.props;

        if (haveReceivedResponseFromLogin && loginResponse.roles.length === 0) {
            return <Redirect to="/ukelonn/login" />;
        }

        return (
            <div>
                <h1>Jobbstatistikk</h1>
                <br/>
                <Link to="/ukelonn/">Tilbake</Link><br/>
                <Link to="/ukelonn/statistics/earnings/sumoveryear">Sum av beløp tjent pr. år</Link><br/>
                <Link to="/ukelonn/statistics/earnings/sumovermonth">Sum av beløp tjent pr. år og måned</Link><br/>
                <br/>
                <button onClick={() => onLogout()}>Logout</button>
                <br/>
                <a href="../../..">Tilbake til topp</a>
            </div>
        );
    };
};

const mapStateToProps = state => {
    return {
        haveReceivedResponseFromLogin: state.haveReceivedResponseFromLogin,
        loginResponse: state.loginResponse,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onLogout: () => dispatch(LOGOUT_REQUEST()),
    };
};

Statistics = connect(mapStateToProps, mapDispatchToProps)(Statistics);

export default Statistics;
