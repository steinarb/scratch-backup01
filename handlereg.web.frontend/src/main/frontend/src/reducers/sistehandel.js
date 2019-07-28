import { createSlice, createAction } from 'redux-starter-kit';

const sistehandel = createSlice({
    initialState: [],
    reducers: {
        SISTEHANDEL_MOTTA: (state, action) => action.payload,
    },
});

export const SISTEHANDEL_HENT = createAction('SISTEHANDEL_HENT');

export default sistehandel;
