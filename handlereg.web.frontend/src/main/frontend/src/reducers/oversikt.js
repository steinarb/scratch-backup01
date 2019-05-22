import { createSlice, createAction } from 'redux-starter-kit';

const defaultState = {
    oversiktresultat: {},
};

const oversikt = createSlice({
    initialState: defaultState,
    reducers: {
        OVERSIKT_MOTTA: (state, action) => action.payload,
        NYHANDLING_LAGRET: (state, action) => action.payload,
    },
});

export const OVERSIKT_HENT = createAction('OVERSIKT_HENT');

export default oversikt;
