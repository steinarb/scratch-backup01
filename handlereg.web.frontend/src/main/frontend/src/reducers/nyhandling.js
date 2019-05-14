import { createReducer } from 'redux-starter-kit';
import moment from 'moment';
import {
    BELOP_ENDRE,
    BUTIKK_ENDRE,
    DATO_ENDRE,
    NYHANDLING_LAGRET,
} from '../actiontypes';

const defaultState = {
    handletidspunkt: moment(),
    belop: 0.0,
    storeId: -1,
};

const nyhandlingReducer = createReducer(defaultState, {
    [BELOP_ENDRE]: (state, action) => {
        const belop = action.payload;
        return { ...state, belop };
    },
    [BUTIKK_ENDRE]: (state, action) => {
        const storeId = action.payload;
        return { ...state, storeId };
    },
    [DATO_ENDRE]: (state, action) => {
        const handletidspunkt = action.payload;
        return { ...state, handletidspunkt };
    },
    [NYHANDLING_LAGRET]: (state, action) => ({ ...defaultState, handletidspunkt: moment() }),
});

export default nyhandlingReducer;
