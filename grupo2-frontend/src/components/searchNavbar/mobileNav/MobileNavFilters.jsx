import React from 'react'
import {motion} from 'framer-motion'

export const MobileNavFilters = () => {
  return (
    <motion.div
    className='w-full h-full overflow-hidden
    rounded-tl-xl rounded-bl-xl rounded-tr-xl rounded-br-[10rem]'
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
        <div className='flex flex-col justify-center w-full overflow-y-auto scrollbar-none h-screen max-h-[70vh]'>
            <div className=' w-full px-2  py-4'>

            </div>
        </div>

    </motion.div>
  )
}
