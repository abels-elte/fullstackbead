import React from 'react';
import './Input.css';

export function Input(props) {
    return (
        <input className="input" id={props.id} type={props.type} placeholder={props.placeholder} onChange={props.onChange}/>
    );
}