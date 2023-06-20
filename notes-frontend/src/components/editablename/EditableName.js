import React, { useState } from 'react';

import './EditableName.css';

export function EditableName(props) {
    return (
        <input className="editable-name" type="text" defaultValue={props.name} onChange={props.onChange}/>
    );
}