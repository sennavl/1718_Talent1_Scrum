import fetch from 'cross-fetch'

export const AddTalentClicked = (talentCount) => {
    return {
        type: 'ADD_TALENT_CLICKED',
        count: talentCount
    }
};

const API = 'http://localhost:8080/api/';
export const FetchTalents = () => {
    return dispatch => {
        dispatch(FetchinTalents());
        return fetch(API+'talents', {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
            .then(response => response.json())
            .then(json => dispatch(FetchedTalents(json)))
    }
};

const FetchinTalents = () => {
    return {
        type: 'FETCHING_TALENTS',
    }
};

const FetchedTalents = (json) => {
    return {
        type: 'FETCHED_TALENTS',
        json
    }
};

export const chooseTalent = (talent, key) => {
    return {
        type: 'CHOOSE_TALENT',
        talent,
        key
    }
};

export const postAllTalents = (talents, userId) => {
  return dispatch => {
      talents.map((talent, index) => {
          if (index != 0) { dispatch(doPostTalent(talent, userId))};
      });
      return {
          type: 'POSTING_TALENTS'
      }

  }
};

const doPostTalent = (talent, userId) => {
    return dispatch => {
        //dispatch(LogingIn());
        return fetch(API+'users/4/talents/add', {
            method: "POST",
            body: JSON.stringify({
                userTalent: {
                    "talentId": talent.id,
                    "description":"Ik vind dit bij me passen",
                    "hide":"0"
                },
            }),
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin"
        })
            .then(response => response.json())
            //.then(json => dispatch(LoggedIn(json)))
    }
};