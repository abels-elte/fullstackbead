import React, { useState } from 'react';

import './SideNote.css';
import { Button } from '../button/Button';
import { useNavigate } from 'react-router-dom';

export function SideNote(props) {

    const navigate = useNavigate();

    const auth = localStorage.getItem("notes-auth");

    const deleteMe = async () => {
        const res = await fetch("http://localhost:8080/notes/" + props.note.id, {
            method: "DELETE", 
            headers: {
            "Authorization": "Bearer " + auth
            },
        });
        console.log(res);
        navigate(0);
    };

    return (
        <div className={props.default ? "sidenote sidenote-default" : "sidenote"}>
            <div className="sidenote-title"> <Button text="X" onClick={deleteMe}/> {<i className="fa-solid fa-file-alt"></i>} {props.note.title}</div>
            <div className="sidenote-content">{props.note.content.substring(0, 20)}{props.note.content.length > 20 ? "…" : ""}</div>
        </div>
    );
}