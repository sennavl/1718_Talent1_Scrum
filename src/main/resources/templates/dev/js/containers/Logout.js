import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Modal, Button, FormGroup, form, ControlLabel, FormControl} from  'react-bootstrap';
import {LogOut} from '../actions/LoginActions';
import { Navigation } from '../components/Navigation';

class Logout extends Component {
    componentWillMount(){
        LogOut();
        this.props.history.push("/login/");
    }
    render() {
        return (
            <div>

            </div>
        );
    }
}

const mapStateToProps = (state) => ({
});

const mapDispatchToProps = (dispatch) => {
    return {
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Logout);
