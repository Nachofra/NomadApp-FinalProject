import React from "react";
import { useState } from "react";

function Email({ email, setUser }) {
  const [error, setError] = useState(null);
  function emailValidation() {
    const regex =
      /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (!email || regex.test(email) === false) {
      setError("Enter a valid email");
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
    <div className="w-full mt-4">
      <input className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-400 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300" type="email" name="email" value={email} onChange={handleChange} placeholder="Email" />
      <span>{error}</span>
    </div>
  );
}
export default Email;
