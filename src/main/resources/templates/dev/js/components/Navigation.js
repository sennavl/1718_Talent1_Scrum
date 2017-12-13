import React, {Component} from 'react';
import {Navbar, Nav, NavItem} from  'react-bootstrap';

export const Navigation = (props, status) => (
    <Navbar inverse collapseOnSelect>
        <Navbar.Header>
            <Navbar.Brand>
                <a href="#">MyTalent</a>
            </Navbar.Brand>
            <Navbar.Toggle />
        </Navbar.Header>
        <Navbar.Collapse>
            {                console.log(props.history)
            }
            {
                status != 'LOGGED_IN' ?
                    <Nav pullRight>
                        <NavItem onClick={() => props.history.push("/login")}>Login</NavItem>
                        <NavItem onClick={() => props.history.push("/register")}>Register</NavItem>
                    </Nav>
                    :
                    <Nav pullRight>
                        <NavItem onClick={() => props.history.push("/profile")}>Profiel</NavItem>
                    </Nav>
            }

        </Navbar.Collapse>
    </Navbar>
);

