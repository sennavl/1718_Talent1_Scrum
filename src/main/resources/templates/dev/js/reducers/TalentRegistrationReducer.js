
const initialState = {
  talentCount: 3
};

export default function (state = initialState, action) {
    switch (action.type) {
        case 'ADD_TALENT_CLICKED':
            return Object.assign({}, state, {
                talentCount: action.count + 1
            });
        default:
            return state
    }
}