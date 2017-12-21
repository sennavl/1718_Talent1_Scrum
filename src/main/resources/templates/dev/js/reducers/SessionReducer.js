import {FETCHED_SESSION} from '../actions/ProfileActions';

const initialState = {
    session: {
        email: "aaa",
        firstname: "aaa",
        id:683,
        lastname:"aaa"
    }
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'FETCHED_SESSION':
            console.log(action.json);
            return Object.assign({}, state, {
                session: action.json,
                status: 'SESSION_FETCHED'
            });
        default:
            return state
    }
}
