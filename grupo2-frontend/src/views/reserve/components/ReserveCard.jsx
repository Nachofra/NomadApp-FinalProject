import { MapPinIcon, StarIcon } from '@heroicons/react/24/outline'
import React from 'react'

export const ReserveCard = ({data, dates, handleSubmit}) => {

    Date.prototype.formatMMDDYYYY = function(){
        return (this.getMonth() + 1) + 
        "/" +  this.getDate() +
        "/" +  this.getFullYear();
    }
        
    const handleDateFormat = date => date? date.formatMMDDYYYY() : 'Any';

  return (
    <article className='flex flex-col justify-start items-start
    bg-white rounded-xl border-2 border-violet-700 grow
    sticky top-56  max-w-md overflow-hidden shadow-lg'>
        <h3 className='text-2xl  text-gray-700
          font-medium text-gray-700mb-4 pt-4 pb-2 px-6'>
            Reservation details</h3>
        <div className='p-3 border-t-2 border-b-2 border-violet-700'>
            <img 
            src={data.images[0].url} 
            alt={data.images[0].name}
            className="w-full max-h-80 object-cover rounded-lg overflow-hidden"
            />
        </div>
        <div className='flex flex-col p-4 w-full'>
            <p className='uppercase text-gray-600 font-thin '>{data.category.title}</p>
            <p className='text-xl md:text-2xl text-violet-700 font-medium 
            overflow-ellipsis whitespace-nowrap overflow-hidden 
            max-w-[20ch] sm:max-w-[25ch]'>{data.title}</p>
            <div className='flex items-center mt-1 mb-2'>
                <StarIcon className='w-6 h-6 text-violet-700' />
                <StarIcon className='w-6 h-6 text-violet-700' />
                <StarIcon className='w-6 h-6 text-violet-700' />
                <StarIcon className='w-6 h-6 text-violet-700' />
                <StarIcon className='w-6 h-6 text-gray-500' />
            </div>
            <div className='flex items-center text-gray-700 max-w-[100vw]'>
            <MapPinIcon className='w-6 h-6' />
            <div className='ml-2 flex items-center text-sm md:text-base'>
                <p className='overflow-ellipsis whitespace-nowrap overflow-hidden 
                max-w-[25ch] sm:max-w-none'>{data.city.name},
                <span className='ml-1 font-medium text-gray-900'>{data.city.country.name}</span>
                </p>
            </div>
        </div>
        <div className='flex items-center justify-between mt-6 
        pt-3 border-t-2 border-violet-700'>
            <p className='text-lg font-medium text-gray-700'>Check In</p>
            <p className='text-lg'>{handleDateFormat(dates.from)}</p>
        </div>
        <div className='flex items-center justify-between mt-4 mb-8
        pb-2 border-b-2 border-violet-700'>
            <p className='text-lg font-medium text-gray-700'>Check Out</p>
            <p className='text-lg'>{handleDateFormat(dates.to)}</p>
        </div>
        <button
        onClick={handleSubmit}
        className='w-full bg-violet-700 py-4 flex items-center justify-center rounded-lg'>
            <p className='text-white font-semibold text-lg'>Confirm reserve</p>
        </button>
        </div>
    </article>
  )
}
