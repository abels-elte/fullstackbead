import React, { useState } from 'react';

import './EditableNote.css';

export function EditableNote(props) {
    return (
        <textarea className="editable-note" value={props.content} onChange={props.onChange}>
        </textarea>
    );
}