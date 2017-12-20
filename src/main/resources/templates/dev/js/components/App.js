import React from 'react';
import Login from '../containers/Login'
import Register from '../containers/Register'
import Profile from '../containers/Profile'
import { Switch, Route } from 'react-router-dom'
import TalentRegistration from '../containers/TalentRegistration'
import Search from '../containers/Search'


require('../../scss/style.scss');


const App = () => (

    <div>
        <Switch>
            <Route exact path='/register' component={Register}/>
            <Route exact path='/login' component={Login}/>
            <Route exact path='/profile' component={Profile}/>
            <Route exact path='/talentregistration' component={TalentRegistration}/>
            <Route exact path='/search' component={Search}/>
        </Switch>
    </div>
);

export default App;
