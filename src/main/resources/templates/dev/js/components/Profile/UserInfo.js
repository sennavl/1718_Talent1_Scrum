import React, {Component} from 'react';
import {ButtonGroup, DropdownButton, MenuItem} from  'react-bootstrap';

export const UserInfo = (props) => (
    <div>
        <h2>Profiel</h2>
        <p>Naam: {props.userName}</p>
        <p>Geboortedatum: {props.userBirthday}</p>
        <p>Email: {props.userEmail}</p>
    </div>
);
