import React, { Component } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import { Link } from 'react-router-dom';
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkLeft } from './bootstrap/StyledLinkLeft';
import { StyledLinkRight } from './bootstrap/StyledLinkRight';
import { SUMYEARMONTH_HENT } from '../reducers/sumyearmonth';

class StatistikkSumyearmonth extends Component {
    componentDidMount() {
        const { hentSumyearmonth } = this.props;
        hentSumyearmonth();
    }

    render() {
        const { sumyearmonth } = this.props;

        return (
            <div>
                <StyledLinkLeft to="/handlereg/statistikk">Tilbake</StyledLinkLeft>
                <Header>
                    <h1>Handlesum for år og måned</h1>
                </Header>
                <Container>
                <div className="table-responsive table-sm table-striped">
                    <table className="table">
                        <thead>
                            <tr>
                                <td>År</td>
                                <td>Måned</td>
                                <td>Handlebeløp</td>
                            </tr>
                        </thead>
                        <tbody>
                            {sumyearmonth.map((sym) =>
                                <tr key={'year' + sym.year.value}>
                                    <td>{sym.year.value}</td>
                                    <td>{sym.month}</td>
                                    <td>{sym.sum}</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
                </Container>
            </div>
        );
    }
}

const mapStateToProps = state => {
    const sumyearmonth = state.sumyearmonth;
    return {
        sumyearmonth,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        hentSumyearmonth: () => dispatch(SUMYEARMONTH_HENT()),
    };
};

StatistikkSumyearmonth = connect(mapStateToProps, mapDispatchToProps)(StatistikkSumyearmonth);
export default StatistikkSumyearmonth;
