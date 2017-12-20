import React, {Component} from 'react';
import {connect} from 'react-redux';
import { Navigation } from '../components/Navigation';
import { searchClicked } from '../actions/SearchActions';
import { Userlink } from '../components/Search/UserLink';

class Search extends Component {
    render() {
        return (
            <div>
                <Navigation parent={this} searchstring={this.props.string} onSearchClick={this.props.onSearchClick} history={this.props.history} status={this.props.logStatus} />
                <Userlink foundUsers={this.foundUsers} history={this.props.history}/>
                {
                    this.props.logStatus !== "LOGGED_IN" ? this.props.history.push("/login") : ''
                }
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    logStatus: state.Auth.status,
    string: state.Search.searchstring,
    foundUsers: state.Search.foundUsers
});

const mapDispatchToProps = (dispatch) => {
    return {
        onSearchClick: (searchstring) => {
            dispatch(searchClicked(searchstring))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Search);