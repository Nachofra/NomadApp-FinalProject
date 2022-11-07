import React from 'react'
import { motion } from 'framer-motion'
import { CheckIcon } from '@heroicons/react/24/solid'
import { MobileNavModal } from './MobileNavModal'
import { useSearchContext } from '@/context/SearchContext'
import { Datalist, DatalistItem } from '../../../datalist'
import listBlock from '@/staticJSON/listBlock.json'
import { LocationDatalist } from '../components/LocationDatalist'
import { CollapsableMenu } from '../../../collapsableMenu/CollapsableMenu'
import { Calendar } from '../../../Calendar/Calendar'
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
      <CollapsableMenu
        question='Find locations'
      >
        <div className='p-2 my-4'>
        <LocationDatalist />
        </div>
      </CollapsableMenu>
      <CollapsableMenu
        question='Search by week'
      >
        <div className='p-2 my-4 flex items-center justify-center'>
        <Calendar
          // startDate={filters.date.from}
          // endDate={filters.date.to}
          // setStartDate={handleDateFrom}
          // setEndDate={handleDateTo}
          monthsDisplayed={1}
        />
        </div>
      </CollapsableMenu>
    </MobileNavModal>

    )
}
