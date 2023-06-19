import React, { useState } from 'react';
import './Create.css';

import { Button } from '../../components/button/Button.js';
import { Input } from '../../components/input/Input.js';
import { Logo } from '../../components/logo/Logo';

export function Create() {
    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="menu create">
            <Logo />
            <h1>Create account</h1>
            <div className="create-container">
                <Input type="text" placeholder="username" />
                <div>
                    <Input
                        type={showPassword ? "text" : "password"}
                        placeholder="password"
                    />
                    <Button
                        icon="fa-solid fa-eye"
                        onClick={togglePasswordVisibility}
                    />
                </div>
            </div>
            <Button text="create account" />
            <a href="/">Back to login.</a>
        </div>
    );
}