import { createSlice } from 'redux-starter-kit';

const defaultState = [];

const handlinger = createSlice({
    initialState: defaultState,
    reducers: {
        HANDLINGER_MOTTA: (state, action) => action.payload,
    },
});

export default handlinger;
