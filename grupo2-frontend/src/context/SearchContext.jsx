// context to handle cursor 

import { createContext, useContext, useState } from 'react'

export const SearchContext = createContext(null)

export const useSearchContext = () => useContext(SearchContext);

export const emptyFilters = 
    {
        location: null,
        date: {
          from: null,
          to: null,
        },
        price: {
            min: '',
            max: '',
        },
        guests: {
          adults: undefined,
          children: undefined,
          babies: undefined
        },
        bathrooms: null,
        rooms: null,
        beds: null,
        category: null
      }

export const SearchProvider = ({ children }) => {
    const [filters, setFilters] = useState(emptyFilters)

      const reset = () => setFilters(emptyFilters)

      const applyCategory = (id) => {
        setFilters({...filters, category : id})
      }
    
    return (
        <SearchContext.Provider value={{ filters, setFilters, reset, applyCategory }}>
            {children}
        </SearchContext.Provider>
    )
}

