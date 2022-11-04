import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { Footer, HeaderNav, SearchNav } from '../../components/partials'
import listBlock from '@/staticJSON/listBlock.json'
import { ChevronLeftIcon } from '@heroicons/react/24/solid'
import { MapPinIcon, PhotoIcon, StarIcon } from '@heroicons/react/24/outline'
import { FeatureItem } from '../../components/featureItem/FeatureItem'
import { motion } from 'framer-motion'
import { ImageSlider } from '../../components/imageSlider/ImageSlider'
import { ImageGallery } from '../../components/imageGallery/ImageGallery'
import Modal from '../../components/modal/Modal'
export const Product = () => {

    const navigate = useNavigate();
    const [ data, setData ] = useState(listBlock[0])

    const featureList = [
        {
            name: 'WiFi',
            icon: '/icons/features/freeWifi.svg'
        },
        {
            name: 'Security 24hs',
            icon: '/icons/features/security.svg'
        },
        {
            name: 'King-S bed',
            icon: '/icons/features/kingBed.svg'
        },
        {
            name: 'Pets allowed',
            icon: '/icons/features/petHand.svg'
        },
        {
            name: 'Home heating',
            icon: '/icons/features/heating.svg'
        },
        {
            name: 'Microwave',
            icon: '/icons/features/microwave.svg'
        },
        {
            name: 'Air conditioner',
            icon: '/icons/features/airConditioner.svg'
        },
        {
            name: 'Garage',
            icon: '/icons/features/car.svg'
        },
    ]
    const imageList = [
        {
            src : '/mockup/img/house2.jpg',
            alt : 'Mockup house'
        },
        {
            src : '/mockup/img/building1.jpg',
            alt : 'Mockup house'
        },
        {
            src : '/mockup/img/house2.jpg',
            alt : 'Mockup house'
        },
        {
            src : '/mockup/img/house2.jpg',
            alt : 'Mockup house'
        },
        {
            src : '/mockup/img/house2.jpg',
            alt : 'Mockup house'
        }
    ]

    const [modal, setModal ] = useState(false);
  return (
    <>
    <HeaderNav />
    <BaseLayout
        wrapperClassName="bg-violet-800"
        className="flex items-center justify-between"
    >
        <div className='flex flex-col items-start justify-center py-4'>
            <p className='uppercase text-gray-200 font-thin text-lg'>{data.category.title}</p>
            <p className='text-xl text-white font-medium'>{data.title}</p>
        </div>
        <button onClick={() => navigate(-1)}>
            <ChevronLeftIcon className='w-10 h-10 text-white' />
        </button>
    </BaseLayout>
    <BaseLayout
        wrapperClassName="bg-violet-100"
        className=" flex items-center justify-between"
        padding='pt-4 pb-4'
    >
        <div className='flex items-center max-w-xs text-gray-700'>
            <MapPinIcon className='w-6 h-6' />
            <div className='ml-2 flex items-center text-sm md:text-base '>
                <p className='mr-1'>{data.location.city.name},</p>
                <p className='font-medium text-gray-900'>{data.location.city.province.country.name}</p>
            </div>
        </div>
        <div className='flex items-center'>
            <StarIcon className='w-6 h-6 text-violet-700' />
            <StarIcon className='w-6 h-6 text-violet-700' />
            <StarIcon className='w-6 h-6 text-violet-700' />
            <StarIcon className='w-6 h-6 text-violet-700' />
            <StarIcon className='w-6 h-6 text-gray-500' />
        </div>
    </BaseLayout>
    <BaseLayout
        padding='p-2 px-2'
        wrapperClassName="xl:hidden"
    >
        <ImageSlider images={imageList} />
    </BaseLayout>
    <BaseLayout
        padding='p-4 px-4'
        wrapperClassName="hidden xl:block"
    >
        <div className='flex items-center justify-center w-full gap-4'>
            <div className='h-full max-h-[550px] w-[70%] rounded-lg overflow-hidden'>
                <img
                className='w-full h-full object-cover'
                src={imageList[0].src} 
                alt={imageList[0].alt}
                />
            </div>
            <div className='h-full max-h-[550px] w-[30%] overflow-hidden
            grid grid-cols-2 grid-rows-4 gap-4'>
                {
                    imageList.map((image, i) => { 
                        if (i > 0 && i <= 8){ return(
                            <img
                            className='w-full h-full object-cover rounded-lg'
                            src={image.src} 
                            alt={image.alt}
                            key={i}
                            />
                        )}
                    })
                }
                <div 
                    onClick={() => setModal(true)}
                    className='flex flex-col items-center gap-4 justify-center
                    border-2 border-gray-200 rounded-lg cursor-pointer'
                >
                    <PhotoIcon className='w-10 h-10 text-gray-400' />
                    <p className='text-gray-500'>{`View more (${imageList.length})`}</p>
                </div>
            </div>
        </div>
        <Modal isOpen={modal} closeModal={() => setModal(false)} >
            <ImageGallery images={imageList} />
        </Modal>
    </BaseLayout>
    <BaseLayout
        padding='px-4 p-2'
    >
        <h3 className='text-xl md:text-2xl lg:text-3xl font-medium text-gray-700 mb-3'>{data.title}</h3>
        <p className='text-gray-700 md:text-lg max-w-4xl'>{data.description}</p>
    </BaseLayout>
    <BaseLayout
        wrapperClassName='my-4 p-2'
        padding='px-0 py-0'
        className='rounded-md border-2 border-violet-500 overflow-hidden'
    >
        <h5 className='text-lg md:text-xl px-4 py-4
        text-gray-700 border-b-2 border-violet-500'>
            What this place offers
        </h5>
        <ul className='bg-gradient-to-tr from-gray-100 to-violet-50  px-4 py-4 
        grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4  gap-y-6 gap-x-4'>
            {featureList.map((feature, i) => (
                <FeatureItem
                key={i}
                icon={feature.icon}
                text={feature.name}
                />
            ))}
        </ul>
    </BaseLayout>
    <BaseLayout
        padding='px-2 p-2'
    >
    </BaseLayout>
    <Footer />
    <SearchNav />
</>
  )
}
