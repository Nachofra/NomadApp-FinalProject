import { Popover, Transition } from '@headlessui/react'
import { ChevronDownIcon } from '@heroicons/react/20/solid'
import { CheckBadgeIcon, CheckIcon, UserGroupIcon } from '@heroicons/react/24/solid'
import { Fragment } from 'react'
import './SearchbarModal.scss'

export default function DeskSearchbarModal({children, text, icon, active, placeholder}) {
  return (
    <div className="relative">
      <Popover className="relative">
        {({ open, close }) => (
          <>
            <Popover.Button
              className={`mr-4
                group inline-flex items-center rounded-md  text-base
                 hover:text-opacity-100 focus:outline-none focus-visible:ring-2 
                 focus-visible:ring-white focus-visible:ring-opacity-75
                 ${active ? 'bg-violet-700 ' : 'bg-gray-100'}`}
            >   
                <div className='bg-violet-200 w-12 h-12 
                flex items-center justify-center rounded-l-md'>
                  {icon}
                </div>
                <p className={`${active ? 'text-white' : 'text-gray-800'} 
                text-[14px] px-3 min-w-[180px] text-left `}>
                  {active ? text : placeholder}
                </p>
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
              flex items-start justify-start circleBtnModel
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


