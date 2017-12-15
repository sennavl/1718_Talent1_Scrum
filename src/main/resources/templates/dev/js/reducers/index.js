import {combineReducers} from 'redux';
import LoginReducer from './LoginReducer';
import RegisterReducer from './RegisterReducer';
import TalentRegistrationReducer from './TalentRegistrationReducer';

/*
 * We combine all reducers into a single object before updated data is dispatched (sent) to store
 * Your entire applications state (store) is just whatever gets returned from all your reducers
 * */

const allReducers = combineReducers({
    Auth: LoginReducer,
    Register: RegisterReducer,
    TalentRegister: TalentRegistrationReducer
});

export default allReducers
