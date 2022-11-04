import React from 'react'
import { AdjustmentsHorizontalIcon } from '@heroicons/react/24/solid'
import { Popover, Transition } from '@headlessui/react'
import { CheckIcon } from '@heroicons/react/24/solid'
import { Fragment } from 'react'
import './SearchbarModal.scss'

export const DeskFiltersPanel = ({children}) => {
  return (
    <div className="relative">
    <Popover className="relative">
      {({ open, close }) => (
        <>
          <Popover.Button
            className='px-3 py-2 rounded-lg 
            flex items-center
            bg-violet-700
            text-white font-semibold shadow-md focus:outline-none'
          >
            <AdjustmentsHorizontalIcon className='w-6 h-6 ' />
            <p className='mx-2'>Filter</p>
          </Popover.Button>
          <Transition
            as={Fragment}
            enter="transition ease-out duration-300 transform"
            enterFrom="opacity-0 translate-y-1"
            enterTo="opacity-100 translate-y-0"
            leave="transition ease-in duration-150"
            leaveFrom="opacity-100 translate-y-0"
            leaveTo="opacity-0 translate-y-1"
          >
            <Popover.Panel className="absolute left-1/2 -top-10
            transform -translate-y-[100%] -translate-x-1/2
            flex-col items-start justify-start circleBtnModel
            px-4 pt-4 pb-10 rounded-lg shadow-md bg-white">
                {children}
                <button onClick={close}
                className="absolute shadow-md w-[3.25rem] h-[3.25rem] bg-violet-700 z-10
                rounded-full left-1/2 bottom-0 -translate-x-1/2 translate-y-1/2
                flex items-center justify-center">
                  <CheckIcon className='w-8 text-white' />
                </button>
            </Popover.Panel>
          </Transition>
        </>
      )}
    </Popover>
  </div>
  )
}
