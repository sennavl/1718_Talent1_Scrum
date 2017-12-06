import React from 'react';
import Login from '../containers/Login'
import Register from '../containers/Register'
require('../../scss/style.scss');

const App = () => (
    <div>
        <Login />
        <Register />
    </div>
);

export default App;
