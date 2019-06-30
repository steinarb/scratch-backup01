import { createSlice, createAction } from 'redux-starter-kit';
import butikk from './butikk';

const { VELG_BUTIKK } = butikk.actions;

const defaultState = -1;

const valgtButikk = createSlice({
    initialState: defaultState,
    extraReducers: {
        VELG_BUTIKK: (state, action) => {
            const { indeks } = action.payload;
            return indeks;
        },
    },
});

export default valgtButikk;
