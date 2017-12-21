import React, {Component} from 'react';
import {connect} from 'react-redux';
import { Logout as LA } from '../actions/LoginActions';

class Logout extends Component {
    componentWillMount(){
        this.props.onLogout
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
        onLogout: () => {
            dispatch(LA())
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Logout);
