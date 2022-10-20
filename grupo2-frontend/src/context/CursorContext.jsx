// context to handle cursor 

import { createContext, useContext, useState } from 'react'
import useWindowSize from '../hooks/useWindowSize'
import Cursor from '../components/cursor/Cursor';

export const CursorContext = createContext(null)

export const useCursorContext = () => useContext(CursorContext);

export const CursorProvider = ({ children }) => {
    const [cursorVariant, setCursorVariant] = useState('default')
    
    const setCursor = (variant) => {
        setCursorVariant(variant)
    }

    const {height, width} = useWindowSize();
    
    
    return (
        <CursorContext.Provider value={{ cursorVariant, setCursor }}>
            {children}
            {height && width && height > 600 && width > 1200 && 
                <Cursor cursorVariant={cursorVariant}  />
            }
        </CursorContext.Provider>
    )
}

