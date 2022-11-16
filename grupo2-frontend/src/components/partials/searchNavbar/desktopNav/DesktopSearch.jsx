import React from 'react'
import {  ArrowPathIcon, MagnifyingGlassIcon } from '@heroicons/react/24/solid'
import { MapPinIcon, UserGroupIcon, CalendarDaysIcon } from '@heroicons/react/24/outline'
import { useSearchContext } from '../../../../context/SearchContext'
import DeskSearchbarModal from './components/DeskSearchbarModal'
import { Calendar } from '../../../Calendar/Calendar'
import { DeskFiltersPanel } from './components/DeskFiltersPanel'
import { PropertyTypeSelect } from '../components/PropertyTypeSelect'
import { LocationDatalist } from '../components/LocationDatalist'
import { AnimatePresence, motion } from 'framer-motion'
export const DesktopSearch = ({hide}) => {

  const {filters, 
    categories,
    setFilters, 
    reset, 
    applyCategory,
    handleDates } = useSearchContext()

Date.prototype.formatMMDDYYYY = function(){
  return (this.getMonth() + 1) + 
  "/" +  this.getDate() +
  "/" +  this.getFullYear();
}

const handleDateFormat = date => date? date.formatMMDDYYYY() : 'Any';

  return (
    <AnimatePresence initial={false} mode="wait">
    {!hide && 
    <motion.section
    initial={{ y: -100, opacity: 0 }}
    animate={{ y: 0, opacity: 1 }}
    exit={{ y: 100, opacity: 0 }}
    className='w-screen fixed bottom-4 flex items-center justify-center'> 
      <div
        className={`w-[94vw] max-w-[900px]  
        shadow-xl cursor-pointer h-auto relative
        flex justify-center items-center p-6
        rounded-xl bg-shape-navbar gap-4
        ring-1 ring-violet-700 ring-opacity-5`}
      > 
        <DeskSearchbarModal
          active={filters.location !== null}
          icon={<MapPinIcon className='shrink-0 w-7 h-7 text-violet-700' />}
          text={`${filters.location?.name},  ${filters.location?.country.name}`}
          placeholder="Searching any place"
        >
          <div className='w-96 flex flex-col items-center'>
            <p className='text-violet-700 font-semibold uppercase text-3xl'>Find places</p>
            <p className='mb-4 text-lg text-gray-600'>Select one from the list</p>
          <LocationDatalist flowTop />
          </div>
        </DeskSearchbarModal>
        <DeskSearchbarModal
          active={filters.date.from !== null | filters.date.to !== null}
          icon={<CalendarDaysIcon className='w-7 h-7 text-violet-700' />}
          text={`${handleDateFormat(filters.date.from)} - ${handleDateFormat(filters.date.to)}`}
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
            monthsDisplayed={2}
            afterChange={(dateFrom, dateTo) => { handleDates(dateFrom, dateTo)}}
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
          <DeskFiltersPanel>
            <h2 className='text-center text-2xl'>Filters</h2>
            <div className='flex items-center justify-start
              pt-4 overflow-auto  snap-x snap-mandatory  scrollbar-none'>
                <div className="flex space-x-6 snap-x snap-mandatory">
                  {categories.map((category, i) => (
                    <PropertyTypeSelect
                        key={i}
                        name={category.title}
                        illustration={category.categoryIllustration.url}
                        onClick={() => applyCategory(category.id)}
                        selected={filters?.category === category.id} 
                    />
                    ))}
                </div>
            </div>
          </DeskFiltersPanel>
          <ArrowPathIcon onClick={reset} className='text-violet-700 w-6 h-6 ml-4' />
        </div>
        {/* <QuestionMarkCircleIcon 
        className='absolute -right-4 translate-x-full
        w-6 h-6 text-violet-700' 
        /> */}
      </div>
    </motion.section>
    }</AnimatePresence>  
  )
}
