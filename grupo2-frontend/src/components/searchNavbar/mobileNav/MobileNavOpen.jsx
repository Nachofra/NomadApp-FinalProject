import React from 'react'
import { AnimatePresence, motion } from 'framer-motion'
import { CollapsableMenu } from '../../collapsableMenu/CollapsableMenu'
import { PropertyTypeSelect } from '../components/PropertyTypeSelect'
import { useSearchContext } from '../../../context/SearchContext'
import { CountSelect } from '../components/CountSelect'

export const MobileNavOpen = () => {

    const {filters, setFilters, reset} = useSearchContext()
    // const {category, price, date, guests, location} = filters
  
  return (
    <motion.div
        className='flex flex-col justify-center h-full w-full'
        initial={{
            height: 0,
            opacity: 0,
        }}
        animate={{
            height: "auto",
            opacity: 1,
            transition: {
               height: {
                    duration: 0.4,
               },
                opacity: {
                    duration: 0.25,
                    delay: 0.15,
              },
            },
        }}
        exit={{
            height: 0,
            opacity: 0,
            transition: {
               height: {
                  duration: 0.4,
              },
               opacity: {
                   duration: 0.25,
               },
           },
        }}
    >
        <div className='h-screen max-h-[70vh] w-full'>
            
            <CollapsableMenu
                question='Price range (per nigth)'
            >
                <div className='flex items-center space-x-2 py-4 ml-0.5'>
                    <input 
                        type='number'
                        onChange={(e) => setFilters({...filters, price: {...filters.price, min: e.target.value}})}
                        value={filters.price.min}
                        className=' max-w-[130px] py-2 px-3
                        rounded-lg ring-1 ring-gray-400 
                        focus:ring-violet-700 active:ring-violet-700 asd
                        placeholder:text-gray-600 focus:outline-none'
                        placeholder='$ min. price'
                    />
                    <span className='text-gray-800'>to</span>
                    <input 
                        type='number' 
                        onChange={(e) => setFilters({...filters, price: {...filters.price, max: e.target.value}})}
                        value={filters.price.max}
                        className=' max-w-[130px] py-2 px-3
                        rounded-lg ring-1 ring-gray-400 
                        focus:ring-violet-700 active:ring-violet-700 
                        placeholder:text-gray-600 focus:outline-none'
                        placeholder='$ max. price'
                    />
                </div>
            </CollapsableMenu>

            <CollapsableMenu
                question='Property type'
            >
                <div className='flex items-center justify-start
                pt-4 overflow-auto  snap-x snap-mandatory'>
                    <div className="flex space-x-6 snap-x snap-mandatory">
                        <PropertyTypeSelect
                            name='Apartment'
                            illustration='/illustrations/common_building.svg'
                            onClick={() => setFilters (prev => ({...prev, category: {
                                ...prev.category, apartment: !prev.category.apartment
                                }
                            }))}
                            selected={filters?.category.apartment} 
                        />
                        <PropertyTypeSelect
                            name='House'
                            illustration='/illustrations/house_wide.svg'
                            onClick={() => setFilters (prev => ({...prev, category: {
                                ...prev.category, house: !prev.category.house
                                }
                            }))}
                            selected={filters?.category.house} 
                        />
                        <PropertyTypeSelect
                            name='Cottage'
                            illustration='/illustrations/forest_house.svg'
                            onClick={() => setFilters (prev => ({...prev, category: {
                                ...prev.category, cottage: !prev.category.cottage
                                }
                            }))}
                            selected={filters?.category.cottage} 
                        />
                        <PropertyTypeSelect
                            name='Deluxe'
                            illustration='/illustrations/modern_house.svg'
                            onClick={() => setFilters (prev => ({...prev, category: {
                                ...prev.category, deluxe: !prev.category.deluxe
                                }
                            }))}
                            selected={filters?.category.deluxe} 
                        />
                    </div>
                </div>
            </CollapsableMenu>
            <CollapsableMenu
                question='Rooms and beds'
            >
                <h5 className='text-lg font-semibold text-gray-600 mt-5'>Rooms</h5>
                <CountSelect 
                    value={filters.rooms}
                    setValue={(value) => setFilters({...filters, rooms: value})}
                />
                <h5 className='text-lg font-semibold text-gray-600 mt-1'>Beds</h5>
                <CountSelect 
                    value={filters.beds}
                    setValue={(value) => setFilters({...filters, beds: value})}
                />
                <h5 className='text-lg font-semibold text-gray-600 mt-1'>Bathrooms</h5>
                <CountSelect 
                    value={filters.bathrooms}
                    setValue={(value) => setFilters({...filters, bathrooms: value})}
                />
            </CollapsableMenu>
            <button
                onClick={reset}
                className='px-3 py-1 p-2 rounded-lg 
                absolute bottom-3 left-2
                bg-white text-violet-700 border-2 border-violet-700 font-semibold'
            >
                Clear all
            </button>
            
        </div>
        
    </motion.div>

    )
}
