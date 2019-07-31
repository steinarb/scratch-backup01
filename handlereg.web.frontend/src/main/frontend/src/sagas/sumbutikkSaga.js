import { takeLatest, call, put } from 'redux-saga/effects';
import axios from 'axios';
import sumbutikk, { SUMBUTIKK_HENT } from '../reducers/sumbutikk';
const { SUMBUTIKK_MOTTA } = sumbutikk.actions;
import errors from '../reducers/errors';
const { SUMBUTIKK_ERROR } = errors.actions;

function hentSumbutikk() {
    return axios.get('/handlereg/api/statistikk/sumbutikk');
}

function* mottaSumbutikk(action) {
    try {
        const response = yield call(hentSumbutikk);
        const sumbutikk = (response.headers['content-type'] === 'application/json') ? response.data : [];
        yield put(SUMBUTIKK_MOTTA(sumbutikk));
    } catch (error) {
        yield put(SUMBUTIKK_ERROR(error));
    }
}

export default function* sumbutikkSaga() {
    yield takeLatest(SUMBUTIKK_HENT, mottaSumbutikk);
}
