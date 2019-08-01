import { takeLatest, call, put } from 'redux-saga/effects';
import axios from 'axios';
import handlingerbutikk, { HANDLINGERBUTIKK_HENT } from '../reducers/handlingerbutikk';
const { HANDLINGERBUTIKK_MOTTA } = handlingerbutikk.actions;
import errors from '../reducers/errors';
const { HANDLINGERBUTIKK_ERROR } = errors.actions;

function hentHandlingerbutikk() {
    return axios.get('/handlereg/api/statistikk/handlingerbutikk');
}

function* mottaHandlingerbutikk(action) {
    try {
        const response = yield call(hentHandlingerbutikk);
        const handlingerbutikk = (response.headers['content-type'] === 'application/json') ? response.data : [];
        yield put(HANDLINGERBUTIKK_MOTTA(handlingerbutikk));
    } catch (error) {
        yield put(HANDLINGERBUTIKK_ERROR(error));
    }
}

export default function* handlingerbutikkSaga() {
    yield takeLatest(HANDLINGERBUTIKK_HENT, mottaHandlingerbutikk);
}
