import React, { useState } from 'react'
import { AdjustmentsHorizontalIcon, XMarkIcon } from '@heroicons/react/24/solid'
import './searchNavbar.scss'
import { MobileNav } from './mobileNav/MobileNav'
import { AnimatePresence, motion } from 'framer-motion'
import { MobileNavOpen } from './mobileNav/MobileNavOpen'
export const SearchNavbar = () => {

  const [open, setOpen] = useState(false)

  return (
    <AnimatePresence initial={false} mode="wait">
      <div 
        onClick={() => {if (!open) {setOpen(true)}}}
        className={`transition-all w-[94vw] max-w-[900px]  
        fixed bottom-4 shadow-xl bg-shape-navbar 
        flex flex-col justify-start items-start p-2
        rounded-tl-xl rounded-bl-xl rounded-tr-xl rounded-br-[10rem]`}
      > 
          <AnimatePresence initial={false} mode="wait">
            {!open &&  
                <MobileNav /> 
            }
          </AnimatePresence>
          <AnimatePresence initial={false} mode="wait">
            {open &&  
                <MobileNavOpen /> 
            }
          </AnimatePresence>

          <button 
            onClick={() => setOpen(false)}
            className='w-12 h-12 p-2 rounded-full 
            bg-violet-700 shadow-lg shadow-violet-400/20
            absolute bottom-1 right-0'
          >
            {!open ?
            <AdjustmentsHorizontalIcon className='text-white'/> :
            <XMarkIcon className='text-white'/>}
          </button>
          
          <AnimatePresence initial={false} mode="wait">
            {open &&  
              <h3
                className='absolute top-0 left-0 -translate-y-full
                text-gray-800 font-medium text-4xl'
              >
                Search
              </h3>
            }
          </AnimatePresence>
      </div>
    </AnimatePresence>

  )
}
