import React from 'react'
import { MagnifyingGlassIcon } from '@heroicons/react/24/solid'
import { motion } from 'framer-motion'

export const MobileNav = () => {
  return (

    <motion.div
        className='flex'
        initial={{
        height: 0,
        opacity: 0,
        }}
        animate={{
        height: "auto",
        opacity: 1,
        }}
        exit={{
        height: 0,
        opacity: 0,
        }}
    >
    
        <div 
        className="flex flex-row justify-center items-center
        border border-violet-700 w-12 h-12 rounded-md"
        >
            <MagnifyingGlassIcon className="w-6 h-6 text-violet-700" />
        </div>
        <div className="flex flex-col justify-center ml-4">
            <p className=' font-medium' >Search</p>
            <div className='flex items-center text-sm space-x-1'>   
                <p className=' text-gray-800 
                max-w-[10ch] text-ellipsis whitespace-nowrap overflow-hidden' >
                    By location
                </p>
                <span className='w-0.5 h-3 bg-violet-700 rounded-full' />
                <p className=' text-gray-800' >Any date</p>
                <span className='w-0.5 h-3 bg-violet-700 rounded-full' />
                <p className=' text-gray-800' >Any guest</p>
            </div>
        </div>

    </motion.div>
  )
}
