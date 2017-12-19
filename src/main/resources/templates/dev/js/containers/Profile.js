import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Button, ListGroup, ListGroupItem, Panel, Modal, FormGroup, ControlLabel, FormControl} from  'react-bootstrap';
import {Navigation} from '../components/Navigation';
import {Profile as Profilegetter, EditClicked, EndorseClicked, ShowEndorseClicked, ShowEndorsementsClicked, CancelEditClicked, DeleteTalentClicked, FetchPerson, SaveClicked} from '../actions/ProfileActions';
import {UserInfo} from '../components/Profile/UserInfo';
import style from '../../scss/style.scss'


class Profile extends Component {
    componentDidMount() {
        this.props.FetchingUser(this.props.profileUserId)
    }

    render() {
        return (
            <div>
                <Navigation props={this.props.history} status={this.props.logStatus} />
                <div className='col-md-6 col-md-offset-3' >
                    {/*Mag niet eigen profiel zijn*/}
                    {this.props.ownProfile ?
                        <div className='pull-right'>
                        {!this.props.editStatus ?
                            <Button bsStyle='default' disabled={this.props.editStatus} onClick={() => this.props.onEditClick()}>Edit profile</Button>
                            :
                            <div>
                                <Button bsStyle='danger' onClick={() => this.props.onCancelEditClick()}>Cancel</Button>
                                <Button bsStyle='primary' onClick={() => this.props.onSaveClick(this.newFirstname.value, this.newLastname.value, this.newDate.value, this.newPassword.value, this.props.loggedInuserId)}>Save</Button>
                            </div>
                        }
                        </div>
                        : ''
                    }
                    {this.props.editStatus ?
                        <div>
                            <h2>Edit Profiel</h2>
                            <form>
                                <FormGroup controlId="formFirstname">
                                    <ControlLabel>Firstname</ControlLabel>
                                    <FormControl
                                        inputRef={ref => { this.newFirstname = ref; }}
                                        type="text"
                                        placeholder={this.props.userFirstname}
                                    />
                                    <FormControl.Feedback />
                                </FormGroup>
                                <FormGroup controlId="formLastname">
                                    <ControlLabel>Lastname</ControlLabel>
                                    <FormControl
                                        inputRef={ref => { this.newLastname = ref; }}
                                        type="email"
                                        placeholder="Enter your lastname"
                                    />
                                    <FormControl.Feedback />
                                </FormGroup>
                                <FormGroup controlId="formBirth">
                                    <ControlLabel>Birthday</ControlLabel>
                                    <FormControl
                                        inputRef={ref => { this.newDate = ref; }}
                                        type="date"
                                        placeholder="Enter your birthday"
                                    />
                                    <FormControl.Feedback />
                                </FormGroup>
                                <FormGroup controlId="formPassword">
                                    <ControlLabel>Password</ControlLabel>
                                    <FormControl
                                        inputRef={ref => { this.newPassword = ref; }}
                                        type="password"
                                        placeholder="Enter your password"
                                    />
                                    <FormControl.Feedback />
                                </FormGroup>
                            </form>
                        </div>
                        :
                        <div>
                            <h2>Profiel</h2>
                            <p>Naam: {this.props.userFirstname} {this.props.userLastname}</p>
                            <p>Geboortedatum: {this.props.userBirthday}</p>
                            <p>Email: {this.props.userEmail}</p>
                        </div>
                    }
                    {/*<UserInfo userFirstname={this.props.userFirstname} userLastname={this.props.userLastname} userEmail={this.props.userEmail} userBirthday={this.props.userBirthday} editStatus={this.props.editStatus}></UserInfo>*/}
                </div>
                <div>
                    <div className='col-md-5 col-md-offset-3'>
                        <Panel header='Talenten' bsStyle='primary'>
                            <ListGroup>
                                {this.props.userTalents.map((talentInfo, index) =>
                                    <ListGroupItem key={index} className='clearfix'>
                                        <div>
                                            <p className='talentName'>{talentInfo.talent.name}</p>
                                            <div className='pull-right'>
                                                {talentInfo.endorsementCounter > 0 ?
                                                    <Button bsStyle='info' onClick={() => this.props.showEndorsementsClick(this.props.profileUserId, talentInfo.talent.id, talentInfo.talent.name)}>View {talentInfo.endorsementCounter} endorsements</Button>
                                                    : ''
                                                }
                                                {/*Mag niet eigen profiel zijn*/}
                                                {!this.props.ownProfile ?
                                                    <Button bsStyle='success' onClick={() => this.props.showEndorseClick(talentInfo.talent.name, talentInfo.talent.id)}>+</Button>
                                                    : ''
                                                }
                                                {this.props.editStatus ?
                                                    <Button bsStyle='danger' onClick={() => this.props.deleteTalentClick(talentInfo.talent.id, this.props.loggedInuserId)}>X {this.props.counter}</Button>

                                                    : ''
                                                }
                                                {/**/}
                                                {this.props.endorsedTalentIDs.map((item, i) =>
                                                    item.id === talentInfo.talent.id) ?
                                                    ''
                                                    : ''
                                                }
                                            </div>
                                        </div>
                                    </ListGroupItem>
                                )}
                            </ListGroup>
                        </Panel>
                        {this.props.modalStatus === 'ADD' ?
                            <div>
                                <Modal show={this.props.modalShow} onHide={this.close} container={this} aria-labelledby='contained-modal-title'>
                                  <Modal.Header>
                                    <Modal.Title id='contained-modal-title'>Endorse talent: {this.props.endorsingTalent.name}</Modal.Title>
                                  </Modal.Header>
                                  <Modal.Body>
                                      <FormGroup controlId='formControlsTextarea'>
                                        <ControlLabel>Comment</ControlLabel>
                                        <FormControl componentClass='textarea' placeholder='Comments about this talent' rows='6' inputRef={ref => { this.textarea = ref; }}/>
                                      </FormGroup>
                                  </Modal.Body>
                                  <Modal.Footer>
                                      <Button bsStyle='danger' onClick={() => this.props.showEndorseClick()}>Cancel</Button>
                                      <Button bsStyle='success' onClick={() => this.props.onEndorseClick(this.textarea.value, this.props.loggedInuserId, this.props.profileUserId, this.props.endorsingTalent.id)}>Endorse</Button>
                                  </Modal.Footer> : ''
                                </Modal>
                            </div>
                        :   <div>
                                <Modal show={this.props.modalShow} style={'customStyles'} onHide={this.close} container={this} aria-labelledby='contained-modal-title'>
                                    <Modal.Header>
                                      <Modal.Title id='contained-modal-title'>Endorsements talent: {this.props.endorsingTalent.name}</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body className={'modal-body2'}>
                                        <Panel header='Endorsements' bsStyle='primary'>
                                            <ListGroup>
                                                {this.props.endorsementsTalent.map((endorsement, index) =>
                                                    <ListGroupItem key={index} onClick={() => this.props.onViewEndorseClick(endorsement.persons_id)}>
                                                        {this.props.endorsementPerson ?
                                                        <h3>{this.props.endorsementPerson.person.lastname}</h3> : ''
                                                        }
                                                        <p>{endorsement.description}</p>
                                                    </ListGroupItem>
                                                )}
                                            </ListGroup>
                                        </Panel>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button className='close-endorsements-overview' bsStyle='danger' onClick={() => this.props.showEndorseClick()}>X</Button>
                                    </Modal.Footer>
                                </Modal>
                            </div>
                    }
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    userFirstname: state.Profile.firstname,
    userLastname: state.Profile.lastname,
    userBirthday: state.Profile.birthday,
    userEmail: state.Profile.email,
    userTalents: state.Profile.talents,
    userPerson: state.Profile.person,

    profileUserId:state.Profile.profileUserId,
    loggedInuserId: state.Auth.id,
    ownProfile: true,

    editStatus: state.Profile.editStatus,


    modalShow: state.Profile.modalShow,
    modalStatus: state.Profile.modalStatus,

    endorsedTalentIDs: state.Profile.endorsedTalentIDs,
    endorsingTalent: state.Profile.endorsingTalent,
    endorsementsTalent: state.Profile.endorsementsTalent,
    endorsementPerson: state.Profile.endorsementPerson,
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
        showEndorseClick: (endorsingTalentName, endorsingTalentId) => {
          dispatch(ShowEndorseClicked(endorsingTalentName, endorsingTalentId));
        },
        showEndorsementsClick: (profileUserId, talentId, talentName) => {
          dispatch(ShowEndorsementsClicked(profileUserId, talentId, talentName));
        },
        onCancelEditClick: () => {
            dispatch(CancelEditClicked());
        },
        deleteTalentClick: (talentId, loggedInuserId) => {
            dispatch(DeleteTalentClicked(talentId, loggedInuserId));
        },
        onViewEndorseClick: (personId) => {
            dispatch(FetchPerson(personId));
        },
        onSaveClick: () => {
            dispatch(SaveClicked());
        },
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
