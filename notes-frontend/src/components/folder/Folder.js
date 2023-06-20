import React, { useState } from 'react';
import { SideNote } from '../sidenote/SideNote';

import './Folder.css';
import { Button } from '../button/Button';
import { useNavigate } from 'react-router-dom';

export function Folder(props) {
    const navigate = useNavigate();
    
    const auth = localStorage.getItem("notes-auth");
    const expiry = localStorage.getItem("notes-auth-expiry");
    
    const notesArr = props.notes;
    console.log(notesArr);

    const [isOpen, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!isOpen);
    };

    const deleteMe = async () => {
        const res = await fetch("http://localhost:8080/folders/" + props.id, {
            method: "DELETE", 
            headers: {
            "Authorization": "Bearer " + auth
            },
        });
        console.log(res);
        navigate(0);
    };

    return (
        <div className="folder">
            {props.default ? <div className="folder-default"><i className="fa-solid fa-toilet-paper"></i> Notes </div> : <div className="folder-name" onClick={toggleOpen}> <div>{isOpen ? <i className="fa-solid fa-folder-open"></i> : <i className="fa-solid fa-folder"></i>} {props.name}</div> <Button text="X" onClick={deleteMe}/></div>}
            {(isOpen || props.default) && notesArr.map(note => <SideNote default={props.default} key={note.id} note={note}/>)}
        </div>
    );
}