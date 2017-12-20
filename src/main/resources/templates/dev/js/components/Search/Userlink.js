import React, {Component} from 'react';
import {ListGroup, ListGroupItem} from  'react-bootstrap';

export const Userlink = (props) => (
    <div>
        {
            props.foundUsers.map((user, i) => {
                return (
                <ListGroup>
                    <ListGroupItem href="#link1">Link 1</ListGroupItem>
                    <ListGroupItem href="#link2">Link 2</ListGroupItem>
                </ListGroup>
                )
            })
        }
    </div>
);

