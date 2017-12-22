import React, {Component} from 'react';
import {Navbar, Nav, NavItem, FormGroup, FormControl, Button, Form} from  'react-bootstrap';

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
                props.session !== null ||
                    props.session.session.id === 0 ?
                        <Nav pullRight>
                            <NavItem onClick={() => props.history.push("/login")}>Login</NavItem>
                            <NavItem onClick={() => props.history.push("/register")}>Register</NavItem>
                        </Nav>
                        :
                        <div className="col-md-11">
                            <Form>
                                <FormGroup controlId="formSearchstring" className="col-md-10">
                                    <FormControl
                                        inputRef={ref => { props.parent.searchstring = ref }}
                                        type="input"
                                        placeholder="Naar wie ben je opzoek?"
                                        style={{margin: '5px', float: 'left', width: '90%'}}
                                    />
                                    <Button onClick={() => (props.onSearchClick(props.parent.searchstring.value), props.history.push("/search"))} style={{margin: '5px'}} bsStyle="primary">Zoek</Button>
                                </FormGroup>
                            </Form>
                            <Nav pullRight className="col-md-2">
                                <NavItem onClick={() => props.history.push("/profile/"+ props.session.session.id)}>Profiel</NavItem>
                                <NavItem onClick={() => props.history.push("/logout/"+ props.session.session.id)}>Profiel</NavItem>                                
                            </Nav>
                        </div>
            }
        </Navbar.Collapse>
    </Navbar>
);
