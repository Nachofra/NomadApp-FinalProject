import { Combobox } from '@headlessui/react'
import { CheckIcon } from '@heroicons/react/24/solid'
import React, {FC} from 'react'


export const DatalistItem  = ({
    className,
    activeClassName,
    inactiveClassName,
    name, 
    icon,
    id,
    value
}) => {
  return (
    <Combobox.Option
      className={`text-gray-900 bg-white relative 
      cursor-default select-none py-2 px-4
      hover:bg-gray-50 ${className}`}
      value={value}
    >
      <span
        className={`flex truncate `}
      >
        {icon && 
          <img src={icon} className="h-5 w-5 mr-3" aria-hidden="true" />
        }
        {name}
      </span>
    </Combobox.Option>
  )
}
