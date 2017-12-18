import React, {Component} from 'react';
import {ButtonGroup, DropdownButton, MenuItem} from  'react-bootstrap';

export const EndorsementModal = (props) => (
    this.props.modalStatus === 'ADD' ?
        <Modal show={true} onHide={close} container={this} aria-labelledby='contained-modal-title'>
          <Modal.Header>
            <Modal.Title id='contained-modal-title'>Endorse talent: {this.props.endorsingTalent.name}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
              <FormGroup controlId='formControlsTextarea'>
                <ControlLabel>Comment</ControlLabel>
                <FormControl componentClass='textarea' placeholder='textarea' rows='6' inputRef={ref => { this.textarea = ref; }}/>
              </FormGroup>
          </Modal.Body>
          <Modal.Footer>
              <Button bsStyle='danger' onClick={() => this.props.ShowEndorseClick()}>Cancel</Button>
              <Button bsStyle='success' onClick={() => this.props.onEndorseClick(this.textarea.value, this.props.loggedInuserId, this.props.profileUserId, this.props.endorsingTalent.id)}>Endorse</Button>
          </Modal.Footer>
        </Modal>
        :
        ''

);
