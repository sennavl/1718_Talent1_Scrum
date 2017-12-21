import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';
export const FetchSession = () => {
    return dispatch => {
        return fetch(API + 'session', {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedSession(json)))
    }
};
const FetchedSession = (json) => {
    return {
        type: 'FETCHED_SESSION',
        json
    }
};
