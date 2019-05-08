import { createReducer } from 'redux-starter-kit';
import {
    BUTIKKER_MOTTA,
} from '../actiontypes';

const tomButikk = {
    storeId: -1,
    butikknavn: '',
    gruppe: 1,
    rekkefølge: 0,
};

const defaultState = [];

const butikkerReducer = createReducer(defaultState, {
    [BUTIKKER_MOTTA]: (state, action) => {
        const butikker = action.payload;
        butikker.unshift(tomButikk);
        return butikker;
    },
});

export default butikkerReducer;
