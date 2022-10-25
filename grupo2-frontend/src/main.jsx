import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import App from './App'
import { CursorProvider } from './context/CursorContext'
import { SearchProvider } from './context/SearchContext'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <SearchProvider>
        <CursorProvider>
            <App />
        </CursorProvider>
      </SearchProvider>
    </BrowserRouter>
  </React.StrictMode>
)
