import { Popover, Transition } from '@headlessui/react'
import { ChevronDownIcon } from '@heroicons/react/20/solid'
import { CheckBadgeIcon, CheckIcon, UserGroupIcon } from '@heroicons/react/24/solid'
import { Fragment } from 'react'
import { Link } from 'react-router-dom'
import './NavBarModal.scss'

export default function DeskSearchbarModal({active, placeholder, route}) {
  return (
    <div className="relative">
      <Link to={route}
        className={`mr-4
          group inline-flex items-center rounded-md  text-base
            hover:text-opacity-100 focus:outline-none focus-visible:ring-2 
            focus-visible:ring-white focus-visible:ring-opacity-75
            ${active ? 'bg-violet-700 ' : 'bg-gray-100'}`}
      >   
          <div className='bg-violet-200 hover:bg-violet-300 w-32 h-12
          flex items-center justify-center rounded-xl
          transition ease-in-out delay-150 hover:-translate-y-1 hover:scale-110 duration-300'>
            {placeholder}
          </div>
      </Link>
    </div>
  )
}
