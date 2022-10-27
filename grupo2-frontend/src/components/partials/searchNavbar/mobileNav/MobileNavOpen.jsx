import React from 'react'
import { motion } from 'framer-motion'
import { CheckIcon } from '@heroicons/react/24/solid'
import { MobileNavModal } from './MobileNavModal'
import { useSearchContext } from '@/context/SearchContext'
import { Datalist, DatalistItem } from '../../../datalist'
import listBlock from '@/staticJSON/listBlock.json'
import { LocationDatalist } from '../components/LocationDatalist'
export const MobileNavOpen = ({open, setOpen}) => {

    const {filters, setFilters, reset} = useSearchContext()
    // const {category, price, date, guests, location} = filters
    // "location": {
    //   "id": 1,
    //   "adress": "Calle Falsa",
    //   "number": 123,
    //   "floor": 10,
    //   "apartment": "A",
    //   "city": {
    //     "id": 1,
    //     "name": "Springfield",
    //     "province": {
    //       "id": 1,
    //       "name": "Awckland",
    //       "country": { "id": 1, "name": "New Zealand" }
    //     }
    //   }
    // }


  return (
    
    <MobileNavModal open={open} setOpen={setOpen} >
      <LocationDatalist />
    </MobileNavModal>

    )
}
