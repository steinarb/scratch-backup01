import { takeLatest, call, put } from 'redux-saga/effects';
import axios from 'axios';
import sumyearmonth, { SUMYEARMONTH_HENT } from '../reducers/sumyearmonth';
const { SUMYEARMONTH_MOTTA } = sumyearmonth.actions;
import errors from '../reducers/errors';
const { SUMYEARMONTH_ERROR } = errors.actions;

function hentSumyearmonth() {
    return axios.get('/handlereg/api/statistikk/sumyearmonth');
}

function* mottaSumyearmonth(action) {
    try {
        const response = yield call(hentSumyearmonth);
        const sumyearmonth = (response.headers['content-type'] === 'application/json') ? response.data : [];
        yield put(SUMYEARMONTH_MOTTA(sumyearmonth));
    } catch (error) {
        yield put(SUMYEARMONTH_ERROR(error));
    }
}

export default function* sumyearmonthSaga() {
    yield takeLatest(SUMYEARMONTH_HENT, mottaSumyearmonth);
}
