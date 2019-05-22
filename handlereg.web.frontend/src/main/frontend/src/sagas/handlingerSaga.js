import { takeLatest, call, put } from 'redux-saga/effects';
import axios from 'axios';
import handlinger, { HANDLINGER_HENT } from '../reducers/handlinger';
const { HANDLINGER_MOTTA } = handlinger.actions;
import errors from '../reducers/errors';
const { HANDLINGER_ERROR } = errors.actions;

function hentHandlinger(accountid) {
    return axios.get('/handlereg/api/handlinger/' + accountid);
}

function* mottaHandlinger(action) {
    try {
        const accountid = action.payload;
        const response = yield call(hentHandlinger, accountid);
        const handlinger = (response.headers['content-type'] === 'application/json') ? response.data : [];
        yield put(HANDLINGER_MOTTA(handlinger));
    } catch (error) {
        yield put(HANDLINGER_ERROR(error));
    }
}

export default function* handlingerSaga() {
    yield takeLatest(HANDLINGER_HENT, mottaHandlinger);
}
