import { CheckBadgeIcon } from '@heroicons/react/24/outline'
import { ChevronLeftIcon } from '@heroicons/react/24/solid'
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { redirect, useLocation, useNavigate, useParams } from 'react-router-dom'
import { Calendar } from '../../components/Calendar/Calendar'
import { Datalist, DatalistItem } from '../../components/datalist'
import { Input } from '../../components/input/Input'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { Modal } from '../../components/modal/Modal'
import { Footer, HeaderNav } from '../../components/partials'
import { useLoadingViewContext } from '../../context/LoadingViewContext'
import { useSearchContext } from '../../context/SearchContext'
import { FetchRoutes } from '../../guard/Routes'
import useWindowDimensions from '../../hooks/useWindowDimensions'
import { PolicyList } from '../product/components/PolicyList'
import { ReserveCard } from './components/ReserveCard'
import { checkinList } from './utilities/checkinList'

export const Reserve = () => {

  const { width } = useWindowDimensions();
  const navigate = useNavigate();
  const { filters } = useSearchContext()
  const {state} = useLocation()

  Date.prototype.formatMMDDYYYYhypens = function(){
    return this.getFullYear() + 
    "-" +  (this.getMonth() + 1) +
    "-" +  this.getDate();
  }
    
  const handleDateFormat = date => date? date.formatMMDDYYYYhypens() : null;

  const {
      status,
      startLoading,
      loadDone,
      triggerError,
    } = useLoadingViewContext()

  const [ data, setData ] = useState(state?.product)
  const {id} = useParams();

  useEffect(() => {
      const fetchData = async () =>{
        startLoading();
          try {
            const { data } = await axios.get(`${FetchRoutes.BASEURL}/product/${id}`);
            setData(data);
          } catch (error) {
            console.error(error.message);
            triggerError()
          }
          loadDone();
        }
        
        if ( !data ) { fetchData() };
  }, [])

    const [modal, setModal ] = useState(false);

  const initialStateForm = {
    id: '',
    name: '',
    email: '',
    lastName: '',
    city: '',
    dateFrom: filters.date.from,
    dateTo: filters.date.to,
    checkin: null
  }

  const [formFields, setFormFields] = useState(initialStateForm)

  const handleDates = ( dateFrom, dateTo )=> {
    setFormFields({...formFields, dateFrom:dateFrom, dateTo:dateTo})
  }

  const postRequest = async () => {
      try {
        startLoading();
        const { data } = await axios.post(`${FetchRoutes.BASEURL}/reservation`,
        {
          checkInTime: checkin?.time,
          checkInDate: handleDateFormat(dateFrom),
          checkOutDate: handleDateFormat(dateTo),
          product: { id: id },
          user: { id: 'z' }
        })

        console.log(data)
        login(data);
        //if everything ok
        //

      } catch (error) {
        console.error(error.message);
        
      } finally{ loadDone() };
  }

  const handleSubmit = () => {
    setModal(true)
  }

    if (data) { return (
    <>
    <HeaderNav />
    <BaseLayout
        padding='mt-16 lg:mt-24 md:px-6 lg:px-8'
        wrapperClassName="bg-violet-800 sticky top-16 lg:top-24 z-20"
        className="flex items-center justify-between"
    >
        <div className='flex flex-col items-start justify-center py-4'>
            <p className='uppercase text-gray-200 font-thin text-lg'>{data.category.title}</p>
            <p className='text-xl text-white font-medium overflow-ellipsis whitespace-nowrap overflow-hidden 
            max-w-[20ch] sm:max-w-[25ch] md:max-w-none'>{data.title}</p>
        </div>
        <button onClick={() => navigate(-1)}>
            <ChevronLeftIcon className='w-10 h-10 text-white' />
        </button>
    </BaseLayout>
    <BaseLayout
        padding='px-4 md:px-6 lg:px-8 p-2 pb-4 pt-6'
    >
      <div className='flex flex-col xl:flex-row items-start xl:justify-between w-full gap-8'>
        <div className='flex flex-col grow w-full xl:w-auto max-w-5xl'>
          <h3 className='text-2xl md:text-3xl
          font-medium text-gray-700 mb-4'>Billing information</h3>
          <div className='grid grid-cols-1 md:grid-cols-2 gap-6 p-4 md:p-8
          rounded-xl border-2 border-violet-500 bg-white'>
            <Input
              label='Name'
              id='reservation_name'
              type='text'
              placeholder='Your full name'
              value={formFields.name}
              onChange={(e) =>
                setFormFields({ ...formFields, name: e.target.value })
              }
            />
            <Input
              label='Last Name'
              id='reservation_lastName'
              type='text'
              placeholder='Your full last name'
              value={formFields.lastName}
              onChange={(e) =>
                setFormFields({ ...formFields, lastName: e.target.value })
              }
            />
            <Input
              label='Email'
              id='reservation_email'
              type='email'
              placeholder='We will send it here'
              value={formFields.email}
              onChange={(e) =>
                setFormFields({ ...formFields, email: e.target.value })
              }
            />
            <Input
              label='City'
              id='reservation_city'
              type='email'
              placeholder='Same as adress'
              value={formFields.city}
              onChange={(e) =>
                setFormFields({ ...formFields, city: e.target.value })
              }
            />

          </div>
          <h3 className='text-2xl md:text-3xl
          font-medium text-gray-700 mt-12 mb-4'>Confirm date</h3>

          <div className='flex items-center justify-center 
          bg-white rounded-xl border-2 border-violet-500'>
            <Calendar
              startDate={formFields.dateFrom}
              endDate={formFields.dateTo}
              afterChange={(dateFrom, dateTo) => { handleDates(dateFrom, dateTo)}}
              monthsDisplayed={width > 660 ? 2 : 1 }
            />
          </div>
          <h3 className='text-2xl md:text-3xl
          font-medium text-gray-700 mt-12 mb-4'>Select Check-in</h3>

          <div className='flex flex-col items-start justify-center 
          bg-white rounded-xl border-2 border-violet-500 p-4'>
            <div className='flex items-center mb-4'>
              <CheckBadgeIcon className='w-8 h-8 text-gray-700 mr-1'/>
              <p className='text-gray-700 text-lg'>
                {formFields.checkin?.value ? 
                `The place will be ready around ${formFields.checkin.value} and ${formFields.checkin.end}` :
                'Please select any checkin hour'}
              </p>
            </div>
            <p className='font-medium text-gray-800 mb-2'>Choose another hour</p>
            <div className='w-full max-w-sm'>
              <Datalist
                data={checkinList}
                value={formFields.checkin ? formFields.checkin?.value : null}
                id='reserve_checkin'
                // label='Company / Organization'
                placeholder='Select daytime'
                onChange={(e) => setFormFields({...formFields, checkin: e})}
                resultRenderer={(time, i) => (
                  <DatalistItem
                    key={i}
                    name={time.value}
                    id={time.id}
                    value={time}
                  />)
                }
              />
            </div>
          </div>
        </div>

        <ReserveCard data={data} dates={{from: formFields.dateFrom, to: formFields.dateTo}} handleSubmit={handleSubmit} />
      </div>
      
    </BaseLayout>
    <BaseLayout
      padding='px-4 md:px-6 lg:px-8 p-2'
      className='py-10 flex flex-col '
    >
      <h5 className='text-2xl md:text-3xl lg:text-4xl mb-4
      text-gray-700 font-medium'>Rent policies</h5>

      <div className='grid grid-cols-1 md:gird-cols-2 lg:grid-cols-3 gap-8 mt-4'>
      { data.policies.map((item, i) => (
          <PolicyList key={i} title={item.name} list={item.policyItems} />
      ))} 
      </div>
    </BaseLayout>
    <Footer />
    <Modal
      isOpen={modal}
      closeModal={() => navigate('/')}
    >
      <div className='w-screen h-screen max-w-[90vw] md:max-w-lg max-h-96 
      bg-white rounded-lg flex flex-col items-center p-4'>
        <p className='text-xl md:text-2xl font-medium mb-2 text-gray-800'>Reservation made</p>
        <p className='md:text-lg text-gray-600'>We have sent you an email with all the details</p>
      </div>
    </Modal>
    </>
  )}

  else {
    return(<p>err</p>)
  }
}
