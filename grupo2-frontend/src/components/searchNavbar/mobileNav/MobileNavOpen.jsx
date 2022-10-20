import React from 'react'
import { AnimatePresence, motion } from 'framer-motion'
import { CollapsableMenu } from '../../collapsableMenu/CollapsableMenu'

export const MobileNavOpen = () => {
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
                <div className='flex items-center space-x-2 py-4'>
                    <input type='number' 
                        className=' max-w-[130px] py-2 px-3
                        rounded-lg ring-1 ring-gray-400 
                        focus:ring-violet-700 active:ring-violet-700 
                        placeholder:text-gray-600 focus:outline-none'
                        placeholder='$ min. price'
                    />
                    <span className='text-gray-800'>to</span>
                    <input type='number' 
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

            </CollapsableMenu>
            <CollapsableMenu
                question='Rooms and beds'
            >
            </CollapsableMenu>
            <button 
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
