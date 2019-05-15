import { createSlice } from 'redux-starter-kit';

const defaultState = {
    loginresultat: {},
};

const login = createSlice({
    initialState: defaultState,
    reducers: {
        USERNAME_ENDRE: (state, action) => {
            const username = action.payload;
            return { ...state, username };
        },
        PASSWORD_ENDRE: (state, action) => {
            const password = action.payload;
            return { ...state, password };
        },
        LOGIN_MOTTA: (state, action) => {
            const loginresultat = action.payload;
            return { ...state, loginresultat };
        },
    },
});

export default login;
