import React, { useState } from 'react';

import './SideNote.css';

export function SideNote(props) {
    return (
        <div className="sidenote">
            <div className="sidenote-title"> {<i className="fa-solid fa-file-alt"></i>} {props.note.title} </div>
            <div className="sidenote-content">{props.note.content.substring(0, 20)}{props.note.content.length > 20 ? "…" : ""}</div>
        </div>
    );
}