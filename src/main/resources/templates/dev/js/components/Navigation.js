import React, {Component} from 'react';
import {Navbar, Nav, NavItem, FormGroup, FormControl, Button} from  'react-bootstrap';

export const Navigation = (props) => (
    <Navbar inverse collapseOnSelect>
        <Navbar.Header className="col-md-1">
            <Navbar.Brand>
                <a href="#">MyTalent</a>
            </Navbar.Brand>
            <Navbar.Toggle />
        </Navbar.Header>
        <Navbar.Collapse>
            {
                props.status !== "LOGGED_IN" ?
                    <Nav pullRight>
                        <NavItem onClick={() => props.history.push("/login")}>Login</NavItem>
                        <NavItem onClick={() => props.history.push("/register")}>Register</NavItem>
                    </Nav>
                    :
                    <div className="col-md-11">
                        <FormGroup className="col-md-10">
                            <FormControl
                                style={{margin: '5px', float: 'left', width: '90%'}}
                                type="text"
                                placeholder="Naar wie ben je opzoek?"
                                inputRef={ref => { this.searchstring = ref; }}
                            />
                            <Button onClick={() => this.props.onSearchClick(this.searchstring.value)} style={{margin: '5px'}}bsStyle="primary">Zoek</Button>
                        </FormGroup>
                        <Nav pullRight className="col-md-2">
                            <NavItem onClick={() => props.history.push("/profile")}>Profiel</NavItem>
                        </Nav>
                    </div>

            }

        </Navbar.Collapse>
    </Navbar>
);

