import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';

class PerformedPayments extends Component {
    constructor(props) {
        super(props);
        this.state = {...props};
    }

    componentDidMount() {
        this.props.onPayments(this.props.account);
    }

    componentWillReceiveProps(props) {
        this.setState({...props});
    }

    render() {
        let { loginResponse, account, payments, onLogout } = this.state;
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
                            <td className="mdl-data-table__cell--non-numeric transaction-table-col transaction-table-col-hide-overflow transaction-table-col2">Utbetalinger</td>
                            <td className="transaction-table-col transaction-table-col3b">Beløp</td>
                        </tr>
                    </thead>
                    <tbody>
                        {payments.map((payment) =>
                            <tr key={payment.id}>
                                <td className="mdl-data-table__cell--non-numeric transaction-table-col">{payment.transactionTime}</td>
                                <td className="mdl-data-table__cell--non-numeric transaction-table-col transaction-table-col-hide-overflow">{payment.name}</td>
                                <td className="transaction-table-col">{payment.transactionAmount}</td>
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
        payments: state.payments,
    };
};
const mapDispatchToProps = dispatch => {
    return {
        onLogout: () => dispatch({ type: 'LOGOUT_REQUEST' }),
        onPayments: (account) => dispatch({ type: 'RECENTPAYMENTS_REQUEST', account: account }),
    };
};

PerformedPayments = connect(mapStateToProps, mapDispatchToProps)(PerformedPayments);

export default PerformedPayments;
