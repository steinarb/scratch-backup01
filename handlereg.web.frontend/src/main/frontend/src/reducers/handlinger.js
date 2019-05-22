import { createSlice, createAction } from 'redux-starter-kit';

const defaultState = [];

const handlinger = createSlice({
    initialState: defaultState,
    reducers: {
        HANDLINGER_MOTTA: (state, action) => action.payload,
    },
});

export const HANDLINGER_HENT = createAction('HANDLINGER_HENT');

export default handlinger;
