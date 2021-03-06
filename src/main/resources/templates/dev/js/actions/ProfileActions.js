import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';

export const EditClicked = () => {
    return {
        type: 'EDIT_CLICKED'
    }
};
//logged in user gebruiken
export const Profile = (profileUserId) => {
    return dispatch => {
        dispatch(FetchingUser());
        return fetch(API+'users/' + profileUserId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'localhost'
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedUser(json)))
            .then(() => dispatch(FetchTalentsUser(profileUserId)))
    }
};
const FetchingUser = () => {
    return {
        type: 'FETCHING_USER',
    }
};
const FetchedUser = (json) => {
    return {
        type: 'FETCHED_USER',
        json
    }
};



const FetchTalentsUser = (profileUserId) => {
    return dispatch => {
        dispatch(FetchingTalentsUser());
        return fetch(API+'users/' + profileUserId + '/talentEndorsements', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'localhost'
    })
        .then(response => response.json())
        .then(json => dispatch(FetchedTalentsUser(json)))
    }
};
const FetchingTalentsUser = () => {
    return {
        type: 'FETCHING_TALENTS_USER',
    }
};
const FetchedTalentsUser = (json) => {
    return {
        type: 'FETCHED_TALENTS_USER',
        json
    }
};



export const EndorseClicked = (description, loggedInuserId=loggedinUseridtemp, profileUserId, talentId) =>{
    return dispatch => {
        return fetch(API+'endorsement/add', {
        method: 'POST',
        body: JSON.stringify({
            'description': description,
            'persons_id': loggedInuserId,
            'users_has_talents_person_id': profileUserId,
            'users_has_talents_talent_id': talentId
        }),
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'localhost'
    })
        .then(json => dispatch(EndAddEndorsement(talentId)))
        .then(dispatch(Profile(profileUserId)))

    }
};
const EndAddEndorsement = (endorsedTalentId) => {
   return {
       type: 'ADDED_ENDORSEMENT',
       endorsedTalentId
   }
};



export const ShowEndorseClicked = (talentName, talentId) => {
   return {
       type: 'SHOW_ENDORSE_CLICKED',
       talentName,
       talentId
   }
};



export const ShowEndorsementsClicked = (profileUserId, talentId, talentName) =>{
    return dispatch => {
        dispatch(FetchingEndorsementsTalent());
        return fetch(API+'users/' + profileUserId + '/talents/' + talentId.toString() + '/endorsements', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'localhost'
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedEndorsementsTalent(json, talentName)))
    }
};
const FetchingEndorsementsTalent = () => {
    return {
        type: 'FETCHING_ENDORSEMENTS_TALENT',
    }
};
const FetchedEndorsementsTalent = (json, talentName) => {
    return {
        type: 'FETCHED_ENDORSEMENTS_TALENT',
        json,
        talentName
    }
};



export const FetchPerson = (personId) => {
    return dispatch => {
        dispatch(FetchingPerson());
        return fetch(API+'persons/' + personId ,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'localhost'
    })
        .then(response => response.json())
        .then(json => dispatch(FetchedPerson(json)))
    }
};
const FetchingPerson = () => {
    return {
        type: 'FETCHING_PERSON'
    }
};
const FetchedPerson = (json) => {
    return {
        type: 'FETCHED_PERSON',
        json
    }
};



export const CancelEditClicked = () => {
    return {
        type: 'CANCEL_EDIT_CLICKED',
    }
};



export const DeleteTalentClicked = (talentId, profileUserId) => {
    return dispatch => {
        return fetch(API+'users/' + profileUserId + '/talents/' + talentId + '/delete', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'localhost'
        })
            .then(dispatch(DeletedUserTalent(talentId)))
            .then(dispatch(FetchTalentsUser(profileUserId)))
    }
};
const DeletedUserTalent = (talentId) => {
    return {
        type: 'DELETED_USER_TALENT',
        talentId,
    }
};



export const SaveClicked = (firstname, lastname, date, password, personId) => {
    return dispatch => {
        dispatch(UpdatingUser());
        return fetch(API+'users/update', {
            method: 'POST',
            body: JSON.stringify({
                'person': {
                    'id': personId,
                    'firstname': firstname !=='' ? firstname : '',
            		'lastname': lastname !=='' ? lastname : '',
            	},
            	'birthday': date !=='' ? date : '',
            	'password': password !=='' ? password : '',
            }),
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
            .then(dispatch(UpdatedUser()))
    }
};
const UpdatingUser = () => {
    return {
        type: 'UPDATING_USER'
    }
};
const UpdatedUser = () => {
    return {
        type: 'UPDATED_USER'
    }
};



export const SuggestTalentClicked = (reason, personId, talentId, profileUserId) => {
    return dispatch => {
        return fetch(API+'users/suggest', {
            method: 'POST',
            body: JSON.stringify({
              'text': reason,
              'person_id': personId,
              'users_has_talents_talent_id': talentId,
              'users_has_talents_person_id': profileUserId
            }),
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
            .then(dispatch(AddedSuggestion()))
    }
};
const AddedSuggestion = () => {
    return {
        type: 'ADDED_SUGGESTION',
    }
};


export const AlertDismissClicked = () => {
    return {
        type: 'DISMISS_ALERT',
    }
};


export const SuggestionsClicked = (personId) => {
    return dispatch => {
        dispatch(FetchingSuggestions());
        return fetch(API+'users/' + personId + '/suggestions', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedSuggestions(json)))
    }
};
const FetchingSuggestions = () => {
    return {
        type: 'FETCHING_SUGGESTIONS',
    }
};
const FetchedSuggestions = (json) => {
    return {
        type: 'FETCHED_SUGGESTIONS',
        json
    }
};



export const AcceptSuggestionClicked = (suggestionId, personId) => {
    return dispatch => {
        return fetch(API+'users/processSugestion', {
            method: 'POST',
            body: JSON.stringify({
                'voteId': suggestionId,
              	'accepted': true,
                'hide' : false
            }),
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
            .then(dispatch(AcceptedSuggestion()))
            .then(dispatch(SuggestionsClicked(personId)))
    }
};
const AcceptedSuggestion = () => {
    return {
        type: 'ACCEPTED_SUGGESTION',
    }
};



export const DeclineSuggestionClicked = (suggestionId, personId) => {
    return dispatch => {
        return fetch(API+'users/processSugestion', {
            method: 'POST',
            body: JSON.stringify({
                'voteId': suggestionId,
              	'accepted': false
            }),
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
            .then(dispatch(DeclinedSuggestion()))
            .then(dispatch(SuggestionsClicked(personId)))
    }
};
const DeclinedSuggestion = () => {
    return {
        type: 'DECLINED_SUGGESTION',
    }
};
