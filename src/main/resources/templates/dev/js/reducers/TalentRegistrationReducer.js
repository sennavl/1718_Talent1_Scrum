
const initialState = {
    talentCount: 1,
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

                    chosenTalents: state.chosenTalents.concat({
                        id: action.talent[0],
                        value: action.talent[1]
                    })
                });
            } else {
                return Object.assign({}, state, {
                    chosenTalents: state.chosenTalents.map(
                        (tal, index) => {
                            if (index === action.key) {
                                return {
                                    id: action.talent[0],
                                    value: action.talent[1]
                                }
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