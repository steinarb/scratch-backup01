import { createSlice, createAction } from 'redux-starter-kit';

const tomButikk = {
    storeId: -1,
    butikknavn: '',
    gruppe: 1,
    rekkefølge: 0,
};

const defaultState = [];

const butikker = createSlice({
    initialState: [],
    reducers: {
        BUTIKKER_MOTTA: (state, action) => {
            const butikker = action.payload;
            butikker.unshift(tomButikk);
            return butikker;
        }
    },
});

export const BUTIKKER_HENT = createAction('BUTIKKER_HENT');

export default butikker;
