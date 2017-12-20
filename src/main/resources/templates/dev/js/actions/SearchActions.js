import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';
export const searchClicked = (searchstring) => {
    return dispatch => {
        dispatch(searchingUser());
        return fetch(API + 'users/search/' + searchstring, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
            .then(response => response.json())
            .then(json => dispatch(foundUser(json)))
    }
};

const searchingUser = () => {
    return {
        type: 'SEARCHING_USER',
    }
};

const foundUser = (json) => {
    return {
        type: 'FOUND_USER',
        json
    }
};

export const searchInput = (string) => {
    return {
        type: 'TYPING',
        string
    }
};