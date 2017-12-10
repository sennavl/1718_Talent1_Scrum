import React, {Component} from 'react';
import {connect} from 'react-redux';
import { Panel, Button } from  'react-bootstrap';
import { AddTalentClicked } from '../actions/TalentRegisterActions';
import { Navigation } from '../components/Navigation';
import { TRDropdown } from '../components/TalentRegistration/TRDropdown';

class TalentRegister extends Component {
    render() {
        //total count dropdowns for talents
        var list = [];
        for (var i = 1; i <= this.props.talentCount; i++) {
            list.push(
                <TRDropdown key={i}/>
            );
        }
        return (
            <div>
                <Navigation props={this.props.history} status={this.props.logStatus} />
                <div className="col-md-6 col-md-offset-3" >
                    <Panel header="Wat zijn uw talenten?" bsStyle="primary">
                        {list}
                    </Panel>
                    <div className="pull-right">
                        <Button bsStyle="primary" onClick={() => this.props.onAddTalentClick(this.props.talentCount)}>+ Talent</Button>
                        <Button bsStyle="success" style={{marginLeft: '5px'}}>Bevestig</Button>
                    </div>
                </div>
            </div>
        );
    }
}



const mapStateToProps = (state) => ({
    logStatus: state.Auth.status,
    talentCount: state.TalentRegister.talentCount

});

const mapDispatchToProps = (dispatch) => {
    return {
         onAddTalentClick: (talentCount) => {
             dispatch(AddTalentClicked(talentCount))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(TalentRegister);
