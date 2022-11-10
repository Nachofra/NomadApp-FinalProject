// context to handle cursor 

import axios from 'axios';
import { createContext, useContext, useEffect, useState } from 'react'
import { FetchRoutes } from '../guard/Routes';

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
    const [filters, setFilters] = useState(emptyFilters);

    const [cities, setCities ] = useState([]);
    const [categories, setCategories ] = useState([])
    
    useEffect(() => {

      const fetchData = async () =>{
          try {
            const { data } = await axios.get(`${FetchRoutes.BASEURL}/city`);
            const { data : category } = await axios.get(`${FetchRoutes.BASEURL}/category`);

            setCities(data);
            setCategories(category)
          } catch (error) {
            console.error(error.message);
          }
        }
        fetchData();
  }, [])

    const reset = () => setFilters(emptyFilters);

    const applyCategory = (id) => {
      setFilters({...filters, category : id})
    }

    const handleDates = ( dateFrom, dateTo )=> {
      console.log(dateFrom, dateTo)
      setFilters({...filters, date:{ from:dateFrom, to:dateTo}})
  
    }
    
    return (
        <SearchContext.Provider value={{ filters, cities, categories, setFilters, reset, applyCategory, handleDates }}>
            {children}
        </SearchContext.Provider>
    )
}

