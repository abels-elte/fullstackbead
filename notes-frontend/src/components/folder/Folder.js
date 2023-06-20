import React, { useState } from 'react';
import { SideNote } from '../sidenote/SideNote';

import './Folder.css';
import { Button } from '../button/Button';

export function Folder(props) {

    const notesArr = [ 
        {
            "id": "1",
            "title": "mocktitle",
            "content": "mockdesc"
        },
        {
            "id": "2",
            "title": "mock title 2",
            "content": "mock desc 2 words"
        },
        {
            "id": "3",
            "title": "longer mock title or something",
            "content": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        }
    ];

    const [isOpen, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!isOpen);
    };

    return (
        <div className="folder">
            <div className="folder-name" onClick={toggleOpen}>{isOpen ? <i className="fa-solid fa-folder-open"></i> : <i className="fa-solid fa-folder"></i>} {props.name} </div>
            {isOpen && notesArr.map(note => <SideNote key={note.id} note={note}/>)}
        </div>
    );
}