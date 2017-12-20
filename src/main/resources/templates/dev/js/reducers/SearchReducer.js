const initialState = {

    status: 'SEARCHING_FOR_NO_USER',
    searchstring: ''
};

export default function (state = initialState, action) {
    switch (action.type) {
        case 'SEARCHING_USER':
            return Object.assign({}, state, {
                status: 'SEARCHING_USER'
            });
        case 'FOUND_USER':
            return Object.assign({}, state, {
                status: 'FOUND_USER',
                foundUsers: action.json
            });
        case 'TYPING':
            return Object.assign({}, state, {
                status: 'TYPING',
                searchstring: action.string
            });

        default:
            return state
    }
}