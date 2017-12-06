import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Modal, Button, FormGroup, form, ControlLabel, FormControl, HelpBlock} from  'react-bootstrap';

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
                                controlId="formEmailAddress"
                            >
                                <ControlLabel>Email address</ControlLabel>
                                <FormControl
                                    type="email"
                                    placeholder="Enter your email address"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                            <FormGroup
                                controlId="formPassword"
                            >
                                <ControlLabel>Password</ControlLabel>
                                <FormControl
                                    type="password"
                                    placeholder="Enter your password"
                                />
                                <FormControl.Feedback />
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button>Cancel</Button>
                        <Button>Login</Button>
                        <Button bsStyle="primary">Register</Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    //user: state.activeUser
});

const mapDispatchToProps = (dispatch) => ({

});

export default connect(mapStateToProps, mapDispatchToProps)(Register);
