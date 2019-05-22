import { takeLatest, call, put } from 'redux-saga/effects';
import axios from 'axios';
import nybutikk, { NYBUTIKK_REGISTRER } from '../reducers/nybutikk';
const { NYBUTIKK_LAGRET } = nybutikk.actions;
import { BUTIKKER_HENT } from '../reducers/butikker';
import errors from '../reducers/errors';
const { NYBUTIKK_ERROR } = errors.actions;

function registrerNybutikk(butikk) {
    return axios.post('/handlereg/api/nybutikk', butikk);
}

function* mottaNybutikk(action) {
    try {
        const nybutikk = action.payload;
        const response = yield call(registrerNybutikk, nybutikk);
        const oversikt = (response.headers['content-type'] === 'application/json') ? response.data : [];
        yield put(NYBUTIKK_LAGRET(oversikt));
        const accountid = oversikt.accountid;
        yield put(BUTIKKER_HENT(accountid));
    } catch (error) {
        yield put(NYBUTIKK_ERROR(error));
    }
}

export default function* nybutikkSaga() {
    yield takeLatest(NYBUTIKK_REGISTRER, mottaNybutikk);
}
