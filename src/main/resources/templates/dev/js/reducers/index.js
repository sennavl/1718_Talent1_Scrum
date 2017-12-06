import {combineReducers} from 'redux';
import LoginReducer from './LoginReducer';

/*
 * We combine all reducers into a single object before updated data is dispatched (sent) to store
 * Your entire applications state (store) is just whatever gets returned from all your reducers
 * */

const allReducers = combineReducers({
    Auth: LoginReducer
});

export default allReducers
