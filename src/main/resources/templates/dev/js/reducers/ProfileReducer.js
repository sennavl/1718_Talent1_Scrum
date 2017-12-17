import {FETCHED_USER, FETCHED_TALENTS_USER, EDIT_CLICKED} from '../actions/ProfileActions';

const initialState = {
    editStatus: false,
    talents: []
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'EDIT_CLICKED':
            return Object.assign({}, state, {
                editStatus: true
            });
        case 'FETCHED_USER':
            return Object.assign({}, state, {
                name: action.json.person.firstname + ' ' + action.json.person.lastname,
                birthday: action.json.birthday,
                email: action.json.person.email,
                status: 'ACTIVE_USER_FETCHED'
            });
        case 'FETCHED_TALENTS_USER':
            return Object.assign({}, state, {
                talents: action.json,
                status: 'ACTIVE_TALENTS_USER_FETCHED'
            });
        default:
            return state
    }
}
