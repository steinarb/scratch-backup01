import React from 'react';
import ReactDOM from 'react-dom';
import App from "./components/App";
import { applyMiddleware, createStore, combineReducers, compose } from 'redux';
import createSagaMiddleware from 'redux-saga';
import { ukelonnReducer } from './reducers';
import { rootSaga } from './sagas';
const sagaMiddleware = createSagaMiddleware();
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import {reducer as notifications} from 'react-notification-system-redux';

const history = createBrowserHistory();
const store = createStore(combineReducers({ukelonn: ukelonnReducer, notifications}), compose(applyMiddleware(sagaMiddleware, routerMiddleware(history)), window.devToolsExtension ? window.devToolsExtension() : f => f));
sagaMiddleware.run(rootSaga);

ReactDOM.render(
    <App store={store} />,
    document.getElementById('root')
);
