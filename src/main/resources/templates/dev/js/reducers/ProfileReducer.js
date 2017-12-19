import {FETCHED_USER, FETCHED_TALENTS_USER, EDIT_CLICKED, SHOW_ENDORSE_CLICKED, FETCHED_ENDORSEMENTS_TALENT, CANCEL_EDIT_CLICKED, DELETED_USER_TALENT, FETCHED_PERSON, UPDATED_PERSON} from '../actions/ProfileActions';

const initialState = {
    editStatus: false,
    talents: [],
    endorsedTalentIDs: [],
    endorsingTalent: {},
    endorsementsTalent:[{}],
    modalShow: false,
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
                firstname: action.json.person.firstname,
                lastname:  action.json.person.lastname,
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
                endorsedTalentIDs: state.endorsedTalentIDs.concat({
                    id: action.endorsedTalentId
                }),
                modalShow: false
            });
        case 'SHOW_ENDORSE_CLICKED':
            return Object.assign({}, state, {
                modalStatus: 'ADD',
                modalShow: !state.modalShow,
                endorsingTalent: {name: action.talentName, id: action.talentId},
                status: 'SHOW_ADD_ENDORSEMENT'
            });
        case 'FETCHED_ENDORSEMENTS_TALENT':
            return Object.assign({}, state, {
                modalStatus: 'VIEW',
                modalShow: !state.modalShow,
                endorsementsTalent: action.json,
                endorsingTalent: {name: action.talentName},
                status: 'ENDORSEMENTS_TALENT_FETCHED'
            });
        case 'CANCEL_EDIT_CLICKED':
            return Object.assign({}, state, {
                editStatus: false
            });
        case 'DELETED_USER_TALENT':
            return Object.assign({}, state, {
                deletedTalentId: action.talentId,
                status: 'USER_TALENT_DELETED'
            });
        case 'FETCHED_PERSON':
            return Object.assign({}, state, {
                endorsementPerson: action.jason,
                status: 'PERSON_FETCHED'
            });
        case 'UPDATED_PERSON':
            return Object.assign({}, state, {
                status: 'PERSON_UPDATED'
            });
        default:
            return state
    }
}
