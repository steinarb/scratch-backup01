import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';

class PerformedJobs extends Component {
    constructor(props) {
        super(props);
        this.state = {...props};
    }

    componentDidMount() {
        this.props.onJobs(this.props.account);
    }

    componentWillReceiveProps(props) {
        this.setState({...props});
    }

    render() {
        let { loginResponse, account, jobs, onLogout } = this.state;
        if (loginResponse.roles.length === 0) {
            return <Redirect to="/ukelonn/login" />;
        }

        return (
            <div>
                <Link to="/ukelonn/">Register betaling</Link>
                <br/>
                <h1>Utførte jobber for {account.firstName}</h1>
                <table className="mdl-data-table mdl-js-data-table transaction-table">
                    <thead>
                        <tr>
                            <td className="mdl-data-table__cell--non-numeric transaction-table-col transaction-table-col1">Dato</td>
                            <td className="mdl-data-table__cell--non-numeric transaction-table-col transaction-table-col-hide-overflow transaction-table-col2">Jobber</td>
                            <td className="transaction-table-col transaction-table-col3">Bel.</td>
                            <td className="mdl-data-table__cell--non-numeric transaction-table-col transaction-table-col4">Bet.</td>
                        </tr>
                    </thead>
                    <tbody>
                        {jobs.map((job) =>
                            <tr key={job.id}>
                                <td className="mdl-data-table__cell--non-numeric transaction-table-col">{job.transactionTime}</td>
                                <td className="mdl-data-table__cell--non-numeric transaction-table-col transaction-table-col-hide-overflow">{job.name}</td>
                                <td className="transaction-table-col">{job.transactionAmount}</td>
                                <td className="mdl-data-table__cell--non-numeric transaction-table-col"><input type="checkbox" checked={job.paidOut}/></td>
                            </tr>
                        )}
                    </tbody>
                </table>
                <br/>
                <br/>
                <button className="mdl-button mdl-js-button mdl-button--raised" onClick={() => onLogout()}>Logout</button>
            </div>
        );
    }
};

const mapStateToProps = state => {
    return {
        loginResponse: state.loginResponse,
        account: state.account,
        jobs: state.jobs,
    };
};
const mapDispatchToProps = dispatch => {
    return {
        onLogout: () => dispatch({ type: 'LOGOUT_REQUEST' }),
        onJobs: (account) => dispatch({ type: 'RECENTJOBS_REQUEST', account: account }),
    };
};

PerformedJobs = connect(mapStateToProps, mapDispatchToProps)(PerformedJobs);

export default PerformedJobs;
