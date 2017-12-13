import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Modal, Button, FormGroup, form, ControlLabel, FormControl, HelpBlock} from  'react-bootstrap';
import { Register as RegisterClicked } from '../actions/RegisterActions';

class Register extends Component {
    render() {
        return (
            <div className="static-modal" style={{marginTop: '50px'}}>
                <Modal.Dialog>
                    <Modal.Header>
                        <Modal.Title>Register</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <form>
                            <FormGroup
                                controlId="formFirstname"
                            >
                                <ControlLabel>Firstname</ControlLabel>
                                <FormControl
                                    inputRef={ref => { this.firstname = ref; }}
                                    type="text"
                                    placeholder="Enter your firstname"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                            <FormGroup
                                controlId="formLastname"
                            >
                                <ControlLabel>Lastname</ControlLabel>
                                <FormControl
                                    inputRef={ref => { this.lastname = ref; }}
                                    type="email"
                                    placeholder="Enter your lastname"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                            <FormGroup
                                controlId="formEmailAddress"
                            >
                                <ControlLabel>Email address</ControlLabel>
                                <FormControl
                                    inputRef={ref => { this.email = ref; }}
                                    type="email"
                                    placeholder="Enter your email address"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                            <FormGroup
                                controlId="formBirth"
                            >
                                <ControlLabel>Birthday</ControlLabel>
                                <FormControl
                                    inputRef={ref => { this.date = ref; }}
                                    type="date"
                                    placeholder="Enter your birthday"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                            <FormGroup
                                controlId="formPassword"
                            >
                                <ControlLabel>Password</ControlLabel>
                                <FormControl
                                    inputRef={ref => { this.password = ref; }}
                                    type="password"
                                    placeholder="Enter your password"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button>Cancel</Button>
                        <Button onClick={() => this.props.history.push("/login")}>Login</Button>
                        <Button bsStyle="primary" onClick={() => this.props.onRegisterClick(this.firstname.value, this.lastname.value, this.email.value, this.date.value, this.password.value)}>Register</Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    //user: state.activeUser
});

const mapDispatchToProps = (dispatch) => {
    return {
        onRegisterClick: (firstname, lastname, email, date, password) => {
            dispatch(RegisterClicked(firstname, lastname, email, date, password))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Register);
