import {FETCHED_USER, FETCHED_TALENTS_USER} from '../actions/ProfileActions';

const initialState = {
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'FETCHED_USER':
            return Object.assign({}, state, {
                name: action.json.person.firstname + " " + action.json.person.lastname,
                status: 'ACTIVE_USER_FETCHED'
            });
        case 'FETCHED_TALENTS_USER':
            return Object.assign({}, state, {
                talents: action.json,
                status: 'ACTIVE_TALENTS_USER_FETCHED'
            });
                break;
        default:
            return state
    }
}
