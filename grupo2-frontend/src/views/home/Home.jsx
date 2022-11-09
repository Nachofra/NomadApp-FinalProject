import React, { useEffect, useState } from 'react'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { SearchNav } from '../../components/partials'
import { Footer } from '../../components/partials/footer/Footer'
import { HeaderNav } from '../../components/partials/headerNav/HeaderNav'
import { PropertyCard } from '../../components/propertyCard/PropertyCard'
import data from '@/staticJSON/listBlock.json'
import { HomeCategories } from './components/HomeCategories'
import { HeartIcon } from '@heroicons/react/20/solid'
import { FetchRoutes } from '../../guard/Routes'
import axios from 'axios'
import { useLoadingViewContext } from '../../context/LoadingViewContext'
export const Home = () => {

  const [categories, setCategories] = useState(null);

    const {
      status,
      startLoading,
      loadDone,
      triggerError
    } = useLoadingViewContext()

    useEffect(() => {

      const fetchData = async () =>{
        startLoading();
          try {
            const { data } = await axios.get(`${FetchRoutes.BASEURL}/category`);
            setCategories(data);
          } catch (error) {
            console.error(error.message);
            triggerError()
          }
          loadDone();
        }
        
        fetchData();
  }, [])
  
  return (
    <>
        <HeaderNav />
        <BaseLayout 
          padding='px-3 pt-32'
        >
        <HomeCategories categories={categories} />
        
        <h2 className='text-3xl text-gray-800 text-center font-medium mb-6'>
            Places you will love <span className="text-violet-700">❤</span>
        </h2>


          <div className='flex items-center justify-center flex-wrap gap-8 mb-36'>
          { data.map(( property, i ) => <PropertyCard property={property} key={i} /> )}
          </div>
        </BaseLayout>
        <Footer />
        <SearchNav />
    </>
  )
}
