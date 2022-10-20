// context to handle cursor 

import { createContext, useContext, useState } from 'react'
import { SearchNavbar } from '../components/searchNavbar/SearchNavbar';

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
        guests: null,
        bathrooms: null,
        rooms: null,
        beds: null,
        category: {
          apartment: null,
          house: null,
          cottage: null,
          deluxe: null
        }
      }

export const SearchProvider = ({ children }) => {
    const [filters, setFilters] = useState(emptyFilters)

      const reset = () => setFilters(emptyFilters)
    
    return (
        <SearchContext.Provider value={{ filters, setFilters, reset }}>
            {children}
            <SearchNavbar />
        </SearchContext.Provider>
    )
}

