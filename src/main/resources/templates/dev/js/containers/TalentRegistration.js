import React, {Component} from 'react';
import {connect} from 'react-redux';
import { Panel, Button, form, FormGroup, FormControl } from  'react-bootstrap';
import { AddTalentClicked, chooseTalent, postAllTalents, submitTalentClick, submitNewTalent } from '../actions/TalentRegisterActions';
import { Navigation } from '../components/Navigation';
import { TRDropdown } from '../components/TalentRegistration/TRDropdown';

class TalentRegister extends Component {
    render() {
        //total count dropdowns for talents
        var list = [];
        for (var i = 1; i <= this.props.talentCount; i++) {
            list.push(
                <TRDropdown key={i} index={i} chosenTalents={this.props.chosenTalents} talents={this.props.talents} onChooseTalent={(talent, key) => this.props.onChooseTalent(talent, key)}/>
            );
        }



        return (
            <div>

                <Navigation onSearchClick={} history={this.props.history} status={this.props.logStatus} />
                <div className="col-md-6 col-md-offset-3" >
                    <Panel header="Wat zijn uw talenten?" bsStyle="primary">
                        {list}
                        {this.props.talentRegStatus === 'EDIT' ?
                            <form>
                                <FormGroup
                                    controlId="formTalent"
                                >
                                    <FormControl
                                        inputRef={ref => { this.talent = ref; }}
                                        type="text"
                                        placeholder="Voeg een talent toe"
                                    />
                                    <FormControl.Feedback />
                                    <Button bsStyle="primary" onClick={() => this.props.onSubmitNewTalent(this.talent.value)}>Voeg toe</Button>
                                </FormGroup>
                            </form> : ''
                        }
                    </Panel>
                    <div className="pull-right">
                        <Button bsStyle="default" onClick={() => this.props.onSubmitTalentClick()}>Voeg toe</Button>
                        <Button bsStyle="primary" onClick={() => this.props.onAddTalentClick(this.props.talentCount)} style={{marginLeft: '5px', marginRight: '5px'}}>+ Talent</Button>
                        <Button bsStyle="success" onClick={() => this.props.onPostAllTalents(this.props.chosenTalents, this.props.userId)}>Bevestig</Button>
                    </div>
                </div>
                {
                    this.props.logStatus !== "LOGGED_IN" ? this.props.history.push("/login") : ''
                }
            </div>
        );
    }
}



const mapStateToProps = (state) => ({
    logStatus: state.Auth.status,
    talentCount: state.TalentRegister.talentCount,
    talents: state.TalentRegister.talents,
    chosenTalents: state.TalentRegister.chosenTalents,
    userId: state.Auth.id,
    submitTalent: state.TalentRegister.submitTalent,
    talentRegStatus: state.TalentRegister.status
});

const mapDispatchToProps = (dispatch) => {
    return {
         onAddTalentClick: (talentCount) => {
             dispatch(AddTalentClicked(talentCount))
        },
        onChooseTalent: (talent, key) => {
             dispatch(chooseTalent(talent, key))
        },
        onPostAllTalents: (talents, userId) => {
             dispatch(postAllTalents(talents, userId))
        },
        onSubmitTalentClick: () => {
             dispatch(submitTalentClick())
        },
        onSubmitNewTalent: (talent) => {
             dispatch(submitNewTalent(talent))
        },
        onSearchClick: (searchstring) => {
             dispatch:
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TalentRegister);
