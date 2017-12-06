import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';
export const Login = () => {
    return dispatch => {
        dispatch(LogingIn());
        return fetch(API+'users/login', {
            method: "POST",
            body: JSON.stringify({
                "email":"janrob42@gmail.com",
                "password":"Azerty123"
            }),
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
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