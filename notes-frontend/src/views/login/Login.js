import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import './Login.css';

import {Button} from '../../components/button/Button.js';
import {Input} from '../../components/input/Input.js';
import { Logo } from '../../components/logo/Logo';

export function Login() {
  const navigate = useNavigate();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const [errorMessage, setErrorMessage] = useState('');

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const tryLogin = async () => {
    const res = await fetch("http://localhost:8080/auth/sign-in", {
        method: "POST", 
        headers: {
        "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "username": username,
            "password": password
        }), 
    });
    if (res.ok) {
      localStorage.setItem("notes-auth", res.headers.get("authorization"));
      localStorage.setItem("notes-auth-expiry", res.headers.get("expires"));
    } else {
      localStorage.clear();
      setErrorMessage("Invalid username or password.");
      return;
    }
    const data = await res.json();
    if (data) {
        localStorage.setItem("notes-user", data.username);
        navigate("/notes");
    }
  };

  return (
    <div className="menu">
        <Logo/>
        <h1>Login</h1>
        <Input type="text" placeholder="username" onChange={handleUsernameChange}/>
        <Input type="password" placeholder="password" onChange={handlePasswordChange}/>
        <Button text="login" onClick={tryLogin}/>
        <div className="error-message">{errorMessage}</div>
        <a href="/create">Create an account.</a>
    </div>
  );
}