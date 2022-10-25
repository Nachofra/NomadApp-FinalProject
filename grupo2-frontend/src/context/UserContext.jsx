import React, { createContext, useContext, useState } from 'react'

const userContext = createContext(null)

const useUserContext = () => useContext(userContext)

function UserProvider ({ children }) {
  const [user, setUser] = useState(null)
  function login (user) {
    setUser(user)
  }
  function logout () {
    setUser(null)
  }
  function update (newUser) {
    setUser(prevUser => ({
      ...(prevUser),
      ...newUser
    }))
  }

  return (
    <userContext.Provider
      value={{
        user,
        login,
        logout,
        update
      }}
    >
      {children}
    </userContext.Provider>
  )
}

export { userContext, UserProvider, useUserContext }
