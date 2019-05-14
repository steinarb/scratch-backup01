import { createReducer } from 'redux-starter-kit';
import {
    HANDLINGER_MOTTA,
} from '../actiontypes';

const defaultState = [];

const handlingerReducer = createReducer(defaultState, {
    [HANDLINGER_MOTTA]: (state, action) => action.payload,
});

export default handlingerReducer;
