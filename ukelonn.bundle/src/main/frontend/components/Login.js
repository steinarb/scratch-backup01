import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import LoginErrorMessage from './LoginErrorMessage';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {...props};
    }

    componentDidMount() {
        this.props.onFieldChange({ title: 'Ukelønn login' });
    }

    componentWillReceiveProps(props) {
        this.setState({...props});
    }

    render() {
        let {username, password, loginResponse, onFieldChange, onLogin} = this.state;
        if (loginResponse.roles.length > 0) {
            if (loginResponse.roles[0] === 'administrator') {
                return (<Redirect to="/ukelonn/admin" />);
            }

            return (<Redirect to="/ukelonn/user" />);
        }

        return (
            <div className="Login">
                <form  onSubmit={ e => { e.preventDefault(); }}>
                    <div className="mdl-grid">
                        <div className="mdl-cell mdl-cell--2-col graybox">
                            <label htmlFor="username">Brukernavn:</label>
                        </div>
                        <div className="mdl-cell mdl-cell--2-col">
                            <input type='text' name='username' onChange={(event) => onFieldChange({ username: event.target.value })}></input>
                        </div>
                    </div>
                    <div className="mdl-grid">
                        <div className="mdl-cell mdl-cell--2-col graybox">
                            <label htmlFor="password">Passord:</label>
                        </div>
                        <div className="mdl-cell mdl-cell--2-col">
                            <input type='password' name='password' onChange={(event) => onFieldChange({ password: event.target.value })}/>
                        </div>
                    </div>
                    <div className="mdl-grid">
                        <div className="mdl-cell mdl-cell--4-col">
                            <button className="mdl-button mdl-js-button mdl-button--raised" onClick={() => onLogin(username, password)}>Login</button>
                        </div>
                    </div>
                </form>
                <LoginErrorMessage loginResponse={loginResponse} />
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        username: state.username,
        password: state.password,
        loginResponse: state.loginResponse
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onFieldChange: (changedField) => dispatch({ type: 'UPDATE', data: changedField }),
        onLogin: (username, password) => dispatch({ type: 'LOGIN_REQUEST', username, password }),
    };
};

Login = connect(mapStateToProps, mapDispatchToProps)(Login);

export default Login;
