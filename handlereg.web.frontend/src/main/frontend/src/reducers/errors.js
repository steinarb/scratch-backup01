import { createReducer } from 'redux-starter-kit';
import { emptyUser, emptyUserAndPasswords, emptyRole, emptyPermission } from '../constants';
import {
    OVERSIKT_ERROR,
    HANDLINGER_ERROR,
    NYHANDLING_ERROR,
    BUTIKKER_ERROR,
    NYBUTIKK_ERROR,
} from '../actiontypes';

const errorsReducer = createReducer({}, {
    [OVERSIKT_ERROR]: (state, action) => {
        const oversikt = action.payload;
        return { ...state, oversikt };
    },
    [HANDLINGER_ERROR]: (state, action) => {
        const handlinger = action.payload;
        return { ...state, handlinger };
    },
    [NYHANDLING_ERROR]: (state, action) => {
        const nyhandling = action.payload;
        return { ...state, nyhandling };
    },
    [BUTIKKER_ERROR]: (state, action) => {
        const butikker = action.payload;
        return { ...state, butikker };
    },
    [NYBUTIKK_ERROR]: (state, action) => {
        const nybutikk = action.payload;
        return { ...state, nybutikk };
    },
});

export default errorsReducer;
