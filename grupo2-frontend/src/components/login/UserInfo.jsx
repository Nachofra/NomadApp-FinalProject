import React from "react";
import { useState } from "react";
import {
  userContext,
  UserProvider,
  useUserContext,
} from "../../context/UserContext";
export const UserInfo = ({ firstName, lastName, setUser }) => {
    
  function handleNameChange(e) {
    const nameInputValue = e.target.value.trim();
    const nameInputFieldName = e.target.name;
    setUser((user) => ({
        ...user,
        [nameInputFieldName]: nameInputValue,
    }))
  }
  
  return (
    <>
      <div>
        <label>
          <strong>First Name</strong>
        </label>
        <input
          type="text"
          name="firstName"
          value={firstName}
          placeholder="First Name"
          onChange={handleNameChange}
        />
      </div>
      <div>
        <label>
          <strong>Last Name</strong>
        </label>
        <input
          type="text"
          name="lastName"
          value={lastName}
          placeholder="Last Name"
          onChange={handleNameChange}
        />
      </div>
    </>
  );
};
