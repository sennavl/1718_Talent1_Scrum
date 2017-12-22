import React, {Component} from 'react';
import {connect} from 'react-redux';
import { Navigation } from '../components/Navigation';
import { searchClicked } from '../actions/SearchActions';
import { Userlink } from '../components/Search/UserLink';

class Search extends Component {
    render() {
        return (
            <div>
                <Navigation id={this.props.id} parent={this} searchstring={this.props.string} onSearchClick={this.props.onSearchClick} history={this.props.history} status={this.props.logStatus} />
                <Userlink foundUsers={this.props.foundUsers} history={this.props.history}/>
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    logStatus: state.Auth.status,
    string: state.Search.searchstring,
    foundUsers: state.Search.foundUsers,
    id: state.Auth.id
});

const mapDispatchToProps = (dispatch) => {
    return {
        onSearchClick: (searchstring) => {
            dispatch(searchClicked(searchstring)).then(
                this.props.history.push("/search"))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Search);
