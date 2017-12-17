import {FETCHED_USER, FETCHED_TALENTS_USER, EDIT_CLICKED, SHOW_ENDORSE_CLICKED} from '../actions/ProfileActions';

const initialState = {
    editStatus: false,
    talents: [],
    endorsedTalentIDs: [],
    endorsingTalent: {}
}

export default function (state = initialState, action) {
    switch (action.type) {
        case 'EDIT_CLICKED':
            return Object.assign({}, state, {
                editStatus: true,
                status: 'EDIT'
            });
        case 'FETCHED_USER':
            return Object.assign({}, state, {
                name: action.json.person.firstname + ' ' + action.json.person.lastname,
                birthday: action.json.birthday,
                email: action.json.person.email,
                profileUserId: action.json.person.id,
                status: 'ACTIVE_USER_FETCHED'
            });
        case 'FETCHED_TALENTS_USER':
            return Object.assign({}, state, {
                talents: action.json,
                status: 'ACTIVE_TALENTS_USER_FETCHED'
            });
        case 'ADDED_ENDORSEMENT':
            return Object.assign({}, state, {
                status: 'ADDED_ENDORSEMENT',
                //endorsedTalentIDs = action.endorsedTalentId
                endorsedTalentIDs: state.endorsedTalentIDs.concat({
                    id: action.endorsedTalentId
                }),
                show: false
            });
        case 'SHOW_ENDORSE_CLICKED':
            return Object.assign({}, state, {
                show: !state.show,
                endorsingTalent: {name: action.talentName, id: action.talentId},
                status: 'SHOW_ENDORSEMENT'
            });
        default:
            return state
    }
}
