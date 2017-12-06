import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';
export const Login = (email, password) => {
    return dispatch => {
        dispatch(LogingIn());
        return fetch(API+'users/login', {
            method: "POST",
            body: JSON.stringify({
                "email": email,
                "password": password
            }),
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
            .then(response => response.json())
            .then(json => dispatch(LoggedIn(json)))
    }
};

const LogingIn = () => {
    return {
        type: 'LOGGING_IN',
    }
};

const LoggedIn = (json) => {
    return {
        type: 'LOGGED_IN',
        json
    }
};