import {REGISTER_SUCCES} from '../actions/RegisterActions';

const initialState = {
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'REGISTER_SUCCES':
            return Object.assign({}, state, {
                status: 'REGISTER_SUCCES'
            });
        default:
            return state
    }
}