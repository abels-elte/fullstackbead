import React, { useState } from 'react';

import './EditableNote.css';

export function EditableNote(props) {
    return (
        <textarea className="editable-note" defaultValue={props.content}>
        </textarea>
    );
}