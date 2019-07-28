import { createSlice, createAction } from 'redux-starter-kit';

const handlingerbutikk = createSlice({
    initialState: [],
    reducers: {
        HANDLINGERBUTIKK_MOTTA: (state, action) => action.payload,
    },
});

export const HANDLINGERBUTIKK_HENT = createAction('HANDLINGERBUTIKK_HENT');

export default handlingerbutikk;
