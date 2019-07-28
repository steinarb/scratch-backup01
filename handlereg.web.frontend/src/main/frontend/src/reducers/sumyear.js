import { createSlice, createAction } from 'redux-starter-kit';

const sumyear = createSlice({
    initialState: [],
    reducers: {
        SUMYEAR_MOTTA: (state, action) => action.payload,
    },
});

export const SUMYEAR_HENT = createAction('SUMYEAR_HENT');

export default sumyear;
