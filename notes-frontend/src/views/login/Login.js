import React from 'react';

import {Button} from '../../components/button/Button.js';
import {Input} from '../../components/input/Input.js';
import { Logo } from '../../components/logo/Logo';

export function Login() {
  return (
    <div className="menu">
        <Logo/>
        <h1>Login</h1>
        <Input type="text" placeholder="username"/>
        <Input type="password" placeholder="password"/>
        <Button text="login"/>
        <a href="/create">Create an account.</a>
    </div>
  );
}