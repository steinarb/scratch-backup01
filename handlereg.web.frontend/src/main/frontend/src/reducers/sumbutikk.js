import { createSlice, createAction } from 'redux-starter-kit';

const sumbutikk = createSlice({
    initialState: [],
    reducers: {
        SUMBUTIKK_MOTTA: (state, action) => action.payload,
    },
});

export const SUMBUTIKK_HENT = createAction('SUMBUTIKK_HENT');

export default sumbutikk;
