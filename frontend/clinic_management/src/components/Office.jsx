
import React, { useState } from 'react';
import axios from 'axios';
import ClinicButton from './ClinicButton';

function Office() {

    const [officeName, setOfficeName] = useState("");
    const [officeNumber, setOfficeNumber] = useState("");

    async function createAccount() {
        try {
            const response = await axios.post(
                "http://localhost:1014/api/office/save",
                {
                    "name": officeName,
                    "officeNumber": Number(officeNumber)
                }
            );

            if (response) {
                alert("Office saved successfully!");
                setOfficeName("");
                setOfficeNumber("");
            }

        } catch (error) {
            console.log(error);
            alert(
                error.response
                    ? "Error " + error.response.status + ": " + error.response.data
                    : "Request failed: " + error.message
            );
        }
    }

    return (
        <div>
            <h1>Save Office</h1>

            <label>Office Name:</label>
            <input
                type="text"
                value={officeName}
                onChange={e => setOfficeName(e.target.value)}
            />
            <br />

            <label>Office Number:</label>
            <input
                type="number"
                value={officeNumber}
                onChange={e => setOfficeNumber(e.target.value)}
            />
            <br />

            <ClinicButton
                ButtonLabel="SAVE OFFICE"
                actionButton={createAccount}
            />
        </div>
    );
}

export default Office;
