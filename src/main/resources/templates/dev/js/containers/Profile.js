import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Modal, Button, FormGroup, form, ControlLabel, FormControl} from  'react-bootstrap';
import { Navigation } from '../components/Navigation';
import { Profile as Profilegetter } from "../actions/ProfileActions";

class Profile extends Component {
    componentDidMount() {
        this.props.FetchingUser()
        
    }

    render() {
        return (
            <div className="static-modal">
                <Navigation props={this.props.history} status={this.props.logStatus} />
                <h2>{ this.props.name }</h2>

            </div>
        );
    }



}


const mapStateToProps = (state) => ({
    name: state.Profile.name
});

const mapDispatchToProps = (dispatch) => {
    return {
        FetchingUser: () => {
            dispatch(Profilegetter());
        }

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
