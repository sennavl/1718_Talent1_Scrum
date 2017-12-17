import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';

export const EditClicked = () => {
    return {
        type: 'EDIT_CLICKED'
    }
};

export const Profile = (id) => {
    return dispatch => {
        dispatch(FetchingUser());

        return fetch(API+'users/4', {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "localhost"
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedUser(json)))
            .then(() => dispatch(FetchTalentsUser()))
        }
};

const FetchTalentsUser = () => {
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
