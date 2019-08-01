import React, { Component } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import { Link } from 'react-router-dom';
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkLeft } from './bootstrap/StyledLinkLeft';
import { StyledLinkRight } from './bootstrap/StyledLinkRight';
import { SUMYEAR_HENT } from '../reducers/sumyear';

class StatistikkSumyear extends Component {
    componentDidMount() {
        const { hentSumyear } = this.props;
        hentSumyear();
    }

    render() {
        const { sumyear } = this.props;

        return (
            <div>
                <StyledLinkLeft to="/handlereg/statistikk">Tilbake</StyledLinkLeft>
                <Header>
                    <h1>Antall handlinger gjort i butikk</h1>
                </Header>
                <Container>
                <div className="table-responsive table-sm table-striped">
                    <table className="table">
                        <thead>
                            <tr>
                                <td>År</td>
                                <td>Handlebeløp</td>
                            </tr>
                        </thead>
                        <tbody>
                            {sumyear.map((sy) =>
                                <tr key={'year' + sy.year.value}>
                                    <td>{sy.year.value}</td>
                                    <td>{sy.sum}</td>
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
    const sumyear = state.sumyear;
    return {
        sumyear,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        hentSumyear: () => dispatch(SUMYEAR_HENT()),
    };
};

StatistikkSumyear = connect(mapStateToProps, mapDispatchToProps)(StatistikkSumyear);
export default StatistikkSumyear;
