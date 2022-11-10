import React, { Fragment, useState, FC } from 'react'
import { Combobox, Transition } from '@headlessui/react'
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/react/24/solid'
import { DatalistItem } from './DatalistItem'

export const Datalist = ({
  data = [],
  label,
  tag,
  className,
  id,
  name,
  placeholder,
  value = '',
  flowTop,
  onChange,
  filterFunction,
  resultRenderer,
  ...props
}) => {
  const [query, setQuery] = useState('')

  const filteredTag =
    query === ''
      ? data
      : data.filter((item) =>
            item.name
            .toLowerCase()
            .replace(/\s+/g, '')
            .includes(query.toLowerCase().replace(/\s+/g, ''))
      )

  return (
    <div className="font-primary group w-full relative">
      <Combobox value={value} onChange={onChange} >
        <div className="relative w-full">
          <div className="flex flex-col-reverse items-start w-full ">
            <Combobox.Input
              className={`w-full p-2 
              text-gray-900 placeholder:text-gray-600
              rounded-md  bg-white peer ring-1 ring-gray-300
              focus:outline-none shadow-inner
              ${className}`}
              displayValue={value ? value : query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder={placeholder}
            />
            <Combobox.Button className="absolute bottom-2 right-0 flex items-center pr-2 z-50">
              <ChevronUpDownIcon
                className="h-5 w-5 text-gray-400"
                aria-hidden="true"
              />
            </Combobox.Button>
            {label && 
              <label
                htmlFor={id}
                className={`${tag ? "ml-4 peer-focus:bg-devPink-600 bg-slate-500 rounded-t-lg px-4" : "mb-1.5" } 
                py-1 text-white uppercase `}
              >
                {label}
              </label>}
          </div>
          <Transition
            as={Fragment}
            leave="transition ease-in duration-100"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
            afterLeave={() => setQuery('')}
          >
            <Combobox.Options 
            className={`${flowTop ? '-translate-y-full -top-3' : '  mt-3' }
            absolute max-h-60 md:max-h-80 w-full
            overflow-auto rounded-md z-10 divide-y divide-violet-100
            text-slate-100 placeholder:text-slate-400
               ring-1 ring-gray-300`}>
              {filteredTag.length === 0 ? (
                // && query !== ''  
                <div className="relative cursor-default select-none py-2 px-4 text-gray-800 
                rounded-md  bg-white z-10">
                  Nothing found.
                </div>
              ) : (
                filteredTag.map((item, i) => (
                  resultRenderer ? (
                    resultRenderer(item, i)
                  ) : null
                ))
              )}
            </Combobox.Options>
          </Transition>
        </div>
      </Combobox>
    </div>
  )
}
