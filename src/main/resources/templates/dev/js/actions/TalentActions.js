import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';
export const Talent = (id) => {
    return dispatch => {
        dispatch(FetchingTalentsUser());
        return fetch(API+'users/4/talents', {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "localhost"
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
