
import React from 'react';

function ClinicButton({ ButtonLabel, actionButton }) {
    return (
        <div>
            <button onClick={actionButton}>
                {ButtonLabel}
            </button>
        </div>
    );
}

export default ClinicButton;
