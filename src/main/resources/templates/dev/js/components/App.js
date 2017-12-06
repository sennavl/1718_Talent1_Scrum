import React from 'react';
import Login from '../containers/Login'
import Register from '../containers/Register'
import { Switch, Route } from 'react-router-dom'

require('../../scss/style.scss');


const App = () => (
    <div>
        <Switch>
            <Route exact path='/register' component={Register}/>
            <Route exact path='/login' component={Login}/>
        </Switch>
    </div>
);

export default App;
