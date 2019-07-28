import { takeLatest, call, put } from 'redux-saga/effects';
import axios from 'axios';
import sistehandel, { SISTEHANDEL_HENT } from '../reducers/sistehandel';
const { SISTEHANDEL_MOTTA } = sistehandel.actions;
import errors from '../reducers/errors';
const { SISTEHANDEL_ERROR } = errors.actions;

function hentSistehandel() {
    return axios.get('/handlereg/api/statistikk/sistehandel');
}

function* mottaSistehandel(action) {
    try {
        const response = yield call(hentSistehandel);
        const sistehandel = (response.headers['content-type'] === 'application/json') ? response.data : [];
        yield put(SISTEHANDEL_MOTTA(sistehandel));
    } catch (error) {
        yield put(SISTEHANDEL_ERROR(error));
    }
}

export default function* sistehandelSaga() {
    yield takeLatest(SISTEHANDEL_HENT, mottaSistehandel);
}
