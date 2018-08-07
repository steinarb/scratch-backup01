import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { connect } from 'react-redux';

let Home = ({loginResponse}) => {
    if (loginResponse.roles.length > 0) {
        if (loginResponse.roles[0] === 'administrator') {
            return <Redirect to="/ukelonn/admin" />;
        }

        return <Redirect to="/ukelonn/user" />;
    }

    return (
        <div className="transition-item">
            <h1>Ukelønn hjem</h1>
        </div>
    );
};

Home.defaultProps = {
    'data-transition-id': 'home-page',
};

const mapStateToProps = state => {
    return {
        loginResponse: state.loginResponse
    };
};

Home = connect(mapStateToProps)(Home);

export default Home;
