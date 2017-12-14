import {FETCHED_TALENTS_USER} from '../actions/TalentActions';

const initialState = {
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'FETCHED_TALENTS_USER':
            return Object.assign({}, state, {
                talents: action.json
                status: 'TALENTS_USER_FETCHED'
            });

                break;
        default:
            return state
    }
}
