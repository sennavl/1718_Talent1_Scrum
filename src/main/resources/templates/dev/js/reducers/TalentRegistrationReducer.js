
const initialState = {
    talentCount: 3,
    talents: [],
    chosenTalents: [{0:'init'}]
};

export default function (state = initialState, action) {
    switch (action.type) {
        case 'ADD_TALENT_CLICKED':
            return Object.assign({}, state, {
                talentCount: action.count + 1
            });
        case 'FETCHED_TALENTS':
            return Object.assign({}, state, {
                talents: action.json
            });
        case 'CHOOSE_TALENT':
            if (typeof state.chosenTalents[action.key] === 'undefined') {
                return Object.assign({}, state, {

                    chosenTalents: state.chosenTalents.concat(action.talent)
                });
            } else {
                return Object.assign({}, state, {
                    chosenTalents: state.chosenTalents.map(
                        (tal, index) => {
                            if (index === action.key) {
                                return action.talent
                            } else {
                                return tal
                            }
                        }
                    )

                });
            }
        default:
            return state
    }
}