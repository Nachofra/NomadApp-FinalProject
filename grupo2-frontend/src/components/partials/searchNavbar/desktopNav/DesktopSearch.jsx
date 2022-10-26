import { AdjustmentsHorizontalIcon, ArrowPathIcon, MagnifyingGlassIcon } from '@heroicons/react/24/solid'
import { MapPinIcon, QuestionMarkCircleIcon, UserGroupIcon, CalendarDaysIcon } from '@heroicons/react/24/outline'
import React, { useState } from 'react'
import { useSearchContext } from '../../../../context/SearchContext'
import DeskSearchbarModal from './components/DeskSearchbarModal'
import { Calendar } from '../../../Calendar/Calendar'

export const DesktopSearch = () => {

  const {filters, setFilters, reset} = useSearchContext()

  const handleDateFrom = (date)=> {
    setFilters({...filters, date:{...filters.date, from:date}})

  }
  const handleDateTo = (date)=> {
    setFilters({...filters, date:{...filters.date, to:date}})
}

console.log(filters)

  return (
    <section className='w-screen fixed bottom-4 flex items-center justify-center'> 
      <div
        className={`w-[94vw] max-w-[900px]  
        shadow-xl cursor-pointer h-auto relative
        flex justify-center items-center p-6
        rounded-xl bg-shape-navbar
        ring-1 ring-violet-700 ring-opacity-5`}
      > 

        <DeskSearchbarModal
          active={false}
          icon={<MapPinIcon className='w-7 h-7 text-violet-700' />}
          text="Córdoba, Argentina"
          placeholder="Searching any place"
        >
          
        </DeskSearchbarModal>
        <DeskSearchbarModal
          active={filters.date.from !== null | filters.date.to !== null}
          icon={<CalendarDaysIcon className='w-7 h-7 text-violet-700' />}
          text={``}
          placeholder="Any date - any date"
        >
          <div className='h-full w-16 p-6'>
            <p className='absolute flex top-6 left-6
            origin-top-right -translate-x-full -rotate-90
            text-2xl text-violet-700 tracking-widest font-bold uppercase'>Calendar</p>
          </div>
          <Calendar
            startDate={filters.date.from}
            endDate={filters.date.to}
            setStartDate={handleDateFrom}
            setEndDate={handleDateTo}
            monthsDisplayed={2}
          />
        </DeskSearchbarModal>

        <DeskSearchbarModal
          active={false}
          icon={<UserGroupIcon className='w-7 h-7 text-violet-700' />}
          text="Córdoba, Argentina"
          placeholder="Any guests - no pets"
        >
          <div className='h-full w-32'>
            <p className='absolute flex top-6 left-6
            origin-top-right -translate-x-full -rotate-90
            text-2xl text-violet-700 tracking-widest font-bold uppercase'>Guests</p>
          </div>
        </DeskSearchbarModal>
        <div 
          className="flex flex-row justify-center items-center
          border border-violet-700 w-12 h-12 rounded-md ml-2"
          >
              <MagnifyingGlassIcon className="w-6 h-6 text-violet-700" />
        </div>
        <div className='flex items-center absolute -left-4 z-10 -translate-x-full'>
          <button
            className='px-3 py-2 rounded-lg 
            flex items-center
            bg-violet-700 shadow-violet-400/50
            text-white font-semibold shadow-md'
          >
            <AdjustmentsHorizontalIcon className='w-6 h-6 ' />
            <p className='mx-2'>Filter</p>
          </button>
          <ArrowPathIcon onClick={reset} className='text-violet-700 w-6 h-6 ml-4' />
        </div>
        <QuestionMarkCircleIcon 
        className='absolute -right-4 translate-x-full
        w-6 h-6 text-violet-700' 
        />
      </div>
    </section>
  )
}