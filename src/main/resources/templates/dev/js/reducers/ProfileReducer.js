import {FETCHED_USER, FETCHED_TALENTS_USER, EDIT_CLICKED, SHOW_ENDORSE_CLICKED, FETCHED_ENDORSEMENTS_TALENT, CANCEL_EDIT_CLICKED, DELETED_USER_TALENT, FETCHED_PERSON, UPDATED_PERSON, SUGGESTIONS_CLICKED, FETCHED_SUGGESTIONS, FETCHED_ALL_TALENTS, ACCEPTED_SUGGESTION, DECLINED_SUGGESTION, GIVING_NOT_LOGGED_IN_ALERT} from '../actions/ProfileActions';

const initialState = {
    firstname: '',
    lastname: '',
    birthday: '',
    email: '',
    editStatus: false,
    talents: [],
    endorsedTalentIDs: [],
    endorsingTalent: {},
    endorsementsTalent:[{}],
    modalShow: false,
    suggestionTalent: '',
    alertMessage: '',
    alertVisible: false,
    alertStyle: 'default',
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
                endorsedTalentIDs: state.endorsedTalentIDs.concat({
                    id: action.endorsedTalentId
                }),
                modalShow: false,
                alertVisible: true,
                alertStyle: 'success',
                alertMessage: 'Endorsement added!',
                status: 'ADDED_ENDORSEMENT'
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
                modalShow: true,
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
                alertVisible: true,
                alertStyle: 'success',
                alertMessage: 'Talent deleted!',
                status: 'USER_TALENT_DELETED'
            });
        case 'FETCHED_PERSON':
            return Object.assign({}, state, {
                endorsementPerson: action.json,
                status: 'PERSON_FETCHED'
            });
        case 'UPDATED_USER':
            return Object.assign({}, state, {
                alertVisible: true,
                alertStyle: 'success',
                alertMessage: 'Info updated!',
                status: 'PERSON_UPDATED'
            });
        case 'ADDED_SUGGESTION':
            return Object.assign({}, state, {
                alertVisible: true,
                alertStyle: 'success',
                alertMessage: 'Suggestion added!',
                status: 'SUGGESTION_ADDED'
            });
        case 'DISMISS_ALERT':
            return Object.assign({}, state, {
                alertVisible: false,
                status: 'ALERT_DISMISSED'
            });
        case 'FETCHED_SUGGESTIONS':
            return Object.assign({}, state, {
                modalStatus: 'SUGGEST',
                modalShow: true,
                suggestions: action.json,
                status: 'SUGGESTIONS_FETCHED'
            });
        case 'ACCEPTED_SUGGESTION':
            return Object.assign({}, state, {
                alertVisible: true,
                alertStyle: 'success',
                alertMessage: 'Suggestion accepted and talent added!',
                status: 'SUGGESTIONS_ACCECPTED'
            });
        case 'DECLINED_SUGGESTION':
            return Object.assign({}, state, {
                alertVisible: true,
                alertStyle: 'success',
                alertMessage: 'Suggestion declined and talent not added!',
                status: 'SUGGESTIONS_DECLINED'
            });
        case 'GIVING_NOT_LOGGED_IN_ALERT':
            return Object.assign({}, state, {
                alertVisible: true,
                alertStyle: 'danger',
                modalShow: false,
                alertMessage: 'You need to login to do that!',
                status: 'NOT_LOGGED_IN_ALERT_GIVEN'
            });
        default:
            return state
    }
}
