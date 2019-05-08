import { combineReducers } from 'redux';
import { emptyUser, emptyUserAndPasswords, emptyRole, emptyPermission } from '../constants';
import loginReducer from './loginReducer';
import oversiktReducer from './oversiktReducer';
import butikkerReducer from './butikkerReducer';
import nybutikkReducer from './nybutikkReducer';
import handlingerReducer from './handlingerReducer';
import nyhandlingReducer from './nyhandlingReducer';
import errorsReducer from './errorsReducer';

const rootsReducer = combineReducers({
    login: loginReducer,
    oversikt: oversiktReducer,
    butikker: butikkerReducer,
    nybutikk: nybutikkReducer,
    handlinger: handlingerReducer,
    nyhandling: nyhandlingReducer,
    errors: errorsReducer,
});

export default rootsReducer;
