import React, { Component } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import { Link } from 'react-router-dom';
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkLeft } from './bootstrap/StyledLinkLeft';
import { StyledLinkRight } from './bootstrap/StyledLinkRight';
import { SISTEHANDEL_HENT } from '../reducers/sistehandel';

class StatistikkSistehandel extends Component {
    componentDidMount() {
        const { hentSistehandel } = this.props;
        hentSistehandel();
    }

    render() {
        const { sistehandel } = this.props;

        return (
            <div>
                <StyledLinkLeft to="/handlereg/statistikk">Tilbake</StyledLinkLeft>
                <Header>
                    <h1>Siste handel gjort i butikk</h1>
                </Header>
                <Container>
                <div className="table-responsive table-sm table-striped">
                    <table className="table">
                        <thead>
                            <tr>
                                <td>Butikk</td>
                                <td>Sist handlet i</td>
                            </tr>
                        </thead>
                        <tbody>
                            {sistehandel.map((sh) =>
                                <tr key={'butikk' + sh.butikk.storeId}>
                                    <td>{sh.butikk.butikknavn}</td>
                                    <td>{moment(sh.date).format("YYYY-MM-DD")}</td>
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
    const sistehandel = state.sistehandel;
    return {
        sistehandel,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        hentSistehandel: () => dispatch(SISTEHANDEL_HENT()),
    };
};

StatistikkSistehandel = connect(mapStateToProps, mapDispatchToProps)(StatistikkSistehandel);
export default StatistikkSistehandel;
