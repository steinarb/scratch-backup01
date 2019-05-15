import { createAction } from 'redux-starter-kit';
import login from './reducers/login';
import oversikt from './reducers/oversikt';
import butikker from './reducers/butikker';
import nybutikk from './reducers/nybutikk';
import handlinger from './reducers/handlinger';
import nyhandling from './reducers/nyhandling';
import errors from './reducers/errors';

export const { USERNAME_ENDRE, PASSWORD_ENDRE, LOGIN_MOTTA } = login.actions;
export const LOGIN_HENT = createAction('LOGIN_HENT');
export const OVERSIKT_HENT = createAction('OVERSIKT_HENT');
export const { OVERSIKT_MOTTA } = oversikt.actions;
export const BUTIKKER_HENT = createAction('BUTIKKER_HENT');
export const { BUTIKKER_MOTTA } = butikker.actions;
export const NYBUTIKK_REGISTRER = createAction('NYBUTIKK_REGISTRER');
export const { BUTIKKNAVN_ENDRE, NYBUTIKK_LAGRET } = nybutikk.actions;
export const HANDLINGER_HENT = createAction('HANDLINGER_HENT');
export const { HANDLINGER_MOTTA } = handlinger.actions;
export const NYHANDLING_REGISTRER = createAction('NYHANDLING_REGISTRER');
export const { BELOP_ENDRE, BUTIKK_ENDRE, DATO_ENDRE, NYHANDLING_LAGRET } = nyhandling.actions;
export const { LOGIN_ERROR, OVERSIKT_ERROR, HANDLINGER_ERROR, NYHANDLING_ERROR, BUTIKKER_ERROR, NYBUTIKK_ERROR } = errors.actions;
