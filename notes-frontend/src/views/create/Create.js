import React, { useState } from 'react';
import './Create.css';

import { Button } from '../../components/button/Button.js';
import { Input } from '../../components/input/Input.js';
import { Logo } from '../../components/logo/Logo';
import { useNavigate } from 'react-router-dom';

export function Create() {
    const navigate = useNavigate();

    const [showPassword, setShowPassword] = useState(false);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
   
    const [errorMessage, setErrorMessage] = useState('');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const tryCreate = async () => {
        const res = await fetch("http://localhost:8080/auth/sign-up", {
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
            navigate("/login");
        } else {
            setErrorMessage("Error while creating user. The user already exists or the password does not exceed 10 characters.");
        }
        
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="menu create">
            <Logo/>
            <h1>Create account</h1>
            <div className="create-container">
                <Input type="text" placeholder="username" onChange={handleUsernameChange}/>
                <div>
                    <Input type={showPassword ? "text" : "password"} placeholder="password" onChange={handlePasswordChange}/>
                    <Button
                        icon="fa-solid fa-eye"
                        onClick={togglePasswordVisibility}
                    />
                </div>
            </div>
            <Button text="create account" onClick={tryCreate}/>
            <div className="error-message">{errorMessage}</div>
            <a href="/">Back to login.</a>
        </div>
    );
}