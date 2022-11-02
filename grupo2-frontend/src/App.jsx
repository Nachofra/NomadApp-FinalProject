// import { useState } from 'react'
import { SearchProvider } from './context/SearchContext'
import { Auth, Home } from './views'
import { SignIn } from './views/auth/SignIn'
import { SignUp } from './views/auth/SignUp'
import { RouteNotFound } from './utilities/RouteNotFound'
import { AuthGuard } from './guard/AuthGuard'
import './app.css'
import { Route } from 'react-router-dom'
import { PublicRoutes } from './guard/routes'

function App() {
  return (
    <RouteNotFound>
      <Route path={PublicRoutes.HOME} element={<Home />} />
      <Route path={PublicRoutes.AUTH} element={<Auth />} />
      <Route path={PublicRoutes.LOGIN} element={<SignIn />} />
      <Route path={PublicRoutes.REGISTER} element={<SignUp />} />
      
      <Route element={<AuthGuard />}></Route>
  </RouteNotFound>
  )
}

export default App
