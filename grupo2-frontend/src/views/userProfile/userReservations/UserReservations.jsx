import { ArrowLeftIcon } from '@heroicons/react/24/outline'
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { BaseLayout } from '../../../components/layout/BaseLayout'
import { HeaderNav } from '../../../components/partials'
import { useLoadingViewContext } from '../../../context/LoadingViewContext'
import { useUserContext } from '../../../context/UserContext'
import { FetchRoutes, PrivateRoutes } from '../../../guard/Routes'
import { UserReserveCard } from './components/UserReserveCard'

export const UserReservations = () => {

    const {
        status,
        startLoading,
        loadDone
      } = useLoadingViewContext()

      const { user } = useUserContext();

      const [data, setData] = useState(null)

      const navigate = useNavigate();

      const fetchData = async () => {
        try {
          startLoading();
          const {data : reservations} = await axios.get(`${FetchRoutes.BASEURL}${PrivateRoutes.USERRESERVATIONSID(user.id)}`,
          { headers: { Authorization : user.authorization }})

          setData(reservations);
          console.log(reservations)
            
        } catch (error) {
          console.error(error.message);
          navigate('/bad-request')
        } finally{ loadDone() };
    }

    useEffect(() => {
        if(!data){fetchData()}

    }, [])
    
  return (
     <>
        <HeaderNav />
        <BaseLayout
          wrapperClassName="bg-logo-hero-search mb-6 md:mb-10"
          padding='px-3 pt-24 lg:pt-32'
        >
            <div className='flex items-end justify-between w-full py-6 md:px-10'>
                <h1 className='text-2xl font-semibold text-white'>My reservations</h1>
                <ArrowLeftIcon
                onClick={() => navigate(-1)}
                className="text-white w-10 h-10" />
            </div>
        </BaseLayout>
        <BaseLayout
          padding='px-3 pt-4 md:pt-6'
          className=" mb-10"
        >
            <div className='w-full place-items-center grid 
            grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8'>
                {data?.map((reserve, i) => (
                    <UserReserveCard data={reserve} key={i} />
                ))}
            </div>
        </BaseLayout>
    </>
  )
}
