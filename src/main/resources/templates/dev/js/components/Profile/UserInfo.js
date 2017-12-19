import React, {Component} from 'react';
import {FormGroup, ControlLabel, FormControl} from  'react-bootstrap';

const getValidationState = () => {
    const length = this.state.value.length;
    if (length > 10) return 'success';
    else if (length > 5) return 'warning';
    else if (length > 0) return 'error';
    return null;
}


export const UserInfo = (props) => (

    props.editStatus ?
    <div>
        <h2>Edit Profiel</h2>
        <form>
            <FormGroup
                controlId="formFirstname"
            >
                <ControlLabel>Firstname</ControlLabel>
                <FormControl
                    inputRef={ref => { props.userFirstname = ref; }}
                    type="text"
                    placeholder="Enter your firstname"
                />
                <FormControl.Feedback />
            </FormGroup>
        </form>
        <p>Naam: {props.userFirstname} {props.userLastname}</p>
        <p>Geboortedatum: {props.userBirthday}</p>
        <p>Email: {props.userEmail}</p>
    </div>

    :
    <div>
        <h2>Profiel</h2>
        <p>Naam: {props.userFirstname} {props.userLastname}</p>
        <p>Geboortedatum: {props.userBirthday}</p>
        <p>Email: {props.userEmail}</p>
    </div>
);
