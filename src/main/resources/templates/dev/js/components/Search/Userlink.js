import React, {Component} from 'react';
import {ListGroup, ListGroupItem} from  'react-bootstrap';

export const Userlink = (props) => (
    <div>
        <ListGroup>

        {
            props.foundUsers.map((user, i) => {
                return (
                    <ListGroupItem key={i} href="/talentsregistration">{user.person.firstname}</ListGroupItem>
                )
            })
        }
        </ListGroup>
    </div>
);

