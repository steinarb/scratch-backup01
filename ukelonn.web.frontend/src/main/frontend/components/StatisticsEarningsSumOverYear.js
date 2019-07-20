import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
import { findUsernameFromAccountOrQueryParameter } from '../common/account';
import { userIsLoggedIn } from '../common/login';
import {
    ACCOUNT_REQUEST,
    LOGOUT_REQUEST,
} from '../actiontypes';

class StatisticsEarningsSumOverYear extends Component {
    componentDidMount() {
        const username = findUsernameFromAccountOrQueryParameter(this.props);
        this.props.onAccount(username);
    }

    render() {
        if (userIsLoggedIn(this.props)) {
            return <Redirect to="/ukelonn/login" />;
        }

        let { earningsSumOverYear, onLogout } = this.props;


        return (
            <div>
                <h1>Sum av lønn pr år</h1>
                <br/>
                <Link to="/ukelonn/statistics">Tilbake til statistikk</Link><br/>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <td>År</td>
                            <td>Totalt tjent</td>
                        </tr>
                    </thead>
                    <tbody>
                        {earningsSumOverYear.map((sumOverYear) =>
                            <tr key={sumOverYear.year}>
                                <td>{sumOverYear.year}</td>
                                <td>{sumOverYear.sum}</td>
                            </tr>
                        )}
                    </tbody>
                </table>
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
        account: state.account,
        earningsSumOverYear: state.earningsSumOverYear,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onAccount: (username) => dispatch(ACCOUNT_REQUEST(username)),
        onLogout: () => dispatch(LOGOUT_REQUEST()),
    };
};

StatisticsEarningsSumOverYear = connect(mapStateToProps, mapDispatchToProps)(StatisticsEarningsSumOverYear);

export default StatisticsEarningsSumOverYear;
