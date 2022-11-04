import { MapIcon, StarIcon } from '@heroicons/react/24/outline'
import { MapPinIcon } from '@heroicons/react/24/solid'
import React from 'react'
import { Link } from 'react-router-dom'

export const PropertyCard = ({
    property
}) => {
    // "id": 1,
    // "img": "./img/house1.jpg",
    // "category": {
    //   "id": 2,
    //   "title": "House",
    //   "description": "House in general",
    //   "imageUrl": "asdfgv"
    // }
    // "location": {
    //     "id": 7,
    //     "adress": "Calle Cuarteto",
    //     "number": 123,
    //     "floor": 10,
    //     "apartment": "A",
    //     "city": {
    //       "id": 7,
    //       "name": "Cordoba",
    //       "province": {
    //         "id": 7,
    //         "name": "Cordoba",
    //         "country": { "id": 7, "name": "Argentina" }
  return (
    <Link to={`/product/${property.id}`} 
    className='w-full max-w-xs rounded-xl
    flex flex-col bg-white shadow-sm p-2 group'>
        <picture className='w-full h-60 overflow-hidden rounded-lg'>
            <img 
            className='w-full h-full object-cover transition-all duration-300
            group-hover:scale-110' 
            src={property.img}
            alt={property.description}
            />
        </picture>
        <div className='p-1 flex flex-col items-start text-left'>
            <p className='font-medium text-lg text-gray-900'>{property.title}</p>
            <div className='flex items-center text-violet-700'>
                <MapPinIcon className='w-6 h-6 mr-1'/>
                <p className=''>{`${property.location.city.name}, ${property.location.city.province.country.name}`}</p>
            </div>
        </div>
        <div className='w-full flex justify-between items-center p-1 mt-4'>
            <p className='text-2xl text-gray-900'>
                $ 242
                <span className=' font-light italic text-gray-700'>/night</span>
            </p>
            <div className='flex items-center text-violet-700'>
                <StarIcon className='w-8 h-8' />
                <p className='text-xl font-medium'>5.0</p>
            </div>
        </div>
    </Link>
  )
}
