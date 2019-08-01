import { createSlice, createAction } from 'redux-starter-kit';

const sumyearmonth = createSlice({
    initialState: [],
    reducers: {
        SUMYEARMONTH_MOTTA: (state, action) => action.payload,
    },
});

export const SUMYEARMONTH_HENT = createAction('SUMYEARMONTH_HENT');

export default sumyearmonth;
