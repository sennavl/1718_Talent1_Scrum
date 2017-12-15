import {LOGGED_IN} from '../actions/LoginActions';

const initialState = {

    status: 'NOT_LOGGED_IN'
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'LOGGED_IN':
            return Object.assign({}, state, {
                id: action.json.person_id,
                user: action.json.email,
                status: 'LOGGED_IN'
            });
        default:
            return state
    }
}