// import { useState } from 'react'
import { SearchProvider } from './context/SearchContext'
import { Auth, Home, Product } from './views'
import { RouteNotFound } from './utilities/RouteNotFound'
import { AuthGuard } from './guard/AuthGuard'
import { Route } from 'react-router-dom'
import { PublicRoutes } from './guard/Routes'
import './App.css'


function App() {
  return (
    <RouteNotFound>
      <Route path={PublicRoutes.HOME} element={<Home />} />
      <Route path={PublicRoutes.AUTH} element={<Auth />} />
      <Route path={PublicRoutes.PRODUCT} element={<Product />} />
      
      <Route element={<AuthGuard />}></Route>
  </RouteNotFound>
  )
}

export default App
