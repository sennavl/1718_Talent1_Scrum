import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Button, ListGroup, ListGroupItem, Panel} from  'react-bootstrap';
import { Navigation } from '../components/Navigation';
import { Profile as Profilegetter, EditClicked } from "../actions/ProfileActions";
import { UserInfo } from '../components/Profile/UserInfo';


class Profile extends Component {
    componentDidMount() {
        this.props.FetchingUser()
    }

    render() {
        return (
            <div>
                <Navigation props={this.props.history} status={this.props.logStatus} />
                <div className="col-md-6 col-md-offset-3" >
                    <div className="pull-right">
                        <Button bsStyle="default" disabled={this.props.editStatus} onClick={() => this.props.onEditClick()}>Edit profiel</Button>
                    </div>
                    <UserInfo userName={this.props.userName} userEmail={this.props.userEmail} userBirthday={this.props.userBirthday}></UserInfo>

                </div>
                <div>
                    <div className="col-md-5 col-md-offset-3">
                        <Panel header="Talenten" bsStyle="primary">
                            <ListGroup>
                                {this.props.userTalents.map((talent, index) =>
                                    <ListGroupItem key={index}>
                                        <div>
                                            {talent.name}
                                            {!this.props.ownProfile ?
                                                <div className="pull-right">
                                                    <Button bsStyle="success" onClick={() => this.props.onEndorseClick()}>Endorse</Button>
                                                </div> : ''
                                            }

                                        </div>
                                    </ListGroupItem>
                                )}
                            </ListGroup>
                        </Panel>
                    </div>
                    <div>
                        {this.props.editStatus ?
                            <h1>Test</h1> : ''
                        }



                    </div>
                </div>

            </div>
        );
    }



}



const mapStateToProps = (state) => ({
    userName: state.Profile.name,
    userBirthday: state.Profile.birthday,
    userEmail: state.Profile.email,
    userTalents: state.Profile.talents,
    editStatus: state.Profile.editStatus,
    //chosenTalents: [{"id":1,"name":"motiverend","matches":7},{"id":2,"name":"motiverender","matches":36},{"id":8,"name":"technische leider","matches":5},{"id":10,"name":"creatieveling","matches":3},{"id":14,"name":"zelfvertrouwen","matches":3},{"id":19,"name":"probleemanalyse","matches":25},{"id":38,"name":"testicle2","matches":1},{"id":41,"name":"Fietsen","matches":1}]
});

const mapDispatchToProps = (dispatch) => {
    return {
        FetchingUser: () => {
            dispatch(Profilegetter());
        },
        onEditClick: () => {
            dispatch(EditClicked());
       }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
