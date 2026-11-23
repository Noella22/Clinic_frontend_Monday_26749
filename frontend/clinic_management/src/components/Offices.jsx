
import React, { useState, useEffect } from "react";
import axios from "axios";

function Offices() {
  const [offices, setOffices] = useState([]);
  const [search, setSearch] = useState("");
  const [error, setError] = useState("");

  
  async function loadOffices() {
    try {
      const response = await axios.get(
        "http://localhost:1014/api/office/all"
      );

      setOffices(response.data);
      setError("");
    } catch (error) {
      setError("Failed to load offices.");
    }
  }

  
  useEffect(() => {
    loadOffices();
  }, []);

  
  const filteredOffices = offices.filter(office =>
    (office.name || "").toLowerCase().includes(search.toLowerCase()) ||
    String(office.officeNumber).includes(search)
  );

  
  async function deleteOffice(id) {
    const confirmed = window.confirm(
      "Do you want to delete this office?"
    );

    if (!confirmed) return;

    try {
      await axios.delete(
        "http://localhost:1014/api/office/delete/" + id
      );

      alert("Office deleted successfully!");
      loadOffices();
    } catch (error) {
      alert("Failed to delete office.");
    }
  }

  return (
    <div>
      <h1>The Available Offices</h1>

      <input
        type="text"
        placeholder="Search office name or number"
        value={search}
        onChange={e => setSearch(e.target.value)}
      />

      <button onClick={loadOffices}>
        Refresh List
      </button>

      <br /><br />

      {error && <p>{error}</p>}

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>Office Name</th>
            <th>Office Number</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {filteredOffices.map(office => (
            <tr key={office.id}>
              <td>{office.name}</td>
              <td>{office.officeNumber}</td>
              <td>
                <button onClick={() => deleteOffice(office.id)}>
                  Delete
                </button>

                <button disabled>
                  Update
                </button>
              </td>
            </tr>
          ))}

          {filteredOffices.length === 0 && (
            <tr>
              <td colSpan="3">No offices found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default Offices;
