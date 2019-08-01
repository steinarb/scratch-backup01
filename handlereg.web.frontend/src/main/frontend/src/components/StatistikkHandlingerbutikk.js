import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkLeft } from './bootstrap/StyledLinkLeft';
import { StyledLinkRight } from './bootstrap/StyledLinkRight';
import { HANDLINGERBUTIKK_HENT } from '../reducers/handlingerbutikk';

class StatistikkHandlingerbutikk extends Component {
    componentDidMount() {
        const { hentHandlingerbutikk } = this.props;
        hentHandlingerbutikk();
    }

    render() {
        const { handlingerbutikk } = this.props;

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
                                <td>Butikk</td>
                                <td>Antall handlinger</td>
                            </tr>
                        </thead>
                        <tbody>
                            {handlingerbutikk.map((hb) =>
                                <tr key={'butikk' + hb.butikk.storeId}>
                                    <td>{hb.butikk.butikknavn}</td>
                                    <td>{hb.count}</td>
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
    const handlingerbutikk = state.handlingerbutikk;
    return {
        handlingerbutikk,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        hentHandlingerbutikk: () => dispatch(HANDLINGERBUTIKK_HENT()),
    };
};

StatistikkHandlingerbutikk = connect(mapStateToProps, mapDispatchToProps)(StatistikkHandlingerbutikk);
export default StatistikkHandlingerbutikk;
