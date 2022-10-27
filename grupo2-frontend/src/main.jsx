import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import App from './App'
import { SearchProvider } from './context/SearchContext'
import { UserProvider } from './context/UserContext'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <UserProvider>
        <SearchProvider>
              <App />
        </SearchProvider>
      </UserProvider>
    </BrowserRouter>
  </React.StrictMode>
)
