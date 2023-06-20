import React, { useEffect, useState } from 'react';

import { Logo, SmallLogo } from '../../components/logo/Logo';
import { Folder } from '../../components/folder/Folder';
import { Button } from '../../components/button/Button';

import './Notes.css';
import { EditableName } from '../../components/editablename/EditableName';
import { EditableNote } from '../../components/editablenote/EditableNote';
import { useNavigate } from 'react-router-dom';
import { User } from '../../components/user/User';

async function fetchtest() {
    const response = await fetch("http://localhost:8080/auth/sign-in", {
        method: "POST", 
        mode: "cors", 
        cache: "no-cache",
        credentials: "same-origin", 
        headers: {
        "Content-Type": "application/json",
        },
        redirect: "follow",
        referrerPolicy: "no-referrer", 
        body: JSON.stringify({
            "username": "test",
            "password": "test"
        }), 
    });
  console.log(response);
  return response.json(); 
}

export function Notes() {
  const navigate = useNavigate();
  const [foldersArr, setFoldersArr] = useState([]);
  
  const auth = localStorage.getItem("notes-auth");
  const expiry = localStorage.getItem("notes-auth-expiry");

  console.log(auth);
  console.log(expiry);

  const fetchNotes = async () => {
    const res = await fetch("http://localhost:8080/folders", {
        method: "GET", 
        headers: {
        "Authorization": "Bearer " + auth
        },
    });
    const json = await res.json();
        console.log(json);
        setFoldersArr(json);
  }

  const newFolder = async () => {
    const name = prompt("Enter the new folder's name: ");
    if (!name) return;
    const res = await fetch("http://localhost:8080/folders", {
        method: "POST", 
        headers: {
        "Authorization": "Bearer " + auth,
        "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "name": name
        }),
    });
    fetchNotes();
  };

  const exportNotes = async () => {
    const res = await fetch("http://localhost:8080/export", {
        method: "GET", 
        headers: {
        "Authorization": "Bearer " + auth
        },
    });

    const blob = await res.blob();
    const url = URL.createObjectURL(blob);

    window.open(url, '_blank');
    URL.revokeObjectURL(url);
  }

  useEffect(() => {
      if (!auth || !expiry || Date.parse(expiry) - Date.now() <= 0) {
        console.log("login expired or not logged in");
        localStorage.clear();
        navigate("/login");
      } else {
        fetchNotes();
      }
  }, [auth, expiry, navigate]);



  return (
    <div className="notes">
        <div className="sidebar">
            <div className="sticky">
                <SmallLogo/>
                <User/>
                <div className="new-buttons">
                    <Button icon="fa-solid fa-plus" text="new note"/>
                    <Button icon="fa-solid fa-folder-plus" text="new folder" onClick={newFolder}/>
                    <Button icon="fa-solid fa-upload" text="import" />
                    <Button icon= "fa-solid fa-download" text="export" onClick={exportNotes}/> 
                </div>
            </div>
            {foldersArr.map(folder => <Folder default={folder.default} name={folder.name} key={folder.id} notes={folder.notes}/>)}
        </div>
        <div className="edit-area">
            <div className="toolbar">
                <EditableName name="mocktitle"/>
                <Button icon="fa-solid fa-save" text="save note"/>
                <Button icon="fa-solid fa-folder" text="choose folder"/>
            </div>  
            <EditableNote content="mockdesc"/>
        </div>
    </div>
  );
}