import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkLeft } from './bootstrap/StyledLinkLeft';
import butikk, { NYBUTIKK_REGISTRER } from '../reducers/butikk';
const { BUTIKKNAVN_ENDRE } = butikk.actions;

function NyButikk(props) {
    const { butikk, endreNavn, onRegistrerNyButikk } = props;

    return (
        <div>
            <StyledLinkLeft to="/handlereg">Opp til matregnskap</StyledLinkLeft>
            <Header>
                <h1>Ny butikk</h1>
            </Header>
            <Container>
                <form onSubmit={ e => { e.preventDefault(); }}>
                    <div className="form-group row">
                        <label htmlFor="amount" className="col-form-label col-5">Ny butikk</label>
                        <div className="col-7">
                            <input id="amount" className="form-control" type="text" value={butikk.butikknavn} onChange={e => endreNavn(e.target.value)} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <div className="col-5"/>
                        <div className="col-7">
                            <button className="btn btn-primary" onClick={() => onRegistrerNyButikk(butikk)}>Legg til butikk</button>
                        </div>
                    </div>
                </form>
            </Container>
        </div>
    );
}

function mapStateToProps(state) {
    const { butikk } = state;
    return {
        butikk,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        endreNavn: (butikknavn) => dispatch(BUTIKKNAVN_ENDRE(butikknavn)),
        onRegistrerNyButikk: (nybutikk) => dispatch(NYBUTIKK_REGISTRER(nybutikk)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(NyButikk);
