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

function IconOne() {
  return (
    <svg
      width="48"
      height="48"
      viewBox="0 0 48 48"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect width="48" height="48" rx="8" fill="#FFEDD5" />
      <path
        d="M24 11L35.2583 17.5V30.5L24 37L12.7417 30.5V17.5L24 11Z"
        stroke="#FB923C"
        strokeWidth="2"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M16.7417 19.8094V28.1906L24 32.3812L31.2584 28.1906V19.8094L24 15.6188L16.7417 19.8094Z"
        stroke="#FDBA74"
        strokeWidth="2"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M20.7417 22.1196V25.882L24 27.7632L27.2584 25.882V22.1196L24 20.2384L20.7417 22.1196Z"
        stroke="#FDBA74"
        strokeWidth="2"
      />
    </svg>
  )
}

function IconTwo() {
  return (
    <svg
      width="48"
      height="48"
      viewBox="0 0 48 48"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect width="48" height="48" rx="8" fill="#FFEDD5" />
      <path
        d="M28.0413 20L23.9998 13L19.9585 20M32.0828 27.0001L36.1242 34H28.0415M19.9585 34H11.8755L15.9171 27"
        stroke="#FB923C"
        strokeWidth="2"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M18.804 30H29.1963L24.0001 21L18.804 30Z"
        stroke="#FDBA74"
        strokeWidth="2"
      />
    </svg>
  )
}

function IconThree() {
  return (
    <svg
      width="48"
      height="48"
      viewBox="0 0 48 48"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect width="48" height="48" rx="8" fill="#FFEDD5" />
      <rect x="13" y="32" width="2" height="4" fill="#FDBA74" />
      <rect x="17" y="28" width="2" height="8" fill="#FDBA74" />
      <rect x="21" y="24" width="2" height="12" fill="#FDBA74" />
      <rect x="25" y="20" width="2" height="16" fill="#FDBA74" />
      <rect x="29" y="16" width="2" height="20" fill="#FB923C" />
      <rect x="33" y="12" width="2" height="24" fill="#FB923C" />
    </svg>
  )
}
