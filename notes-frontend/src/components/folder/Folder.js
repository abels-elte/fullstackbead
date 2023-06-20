import React, { useState } from 'react';
import { SideNote } from '../sidenote/SideNote';

import './Folder.css';
import { Button } from '../button/Button';

export function Folder(props) {

    const notesArr = props.notes;

    const [isOpen, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!isOpen);
    };

    return (
        <div className="folder">
            {props.default ? <div className="folder-name"><i className="fa-solid fa-toilet-paper"></i> Notes</div> : <div className="folder-name" onClick={toggleOpen}>{isOpen ? <i className="fa-solid fa-folder-open"></i> : <i className="fa-solid fa-folder"></i>} {props.name} </div>}
            {isOpen || props.default && notesArr.map(note => <SideNote default={props.default} key={note.id} note={note}/>)}
        </div>
    );
}