import React from 'react';
import ReactDOM from 'react-dom';
import App from "./components/App";
import { applyMiddleware, createStore, compose } from 'redux';
import createSagaMiddleware from 'redux-saga';
import { ukelonnReducer } from './reducers';
import { rootSaga } from './sagas';
const sagaMiddleware = createSagaMiddleware();
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';

const history = createBrowserHistory();
const store = createStore(ukelonnReducer, compose(applyMiddleware(sagaMiddleware, routerMiddleware(history)), window.devToolsExtension ? window.devToolsExtension() : f => f));
sagaMiddleware.run(rootSaga);

console.log('Her(1)');


if (Notification) {
    console.log('Her(2)');
    Notification.requestPermission().then(function(result) {
        store.dispatch({ type: 'UPDATE', data: { notificationAvailable: true } });
        console.log(result);
    });
}

console.log('Her(3)');


ReactDOM.render(
    <App store={store} />,
    document.getElementById('root')
);
