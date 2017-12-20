import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Modal, Button, FormGroup, form, ControlLabel, FormControl} from  'react-bootstrap';
import { Login as LoginClicked } from '../actions/LoginActions';
import { Navigation } from '../components/Navigation';

class Login extends Component {
    render() {
        return (
            <div className="static-modal">
                <Navigation history={this.props.history} status={this.props.logStatus} />


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
                        <Button>Cancel</Button>
                        <Button onClick={() => this.props.history.push("/register")}>Register</Button>
                        <Button bsStyle="primary" onClick={() => this.props.onLoginClick(this.email.value, this.password.value)}>Login</Button>
                        {
                            //this.props.logStatus === "LOGGED_IN" ? this.props.history.push("/talentregistration") : ''
                        }
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    logStatus: state.Auth.status
});

const mapDispatchToProps = (dispatch) => {
    return {
        onLoginClick: (email, password) => {
            dispatch(LoginClicked(email, password))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
