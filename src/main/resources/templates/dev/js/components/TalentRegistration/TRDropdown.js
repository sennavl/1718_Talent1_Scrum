import React, {Component} from 'react';
import {ButtonGroup, DropdownButton, MenuItem} from  'react-bootstrap';

export const TRDropdown = (props) => (
    <div style={{margin: '5px'}}>
        <ButtonGroup justified>
            <DropdownButton title={props.chosenTalents[props.index] === undefined ? "Kies een talent" : props.chosenTalents[props.index]} id="dropdown-size-medium" onSelect={(evt) => props.onChooseTalent(evt, props.index)}>
                {
                    props.talents.map((talent, index) => {
                        return <MenuItem  key={index} eventKey={talent.name} value={props.chosenTalents[index]}>{talent.name}</MenuItem>
                    })
                }
            </DropdownButton>
        </ButtonGroup>
    </div>
);

