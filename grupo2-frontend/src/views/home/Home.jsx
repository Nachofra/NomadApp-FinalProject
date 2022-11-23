import React, { useEffect, useState } from 'react'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { SearchNav } from '../../components/partials'
import { Footer } from '../../components/partials/footer/Footer'
import { HeaderNav } from '../../components/partials/headerNav/HeaderNav'
import { PropertyCard } from '../../components/propertyCard/PropertyCard'
import { HomeCategories } from './components/HomeCategories'
import { FetchRoutes } from '../../guard/Routes'
import { useLoadingViewContext } from '../../context/LoadingViewContext'
import { useUserContext } from '../../context/UserContext'
import { useSearchContext } from '../../context/SearchContext'
import { LoadingSpinner } from '../../components/loadingSpinner/LoadingSpinner'
import { HeroSearchSection } from './components/HeroSearchSection'
import useScrollPosition from '../../hooks/useScrollPosition'
import axios from 'axios'
import './home.scss'
import { MagnifyingGlassIcon } from '@heroicons/react/24/outline'
export const Home = () => {

  // const [categories, setCategories] = useState(null);
  const [index, setIndex ] = useState(null)
  const [ showCount, setShowCount ] = useState(10);

  const {
    status,
    startLoading,
    loadDone,
    triggerError
  } = useLoadingViewContext()

  const [ feedStatus, setFeedStatus ] = useState('OK');

  const { user } = useUserContext();

  const { filters, categories } = useSearchContext()

  const scrollPosition = useScrollPosition();
  
  function getCategoryByID() {
    return categories?.find(elm => elm.id === filters.category)
  };

    useEffect(() => {

      const fetchData = async () => {
        startLoading();
          try {
            // const { data : categoriesData } = await axios.get(`${FetchRoutes.BASEURL}/category`);
            const { data : feed }  = await axios.get(
              `${FetchRoutes.BASEURL}/product${!user ? '/random?' : '?'}${filters.category ? `category=${filters.category}` : ''}${filters.location ? `&city=${filters.location.id}` : ''}`
              );

            // setCategories(categoriesData);
            setIndex(feed)
          } catch (error) {
            console.error(error.message);
            triggerError()
          }
          loadDone();
        }
        const refreshData = async () =>{
          setFeedStatus('LOADING');
            try {
              const { data : feed }  = await axios.get(
                `${FetchRoutes.BASEURL}/product${!user ? '/random?' : '?'}${filters.location ? `&city=${filters.location.id}` : ''} ${filters.category ? `category=${filters.category}` : ''}`
                );

              setIndex(feed)
            } catch (error) {
              console.error(error.message);
              setFeedStatus('ERROR')
            }
            setFeedStatus('OK');
          }
      
        if(!index || !categories) { fetchData() } else { refreshData() };
  }, [filters])




  return (
    <>
        <HeaderNav />
        <BaseLayout
          wrapperClassName="bg-logo-hero-search mb-32 md:mb-20"
          padding='px-3 pt-24 lg:pt-32'
        >
          <HeroSearchSection />
        </BaseLayout>
        <BaseLayout
          padding='px-3 pt-4 md:pt-6'
          className=" mb-10"
        >
          <HomeCategories categories={categories} />
        </BaseLayout>
        <BaseLayout
          anchor="main-feed"
          padding='pt-0 pb-8 '
          className='flex flex-col items-center justify-center'
        >

        {filters.category !== null ?
        <h2 className='text-3xl lg:text-4xl text-gray-800 text-center font-medium mb-6'>
            Searching in <span className="text-violet-700">{ getCategoryByID().title }</span>
        </h2> :
        <h2 className='text-3xl lg:text-4xl text-gray-800 text-center font-medium mb-6'>
          Places you will love <span className="text-violet-700">❤</span>
        </h2>
        }

          {feedStatus === 'OK' ?
            <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 flex-wrap gap-8'>

          { index?.map(( property, i ) => 
          i < showCount ?
          <PropertyCard property={property} key={i} />
          : null )}
          
          </div> :

          <LoadingSpinner />
          
        }
        { (index?.length === 0 & feedStatus === 'OK') &
          <div className='w-screen max-w-[90vw] md:max-w-lg max-h-96 
          bg-white rounded-lg flex flex-col items-center p-4 shadow-xl'>
            <MagnifyingGlassIcon className='w-20 h-20 mb-4 text-violet-700' />
    
            <p className='text-xl md:text-2xl font-medium mb-2 text-gray-800'>Reservation made</p>
            <p className='md:text-lg text-gray-600 mb-4'>We have sent you an email with all the details</p>
            <button
            onClick={() => navigate('/')}
            className="py-3 w-32 text-white bg-violet-700
            rounded-md text-lg font-medium">
              Awesome!
            </button>
          </div>
        } 
          {(showCount < index?.length && feedStatus === 'OK') &&
          <button 
          onClick={() => setShowCount(showCount + 10)}
          className='mb-36 w-32 py-2 mt-10 rounded-lg 
          text-violet-700 border-2 border-violet-700'>
              Show more
          </button>}
        </BaseLayout>
        <Footer />
        <SearchNav hide={scrollPosition < 300} />
    </>
  )
}
