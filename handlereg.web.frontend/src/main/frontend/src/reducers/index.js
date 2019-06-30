import { combineReducers } from 'redux';
import { emptyUser, emptyUserAndPasswords, emptyRole, emptyPermission } from '../constants';
import login from './login';
import oversikt from './oversikt';
import butikker from './butikker';
import butikk from './butikk';
import valgtButikk from './valgtButikk';
import handlinger from './handlinger';
import nyhandling from './nyhandling';
import errors from './errors';

const rootsReducer = combineReducers({
    login: login.reducer,
    oversikt: oversikt.reducer,
    butikker: butikker.reducer,
    butikk: butikk.reducer,
    handlinger: handlinger.reducer,
    nyhandling: nyhandling.reducer,
    errors: errors.reducer,
});

export default rootsReducer;
