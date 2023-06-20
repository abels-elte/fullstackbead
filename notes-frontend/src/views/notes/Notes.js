import React from 'react';

import { Logo, SmallLogo } from '../../components/logo/Logo';
import { Folder } from '../../components/folder/Folder';
import { Button } from '../../components/button/Button';

import './Notes.css';

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
  

  return (
    <div className="notes">
        <div className="sidebar">
            <SmallLogo/>
            <div className="new-buttons">
                <Button icon="fa-solid fa-plus" text="new note"/>
                <Button icon="fa-solid fa-folder-plus" text="new folder"/>
                <Button icon="fa-solid fa-upload" text="import" />
                <Button icon= "fa-solid fa-download" text="export" /> 
            </div>
            <Folder name="test"/>
            <Folder name="overflow test 1"/>
            <Folder name="overflow test 2"/>
            <Folder name="overflow test 3"/>
            <Folder name="overflow test 4"/>
            <Folder name="overflow test 5"/>
            <Folder name="overflow test 6"/>
            <Folder name="overflow test 7"/>
            <Folder name="overflow test 8"/>
        </div>
        <div className="edit-area">
            <div className="toolbar">
            </div>  
        </div>
    </div>
  );
}