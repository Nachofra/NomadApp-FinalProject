import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './app.css'
import { useCursorContext, CursorProvider } from './context/CursorContext'
import { SearchNavbar } from './components/searchNavbar/SearchNavbar'

function App() {

  // use cursor context
  const { cursorVariant, setCursor } = useCursorContext()
  
  const textEnter = () => setCursor('plus')
  const textLeave = () => setCursor('default')

  return (
    <div className="flex flex-col items-center">
      <SearchNavbar />


    </div>
  )
}

export default App
