import { createSlice } from 'redux-starter-kit';

const errors = createSlice({
    initialState: {},
    reducers: {
        OVERSIKT_ERROR: (state, action) => {
            const oversikt = action.payload;
            return { ...state, oversikt };
        },
        HANDLINGER_ERROR: (state, action) => {
            const handlinger = action.payload;
            return { ...state, handlinger };
        },
        NYHANDLING_ERROR: (state, action) => {
            const nyhandling = action.payload;
            return { ...state, nyhandling };
        },
        BUTIKKER_ERROR: (state, action) => {
            const butikker = action.payload;
            return { ...state, butikker };
        },
        NYBUTIKK_ERROR: (state, action) => {
            const nybutikk = action.payload;
            return { ...state, nybutikk };
        },
    },
});

export default errors;
