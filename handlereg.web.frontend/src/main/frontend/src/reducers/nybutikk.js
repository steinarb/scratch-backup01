import { createReducer } from 'redux-starter-kit';
import moment from 'moment';
import {
    BUTIKKNAVN_ENDRE,
    NYBUTIKK_LAGRET,
} from '../actiontypes';

const defaultState = {
    butikknavn: '',
    gruppe: 2,
};

const nybutikkReducer = createReducer(defaultState, {
    [BUTIKKNAVN_ENDRE]: (state, action) => {
        const butikknavn = action.payload;
        return { ...state, butikknavn };
    },
    [NYBUTIKK_LAGRET]: (state, action) => ({ ...defaultState }),
});

export default nybutikkReducer;
