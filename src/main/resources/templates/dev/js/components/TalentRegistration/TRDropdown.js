import React, {Component} from 'react';
import {ButtonGroup, DropdownButton, MenuItem} from  'react-bootstrap';

export const TRDropdown = (props, status) => (
    <div style={{margin: '5px'}}>
        <ButtonGroup justified>
            <DropdownButton title="Kies een talent" id="dropdown-size-medium">
                <MenuItem  eventKey="1">Een of ander talent</MenuItem>
                <MenuItem  eventKey="2">Voeg zelf een talent toe</MenuItem>
            </DropdownButton>
        </ButtonGroup>
    </div>
);

