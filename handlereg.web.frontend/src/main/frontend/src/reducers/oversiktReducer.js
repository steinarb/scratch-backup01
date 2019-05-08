import { createReducer } from 'redux-starter-kit';
import {
    OVERSIKT_MOTTA,
    NYHANDLING_LAGRET,
} from '../actiontypes';

const defaultState = {
    oversiktresultat: {},
};

const oversiktReducer = createReducer(defaultState, {
    [OVERSIKT_MOTTA]: (state, action) => action.payload,
    [NYHANDLING_LAGRET]: (state, action) => action.payload,
});

export default oversiktReducer;
