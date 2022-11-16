import React from 'react'
import { MobileNavModal } from './MobileNavModal'
import { useSearchContext } from '@/context/SearchContext'
import { LocationDatalist } from '../components/LocationDatalist'
import { CollapsableMenu } from '../../../collapsableMenu/CollapsableMenu'
import { Calendar } from '../../../Calendar/Calendar'
export const MobileNavOpen = ({open, setOpen}) => {

    const {filters, setFilters, reset, handleDates} = useSearchContext()
    
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
          startDate={filters.date.from}
          endDate={filters.date.to}
          // setStartDate={handleDateFrom}
          // setEndDate={handleDateTo}
          afterChange={(dateFrom, dateTo) => { handleDates(dateFrom, dateTo)}}
          monthsDisplayed={1}
        />
        </div>
      </CollapsableMenu>
    </MobileNavModal>

    )
}
