import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Button, ListGroup, ListGroupItem, Panel, Modal, FormGroup, ControlLabel, FormControl} from  'react-bootstrap';
import {Navigation} from '../components/Navigation';
import {Profile as Profilegetter, EditClicked, EndorseClicked, ShowEndorseClicked, ShowEndorsementsClicked, } from '../actions/ProfileActions';
import {UserInfo} from '../components/Profile/UserInfo';
import {EndorsementModal} from '../components/Profile/EndorsementModal';
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
                            <Button bsStyle='default' disabled={this.props.editStatus} onClick={() => this.props.onEditClick()}>Edit profile</Button>
                        </div> : ''
                    }
                    <UserInfo userName={this.props.userName} userEmail={this.props.userEmail} userBirthday={this.props.userBirthday}></UserInfo>
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
                                                    <Button bsStyle='info' onClick={() => this.props.ShowEndorsementsClick(this.props.profileUserId, talentInfo.talent.id, talentInfo.talent.name)}>View {talentInfo.endorsementCounter} endorsements</Button>
                                                    : ''
                                                }
                                                {/*Mag niet eigen profiel zijn*/}
                                                {this.props.ownProfile ?
                                                    <Button bsStyle='success' onClick={() => this.props.ShowEndorseClick(talentInfo.talent.name, talentInfo.talent.id)}>+</Button>
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
                                      <Button bsStyle='danger' onClick={() => this.props.ShowEndorseClick()}>Cancel</Button>
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
                                                    <ListGroupItem key={index}>
                                                        <h3>{endorsement.persons_id}</h3>
                                                        <p>{endorsement.description}</p>
                                                    </ListGroupItem>
                                                )}
                                            </ListGroup>
                                        </Panel>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button className='close-endorsements-overview' bsStyle='danger' onClick={() => this.props.ShowEndorseClick()}>X</Button>
                                    </Modal.Footer>
                                </Modal>
                            </div>
                    }
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
    ownProfile: true,

    editStatus: state.Profile.editStatus,

    endorsedTalentIDs: state.Profile.endorsedTalentIDs,

    modalShow: state.Profile.modalShow,
    modalStatus: state.Profile.modalStatus,

    endorsingTalent: state.Profile.endorsingTalent,
    endorsementsTalent: state.Profile.endorsementsTalent
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
        },
        ShowEndorsementsClick: (profileUserId, talentId, talentName) => {
          dispatch(ShowEndorsementsClicked(profileUserId, talentId, talentName));
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
