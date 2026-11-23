
import React, { useState } from 'react';
import ClinicButton from './ClinicButton';

function Authentication() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const [staffID, setStaffID] = useState('');
    const [email, setEmail] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [doYouHaveAccount, setDoYouHaveAccount] = useState(true);

    function handleLogin() {
        alert("Login clicked for: " + username);
    }

    function handleCreateAccount() {
        if (password !== confirmPassword) {
            alert("Passwords do not match");
        } else {
            alert("Create account clicked for: " + staffID);
        }
    }

    return (
        <div>

            {doYouHaveAccount ? (

                <div style={{ border: "3px solid blue", marginTop: "100px", padding: "20px" }}>

                    <h1>Sign In</h1>
                    <h3>Access your Clinic Management System</h3>

                    <label>Username:</label>
                    <input
                        type="text"
                        onChange={e => setUsername(e.target.value)}
                        placeholder="Enter your username"
                    />
                    <br />

                    <label>Password:</label>
                    <input
                        type="password"
                        onChange={e => setPassword(e.target.value)}
                    />
                    <br />

                    <div>
                        <ClinicButton
                            ButtonLabel="LOGIN"
                            actionButton={handleLogin}
                        />
                        <br />

                        <label>New here?</label>

                        <ClinicButton
                            ButtonLabel="Create an Account"
                            actionButton={() => setDoYouHaveAccount(false)}
                        />
                    </div>

                </div>

            ) : (

                <div style={{ border: "3px solid indigo", marginTop: "100px", padding: "20px" }}>

                    <h1>Create an Account</h1>
                    <h3>Clinic Management System</h3>

                    <label>Staff ID:</label>
                    <input
                        type="text"
                        onChange={e => setStaffID(e.target.value)}
                        placeholder="Enter your staff ID"
                    />
                    <br />

                    <label>Email:</label>
                    <input
                        type="email"
                        onChange={e => setEmail(e.target.value)}
                    />
                    <br />

                    <label>Password:</label>
                    <input
                        type="password"
                        onChange={e => setPassword(e.target.value)}
                    />
                    <br />

                    <label>Confirm Password:</label>
                    <input
                        type="password"
                        onChange={e => setConfirmPassword(e.target.value)}
                    />
                    <br />

                    <div>
                        <ClinicButton
                            ButtonLabel="CREATE AN ACCOUNT"
                            actionButton={handleCreateAccount}
                        />
                        <br />

                        <label>Already have an account?</label>

                        <ClinicButton
                            ButtonLabel="Sign In"
                            actionButton={() => setDoYouHaveAccount(true)}
                        />
                    </div>

                </div>

            )}

        </div>
    );
}

export default Authentication;
