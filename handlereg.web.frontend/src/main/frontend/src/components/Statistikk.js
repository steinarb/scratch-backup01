import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkLeft } from './bootstrap/StyledLinkLeft';
import { StyledLinkRight } from './bootstrap/StyledLinkRight';

class Statistikk extends Component {
    render() {
        return (
            <div>
                <StyledLinkLeft to="/handlereg">Opp til matregnskap</StyledLinkLeft>
                <Header>
                    <h1>Statistikk</h1>
                </Header>
                <Container>
                    <StyledLinkRight to="/handlereg/statistikk/sumbutikk">Totalsum pr. butikk</StyledLinkRight>
                    <StyledLinkRight to="/handlereg/statistikk/handlingerbutikk">Antall handlinger i butikk</StyledLinkRight>
                    <StyledLinkRight to="/handlereg/statistikk/sistehandel">Siste handel i butikk</StyledLinkRight>
                    <StyledLinkRight to="/handlereg/statistikk/sumyear">Total handlesum fordelt på år</StyledLinkRight>
                    <StyledLinkRight to="/handlereg/statistikk/sumyearmonth">Total handlesum fordelt på år og måned</StyledLinkRight>
                </Container>
            </div>
        );
    }
}

export default Statistikk;
