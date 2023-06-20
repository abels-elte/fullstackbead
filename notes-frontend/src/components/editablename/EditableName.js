import React, { useState } from 'react';

import './EditableName.css';

export function EditableName(props) {
    return (
        <div className="editable-name" contentEditable="true">
            {props.name}
        </div>
    );
}