import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Button, ListGroup, ListGroupItem} from  'react-bootstrap';
import { Navigation } from '../components/Navigation';
import { Profile as Profilegetter } from "../actions/ProfileActions";
import { TRDropdown } from '../components/TalentRegistration/TRDropdown';



class Profile extends Component {
    componentWillMount() {
        this.props.FetchingUser()
    }


    render() {
        return (
            <div>
                <Navigation props={this.props.history} status={this.props.logStatus} />
                <div className="col-md-6 col-md-offset-3" >
                    <h2>{ this.props.name }</h2>
                    <div className="pull-right">
                        <Button bsStyle="default" onClick={() => this.props.onSubmitTalentClick()}>Edit profiel</Button>
                    </div>
                    <h3>Talenten</h3>
                    <div className="col-md-4 col-md-offset-3">
                        <ListGroup>
                            {this.props.talents.map((talent, index) =>
                                <ListGroupItem key={index} >
                                    <div>
                                        {talent.name}
                                    </div>
                                </ListGroupItem>
                            )}
                        </ListGroup>
                    </div>
                    <div>

                    </div>
                </div>

            </div>
        );
    }



}



const mapStateToProps = (state) => ({

    name: state.Profile.name,
    talents: state.Profile.talents
});

const mapDispatchToProps = (dispatch) => {
    return {
        FetchingUser: () => {
            dispatch(Profilegetter());
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
