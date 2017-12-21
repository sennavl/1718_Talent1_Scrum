import 'babel-polyfill';
import React from 'react';
import ReactDOM from "react-dom";
import {Provider} from 'react-redux';
import {createStore, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';
import promise from 'redux-promise';
import allReducers from './reducers';
import App from './components/App';
import { BrowserRouter } from 'react-router-dom'

import { searchClicked } from './actions/SearchActions';
import { FetchTalents } from './actions/TalentRegisterActions';
import { FetchSession } from './actions/SessionActions';


const store = createStore(
    allReducers,
    compose(
        applyMiddleware(thunk, promise),
        window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    )
);

store.dispatch(FetchSession());

store.dispatch(FetchTalents());
store.dispatch(searchClicked(''));


ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);
