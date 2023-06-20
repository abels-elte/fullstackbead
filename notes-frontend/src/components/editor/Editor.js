import React, { useEffect, useState } from 'react';
import { EditableName } from '../editablename/EditableName';
import { Button } from '../button/Button';
import { EditableNote } from '../editablenote/EditableNote';
import { useNavigate, useParams } from 'react-router-dom';

export function Editor() {
    const navigate = useNavigate();
    const auth = localStorage.getItem("notes-auth");
    // const expiry = localStorage.getItem("notes-auth-expiry");

    const params = useParams();

    const [noteTitle, setNoteTitle] = useState("");
    const [noteContent, setNoteContent] = useState("");


    useEffect( () => {
        const fetchNote = async () => {
            const res = await fetch("http://localhost:8080/notes/" + params.noteid, {
                method: "GET", 
                headers: {
                "Authorization": "Bearer " + auth
                },
            });
            if (!res.ok) {
                navigate("/notes");
            }
            const json = await res.json();
            console.log(json);
            setNoteTitle(json.title);
            setNoteContent(json.content);
        }

        fetchNote();
    }, [params, setNoteTitle, setNoteContent, navigate, auth])


    const saveNote = async() => {
        const newTitle = noteTitle;
        const newContent = noteContent;
        console.log(newTitle);
        console.log(newContent);
        const res = await fetch("http://localhost:8080/notes/" + params.noteid, {
            method: "POST", 
            headers: {
            "Authorization": "Bearer " + auth,
            "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "title": newTitle,
                "content": newContent
            }), 
        });
        console.log(res);
    };
    
    return (
        <div className="edit-area">
            <div className="toolbar">
                <EditableName name={noteTitle} onChange={e => setNoteTitle(e.value)}/>
                <Button icon="fa-solid fa-save" text="save note" onClick={e => saveNote()}/>
                <Button icon="fa-solid fa-folder" text="choose folder"/>
            </div>  
            <EditableNote content={noteContent} onChange={e => {
                setNoteContent(e.target.value)
                console.log(e);
                }}/>
        </div>
    );
}

