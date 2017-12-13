import React, {Component} from 'react';
import {connect} from 'react-redux';
import { Panel, Button } from  'react-bootstrap';
import { AddTalentClicked, chooseTalent, postAllTalents } from '../actions/TalentRegisterActions';
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

                <Navigation history={this.props.history} status={this.props.logStatus} />
                <div className="col-md-6 col-md-offset-3" >
                    <Panel header="Wat zijn uw talenten?" bsStyle="primary">
                        {list}
                    </Panel>
                    <div className="pull-right">
                        <Button bsStyle="primary" onClick={() => this.props.onAddTalentClick(this.props.talentCount)}>+ Talent</Button>
                        <Button bsStyle="success" onClick={() => this.props.onPostAllTalents(this.props.chosenTalents, this.props.userId)} style={{marginLeft: '5px'}}>Bevestig</Button>
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
    userId: state.Auth.id
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
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TalentRegister);
