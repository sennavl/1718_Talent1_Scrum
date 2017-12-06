import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Modal, Button, FormGroup, form, ControlLabel, FormControl} from  'react-bootstrap';
import { Login as LoginClicked } from '../actions/LoginActions';

class Login extends Component {
    render() {
        return (
            <div className="static-modal">
                <Modal.Dialog>
                    <Modal.Header>
                        <Modal.Title>Login</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <form>
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
                        <Button bsStyle="primary" onClick={() => this.props.onLoginClick(this.email.value, this.password.value)}>Login</Button>
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
        onLoginClick: (email, password) => {
            dispatch(LoginClicked(email, password))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
