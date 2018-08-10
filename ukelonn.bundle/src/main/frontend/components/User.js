import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
import Jobtypes from './Jobtypes';

class User extends Component {
    constructor(props) {
        super(props);
        this.state = {...props};
    }

    componentDidMount() {
        this.props.onAccount(this.props.loginResponse.username);
        this.props.onJobtypeList();
    }

    componentWillReceiveProps(props) {
        this.setState({...props});
    }

    render() {
        let { loginResponse, account, jobtypes, jobtypesMap, performedjob, onJobtypeFieldChange, onRegisterJob, onLogout } = this.state;
        if (loginResponse.roles.length === 0) {
            return <Redirect to="/ukelonn/login" />;
        }

        return (
            <div>
                <h1>Ukelønn for {account.firstName}</h1>
                <div className="hline-bottom"/>
                <div className="hline-bottom">Til gode: { account.balance }</div><br/>
                <form onSubmit={ e => { e.preventDefault(); }}>
                    <div className="mdl-grid mdl-grid--no-spacing hline-bottom">
                        <div className="mdl-cell mdl-cell--2-col">
                            <label htmlFor="jobtype">Velg jobb</label>
                        </div>
                        <div className="mdl-cell mdl-cell--2-col">
                            <Jobtypes id="jobtype" jobtypes={jobtypes} jobtypesMap={jobtypesMap} account={account} performedjob={performedjob} onJobtypeFieldChange={onJobtypeFieldChange} />
                        </div>
                    </div>
                    <div className="mdl-grid mdl-grid--no-spacing hline-bottom">
                        <div className="mdl-cell mdl-cell--2-col">
                            <label htmlFor="amount">Beløp</label>
                        </div>
                        <div className="mdl-cell mdl-cell--2-col">
                            <input id="amount" type="text" value={performedjob.transactionAmount} readOnly="true" /><br/>
                        </div>
                    </div>
                    <div className="mdl-grid mdl-grid--no-spacing hline-bottom">
                        <div className="mdl-cell--2-col">
                            &nbsp;
                        </div>
                        <div className="mdl-cell mdl-cell--2-col">
                            <button className="mdl-button mdl-js-button mdl-button--raised" onClick={() => onRegisterJob(performedjob)}>Registrer jobb</button>
                        </div>
                    </div>
                </form>
                <div className="mdl-grid mdl-grid--no-spacing hline-bottom">
                    <div className="mdl-cell mdl-cell--4-col mdl-cell--hide-phone">
                        &nbsp;
                    </div>
                    <div className="mdl-cell mdl-cell--4-col right-align">
                        <Link to="/ukelonn/performedjobs">Utførte jobber</Link>
                        <i className="material-icons right">arrow_forward_ios</i>
                    </div>
                </div>
                <div className="mdl-grid mdl-grid--no-spacing hline-bottom">
                    <div className="mdl-cell mdl-cell--4-col mdl-cell--hide-phone">
                        &nbsp;
                    </div>
                    <div className="mdl-cell mdl-cell--middle mdl-cell--4-col right-align">
                        <Link to="/ukelonn/performedpayments">Siste utbetalinger til bruker</Link>
                        <i className="material-icons right">arrow_forward_ios</i>
                    </div>
                </div>
                <button className="mdl-button mdl-js-button mdl-button--raised" onClick={() => onLogout()}>Logout</button>
                <hr/>
            </div>
        );
    }
};

const emptyJob = {
    account: { accountId: -1 },
    id: -1,
    transactionName: '',
    transactionAmount: 0.0
};

const mapStateToProps = state => {
    if (!state.jobtypes.includes(emptyJob)) {
        state.jobtypes.unshift(emptyJob);
    }

    return {
        loginResponse: state.loginResponse,
        account: state.account,
        jobtypes: state.jobtypes,
        jobtypesMap: new Map(state.jobtypes.map(i => [i.transactionTypeName, i])),
        performedjob: state.performedjob,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onLogout: () => dispatch({ type: 'LOGOUT_REQUEST' }),
        onAccount: (username) => dispatch({ type: 'ACCOUNT_REQUEST', username }),
        onJobtypeList: () => dispatch({ type: 'JOBTYPELIST_REQUEST' }),
        onJobtypeFieldChange: (selectedValue, jobtypesMap, account, performedjob) => {
            let jobtype = jobtypesMap.get(selectedValue);
            let changedField = {
                performedjob: {
                    transactionTypeId: jobtype.id,
                    transactionName: jobtype.transactionName,
                    transactionAmount: jobtype.transactionAmount,
                    account: account
                }
            };
            dispatch({ type: 'UPDATE', data: changedField });
        },
        onRegisterJob: (performedjob) => dispatch({ type: 'REGISTERJOB_REQUEST', performedjob: performedjob }),
    };
};

User = connect(mapStateToProps, mapDispatchToProps)(User);

export default User;
