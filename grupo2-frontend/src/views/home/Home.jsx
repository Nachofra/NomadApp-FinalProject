import React, { useEffect, useState } from 'react'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { SearchNav } from '../../components/partials'
import { Footer } from '../../components/partials/footer/Footer'
import { HeaderNav } from '../../components/partials/headerNav/HeaderNav'
import { PropertyCard } from '../../components/propertyCard/PropertyCard'
import { HomeCategories } from './components/HomeCategories'
import { HeartIcon } from '@heroicons/react/20/solid'
import { FetchRoutes } from '../../guard/Routes'
import axios from 'axios'
import { useLoadingViewContext } from '../../context/LoadingViewContext'
import { useUserContext } from '../../context/UserContext'
export const Home = () => {

  const [categories, setCategories] = useState(null);
  const [index, setIndex ] = useState(null)
  const [ showCount, setShowCount ] = useState(10);

  const {
    status,
    startLoading,
    loadDone,
    triggerError
  } = useLoadingViewContext()

    const { user } = useUserContext();

    useEffect(() => {

      const fetchData = async () =>{
        startLoading();
          try {
            const { data : categoriesData } = await axios.get(`${FetchRoutes.BASEURL}/category`);
            const { data : feed }  = await axios.get(`${FetchRoutes.BASEURL}/product${!user ? '/random' : ''}`);

            setCategories(categoriesData);
            setIndex(feed)
          } catch (error) {
            console.error(error.message);
            triggerError()
          }
          loadDone();
        }
        if(!index || !categories) {fetchData()};
  }, [])


  return (
    <>
        <HeaderNav />
        <BaseLayout 
          padding='px-3 pt-32'
        >
        <HomeCategories categories={categories} />
        </BaseLayout>
        <BaseLayout
          padding='pt-0 pb-8 '
          className='flex flex-col items-center justify-center'
        >
        
        <h2 className='text-3xl lg:text-4xl text-gray-800 text-center font-medium mb-6'>
            Places you will love <span className="text-violet-700">❤</span>
        </h2>

          <div className='flex items-center justify-center flex-wrap gap-8'>

          { index?.map(( property, i ) => 
          i < showCount ?
          <PropertyCard property={property} key={i} />
          : null )}
          
          </div>

          {showCount < index?.length &&
          <button 
          onClick={() => setShowCount(showCount + 10)}
          className='mb-36 w-32 py-2 mt-10 rounded-lg 
          text-violet-700 border-2 border-violet-700'>
              Show more
          </button>}
        </BaseLayout>
        <Footer />
        <SearchNav />
    </>
  )
}
