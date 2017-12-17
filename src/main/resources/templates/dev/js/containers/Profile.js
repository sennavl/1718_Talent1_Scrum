import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Button, ListGroup, ListGroupItem, Panel, Modal, FormGroup, ControlLabel, FormControl} from  'react-bootstrap';
import {Navigation} from '../components/Navigation';
import {Profile as Profilegetter, EditClicked, EndorseClicked, ShowEndorseClicked} from "../actions/ProfileActions";
import {UserInfo} from '../components/Profile/UserInfo';

class Profile extends Component {
    componentDidMount() {
        this.props.FetchingUser(this.props.profileUserId)
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
                                            {!this.props.ownProfile && this.props.endorsedTalentIDs.map((item, i) => {item.id === talent.id }) ?
                                                <div className="pull-right">
                                                    <Button bsStyle="success" onClick={() => this.props.ShowEndorseClick(talent.name, talent.id)}>Endorse</Button>
                                                </div> : ''
                                            }

                                        </div>
                                    </ListGroupItem>
                                )}
                            </ListGroup>
                        </Panel>
                        <Modal show={this.props.show} onHide={close} container={this} aria-labelledby="contained-modal-title">
                          <Modal.Header>
                            <Modal.Title id="contained-modal-title">Endorse talent: {this.props.endorsingTalent.name}</Modal.Title>
                          </Modal.Header>
                          <Modal.Body>
                              <FormGroup controlId="formControlsTextarea">
                                <ControlLabel>Comment</ControlLabel>
                                <FormControl componentClass="textarea" placeholder="textarea" rows='6' inputRef={ref => { this.textarea = ref; }}/>
                              </FormGroup>
                          </Modal.Body>
                          <Modal.Footer>
                              <Button bsStyle="danger" onClick={() => this.props.ShowEndorseClick()}>Cancel</Button>
                              <Button bsStyle="success" onClick={() => this.props.onEndorseClick(this.textarea.value, this.props.loggedInuserId, this.props.profileUserId, this.props.endorsingTalent.id)}>Endorse</Button>
                          </Modal.Footer>
                        </Modal>
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
    profileUserId:state.Profile.profileUserId,
    loggedInuserId: state.Auth.id,
    description: 'TEST',
    editStatus: state.Profile.editStatus,
    endorsedTalentIDs: state.Profile.endorsedTalentIDs,
    show: state.Profile.show,
    endorsingTalent: state.Profile.endorsingTalent
    //chosenTalents: [{"id":1,"name":"motiverend","matches":7},{"id":2,"name":"motiverender","matches":36},{"id":8,"name":"technische leider","matches":5},{"id":10,"name":"creatieveling","matches":3},{"id":14,"name":"zelfvertrouwen","matches":3},{"id":19,"name":"probleemanalyse","matches":25},{"id":38,"name":"testicle2","matches":1},{"id":41,"name":"Fietsen","matches":1}]
});

const mapDispatchToProps = (dispatch) => {
    return {
        FetchingUser: () => {
            dispatch(Profilegetter());
        },
        onEditClick: () => {
            dispatch(EditClicked());
        },
        onEndorseClick: (description, loggedInuserId, profileUserId, talentId) => {
           dispatch(EndorseClicked(description, loggedInuserId, profileUserId, talentId));
        },
        ShowEndorseClick: (endorsingTalentName, endorsingTalentId) => {
          dispatch(ShowEndorseClicked(endorsingTalentName, endorsingTalentId));
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
