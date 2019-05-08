import 'regenerator-runtime/runtime';
import { fork, all } from "redux-saga/effects";
import loginSaga from './loginSaga';
import oversiktSaga from './oversiktSaga';
import handlingerSaga from './handlingerSaga';
import nyhandlingSaga from './nyhandlingSaga';
import butikkerSaga from './butikkerSaga';
import nybutikkSaga from './nybutikkSaga';

export default function* rootSaga() {
    yield all([
        fork(loginSaga),
        fork(oversiktSaga),
        fork(handlingerSaga),
        fork(nyhandlingSaga),
        fork(butikkerSaga),
        fork(nybutikkSaga),
    ]);
};
