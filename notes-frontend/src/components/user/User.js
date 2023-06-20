import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../button/Button';

import './User.css';

export function User() {
    const navigate = useNavigate();
    const logout = () => {
        localStorage.clear();
        navigate("/login");
    }
    return (
        <div className="user">
             <div>Welcome, {localStorage.getItem("notes-user")}!</div>
             <Button icon="fa-solid fa-user-slash" text="logout" onClick={logout}/>
        </div>
    );
}
