import { ChevronDownIcon } from '@heroicons/react/24/solid'
import { AnimatePresence, motion } from 'framer-motion'
import React, { useState } from 'react'

export const CollapsableMenu = (props) => {

    const [isOpen, setIsOpen] = useState(false)
  return (
    <button
      className="flex flex-col text-left w-full p-3 rounded-lg"
    >
      <div 
        className="flex justify-between items-center w-full"
        onClick={() => setIsOpen((prev) => !prev)}
      >
        <div className="text-lg font-semibold">{props.question}</div>
        <AnimatePresence initial={false} mode="wait">
          <motion.div
            initial={{
              rotate: !isOpen ? 0 : 180,
            }}
            animate={{
              zIndex: 1,
              rotate: !isOpen ? 0 : 180,
              transition: {
                type: "tween",
                duration: 0.15,
                ease: "circOut",
              },
            }}
            exit={{
              zIndex: 0,
              rotate: !isOpen ? 0 : 180,
              transition: {
                type: "tween",
                duration: 0.15,
                ease: "circIn",
              },
            }}
          >
            <ChevronDownIcon className="w-6 h-6" />
          </motion.div>
        </AnimatePresence>
      </div>
      <AnimatePresence>
        {isOpen && (
          <motion.div
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
            key={props.answer}
            className="text-lg font-light"
          >
            {props.children}
          </motion.div>
        )}
      </AnimatePresence>
    </button>
  )
}
