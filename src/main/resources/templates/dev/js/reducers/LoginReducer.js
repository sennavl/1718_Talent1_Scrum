import {LOGGED_IN} from '../actions/LoginActions';

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

        default:
            return state
    }
}