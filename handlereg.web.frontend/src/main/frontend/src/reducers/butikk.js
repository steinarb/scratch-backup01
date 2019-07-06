import { createSlice, createAction } from 'redux-starter-kit';
import moment from 'moment';

const defaultState = {
    butikknavn: '',
    gruppe: 2,
};

const nybutikk = createSlice({
    initialState: defaultState,
    reducers: {
        BUTIKKNAVN_ENDRE: (state, action) => {
            const butikknavn = action.payload;
            return { ...state, butikknavn };
        },
        NYBUTIKK_LAGRET: (state, action) => ({ ...defaultState }),
    }
});

export const NYBUTIKK_REGISTRER = createAction('NYBUTIKK_REGISTRER');

export default nybutikk;
