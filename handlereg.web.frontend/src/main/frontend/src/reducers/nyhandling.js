import { createSlice } from 'redux-starter-kit';
import moment from 'moment';

const defaultState = {
    handletidspunkt: moment(),
    belop: 0.0,
    storeId: -1,
};

const nyhandling = createSlice({
    initialState: defaultState,
    reducers: {
        BELOP_ENDRE: (state, action) => {
            const belop = action.payload;
            return { ...state, belop };
        },
        BUTIKK_ENDRE: (state, action) => {
            const storeId = action.payload;
            return { ...state, storeId };
        },
        HANDLINGER_MOTTA: (state, action) => {
            const sistebutikk = [...action.payload].pop();
            const storeId = sistebutikk.storeId;
            return { ...state, storeId };
        },
        DATO_ENDRE: (state, action) => {
            const handletidspunkt = action.payload;
            return { ...state, handletidspunkt };
        },
        NYHANDLING_LAGRET: (state, action) => ({ ...defaultState, handletidspunkt: moment() }),
    },
});

export default nyhandling;
