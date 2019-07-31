import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import moment from 'moment';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { OVERSIKT_HENT } from '../reducers/oversikt';
import { BUTIKKER_HENT } from '../reducers/butikker';
import nyhandling, { NYHANDLING_REGISTRER } from '../reducers/nyhandling';
const { BELOP_ENDRE, BUTIKK_ENDRE, DATO_ENDRE } = nyhandling.actions;
import { Header } from './bootstrap/Header';
import { Container } from './bootstrap/Container';
import { StyledLinkRight } from './bootstrap/StyledLinkRight';


class Home extends Component {
    componentDidMount() {
        const { hentOversikt, hentButikker } = this.props;
        hentOversikt();
        hentButikker();
    }

    render () {
        const { oversikt, handlinger, butikker, nyhandling, endreBelop, endreButikk, endreDato, onRegistrerHandling } = this.props;
        const favordisfavor = oversikt.balanse >= 0 ? 'favør' : 'disfavør';

        return (
            <div>
                <a className="btn btn-block btn-primary left-align-cell" href="../.."><span className="oi oi-chevron-left" title="chevron left" aria-hidden="true"></span>&nbsp;Gå hjem!</a>
                <Header>
                    <h1>Matregnskap</h1>
                </Header>
                <Container>
                    <p>Hei {oversikt.fornavn}! Totalsummen er for øyeblikket {Math.abs(oversikt.balanse)} i din {favordisfavor}.</p>
                    <p>Dine 5 siste innkjøp, er:</p>
                    <div className="table-responsive table-sm table-striped">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th className="transaction-table-col transaction-table-col1">Dato</th>
                                    <th className="transaction-table-col transaction-table-col2">Beløp</th>
                                    <th className="transaction-table-col transaction-table-col-hide-overflow transaction-table-col3">Butikk</th>
                                </tr>
                            </thead>
                            <tbody>
                                {handlinger.map((handling) =>
                                                <tr key={handling.transactionId}>
                                                    <td className="transaction-table-col">{moment(handling.handletidspunkt).format("YYYY-MM-DD")}</td>
                                                    <td className="transaction-table-col">{handling.belop}</td>
                                                    <td className="transaction-table-col transaction-table-col-hide-overflow">{handling.butikk}</td>
                                                </tr>
                                               )}
                            </tbody>
                        </table>
                    </div>
                    <form onSubmit={ e => { e.preventDefault(); }}>
                        <div className="form-group row">
                            <label htmlFor="amount" className="col-form-label col-5">Nytt beløp</label>
                            <div className="col-7">
                                <input id="amount" className="form-control" type="text" value={nyhandling.belop} onChange={e => endreBelop(e.target.value)} />
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="jobtype" className="col-form-label col-5">Velg butikk</label>
                            <div className="col-7">
                                <select value={nyhandling.storeId} onChange={e => endreButikk(e.target.value)}>
                                    {butikker.map(butikk => <option key={butikk.storeId} value={butikk.storeId}>{butikk.butikknavn}</option>)}
                                </select>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="date" className="col-form-label col-5">Dato</label>
                            <div className="col-7">
                                <DatePicker selected={nyhandling.handletidspunkt} dateFormat="YYYY-MM-DD" onChange={(selectedValue) => endreDato(selectedValue)} readOnly={true} />
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="col-5"/>
                            <div className="col-7">
                                <button className="btn btn-primary" disabled={nyhandling.belop <= 0} onClick={() => onRegistrerHandling(nyhandling, oversikt.brukernavn)}>Registrer handling</button>
                            </div>
                        </div>
                    </form>
                </Container>
                <Container>
                    <StyledLinkRight to="/handlereg/statistikk">Statistikk</StyledLinkRight>
                    <StyledLinkRight to="/handlereg/nybutikk">Ny butikk</StyledLinkRight>
                    <StyledLinkRight to="/handlereg/endrebutikk">Endre butikk</StyledLinkRight>
                </Container>
            </div>
        );
    }
}

const mapStateToProps = state => {
    const { oversikt, handlinger, butikker, nyhandling } = state;
    return {
        oversikt,
        handlinger,
        butikker,
        nyhandling,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        hentOversikt: () => dispatch(OVERSIKT_HENT()),
        hentButikker: () => dispatch(BUTIKKER_HENT()),
        endreBelop: (belop) => dispatch(BELOP_ENDRE(belop)),
        endreButikk: (butikk) => dispatch(BUTIKK_ENDRE(butikk)),
        endreDato: (dato) => dispatch(DATO_ENDRE(dato)),
        onRegistrerHandling: (handling, username) => dispatch(NYHANDLING_REGISTRER({ ...handling, username })),
    };
};

Home = connect(mapStateToProps, mapDispatchToProps)(Home);
export default Home;
