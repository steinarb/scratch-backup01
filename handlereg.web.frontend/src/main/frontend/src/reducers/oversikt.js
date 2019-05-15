import { createSlice } from 'redux-starter-kit';

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

export default oversikt;
