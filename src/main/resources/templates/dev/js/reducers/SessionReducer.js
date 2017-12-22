import {FETCHED_SESSION} from '../actions/ProfileActions';

const initialState = {
    session: {
        email: '',
        firstname: '',
        id: 0,
        lastname: ''
    }
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'FETCHED_SESSION':
            return Object.assign({}, state, {
                session: action.json,
                status: 'SESSION_FETCHED'
            });
        default:
            return state
    }
}
