import React, { createContext, useContext, useState } from "react";

const userContext = createContext(null);

const useUserContext = () => useContext(userContext);

function UserProvider({ children }) {
  const [registeredUser, setRegisteredUser ] = useState(null)
  const [user, setUser] = useState(null);

  function register(user){
    setRegisteredUser(user)
  }

  function login() {
    setUser(registeredUser);
  }
  function logout() {
    setUser(null);
  }
  function update(newUser) {
    setUser((prevUser) => ({
      ...prevUser,
      ...newUser,
    }));
  }

  return (
    <userContext.Provider
      value={{
        user,
        registeredUser,
        login,
        register,
        logout,
        update,
      }}
    >
      {children}
    </userContext.Provider>
  );
}
export { userContext, UserProvider, useUserContext };
