import React from 'react';
import './Button.css';

export function Button(props) {
    return (
        <button className={"button"} onClick={props.onClick} disabled={props.disabled}>
             {props.icon && <i className={props.icon}></i>} {props.text}
        </button>
    );
}