import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';

export const EditClicked = () => {
    return {
        type: 'EDIT_CLICKED'
    }
};
//logged in user gebruiken
export const Profile = (id='933') => {
    return dispatch => {
        dispatch(FetchingUser());

        return fetch(API+'users/' + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'localhost'
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedUser(json)))
            .then(() => dispatch(FetchTalentsUser(id)))
        }
};

const FetchTalentsUser = (id) => {
    return dispatch => {
        dispatch(FetchingTalentsUser());
        return fetch(API+'users/' + id + '/talents', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'localhost'
    })
        .then(response => response.json())
        .then(json => dispatch(FetchedTalentsUser(json)))
    }
}


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
}

//logged in user gebruiken
export const EndorseClicked = (description, loggedInuserId='933', profileUserId, talentId) =>{
    return dispatch => {
        return fetch(API+'endorsement/add', {
        method: 'POST',
        body: JSON.stringify({
            'description': description,
            'persons_id': 935,
            'users_has_talents_person_id': profileUserId,
            'users_has_talents_talent_id': talentId
        }),
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'localhost'
    })
        .then(json => dispatch(endAddEndorsement(talentId)))

    }
};

const endAddEndorsement = (endorsedTalentId) => {
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
