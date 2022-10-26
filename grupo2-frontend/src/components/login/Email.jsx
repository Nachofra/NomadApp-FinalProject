import React from "react";
import { useState } from "react";

function Email({ email, setUser }) {
  const [error, setError] = useState(null);
  function emailValidation() {
    const regex =
      /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (!email || regex.test(email) === false) {
      setError("Ingrese un correo válido");
      return false;
    }else{
      setError(null)
      return true;
    }
  }
  function handleChange(e) {
    setUser((user) => {
      return {
        ...user,
        email: e.target.value,
      };
    });
    emailValidation();
  }
  return (
    <div>
      <label>
        <strong>Email</strong>
      </label>
      <input type="email" name="email" value={email} onChange={handleChange} />
      <span>{error}</span>
    </div>
  );
}
export default Email;
