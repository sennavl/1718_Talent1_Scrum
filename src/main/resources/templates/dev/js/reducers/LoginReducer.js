import {LOGGED_IN, LOGGED_OUT} from '../actions/LoginActions';

const initialState = {
    status: 'NOT_LOGGED_IN',
    id: ''
};

export default function (state = initialState, action) {
    switch (action.type) {
        case 'LOGGED_IN':
            return Object.assign({}, state, {
                id: action.json,
                status: 'LOGGED_IN',
            });
        case 'NOT_LOGGED_IN':
            return Object.assign({}, state, {
                status: 'NOT_LOGGED_IN',
                id: '',
            });
        case 'LOGGED_OUT':
            return Object.assign({}, state, {
                status: 'LOGGED_OUT',
                id: '',
            });

        default:
            return state
    }
}
