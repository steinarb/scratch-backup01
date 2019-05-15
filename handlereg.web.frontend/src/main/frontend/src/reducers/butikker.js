import { createSlice } from 'redux-starter-kit';

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

export default butikker;
