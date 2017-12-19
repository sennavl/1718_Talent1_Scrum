import fetch from 'cross-fetch'

const API = 'http://localhost:8080/api/';
export const Register = (firstname, lastname, email, date, password) => {
    return dispatch => {
        dispatch(Registering());
        return fetch(API+'users/register', {
            method: "POST",
            body: JSON.stringify({
                "person": {
                    "firstname": firstname,
                    "lastname": lastname,
                    "email": email
                },
                "user" :{
                    "birthday": date,
                    "password": password
                }
            }),
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
            .then(response => response.json())
            .then(json => dispatch(RegisterSucces(json)))
    }
};

const Registering = () => {
    return {
        type: 'REGISTERING',
    }
};

const RegisterSucces = (json) => {
    return {
        type: 'REGISTER_SUCCES',
        json
    }
};