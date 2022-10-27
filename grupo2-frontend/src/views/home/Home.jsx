import React from 'react'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { SearchNav } from '../../components/partials'
import { Footer } from '../../components/partials/footer/Footer'
import { HeaderNav } from '../../components/partials/headerNav/HeaderNav'
import { PropertyCard } from '../../components/propertyCard/PropertyCard'
import data from '@/staticJSON/listBlock.json'
import { HomeCategories } from './components/HomeCategories'
export const Home = () => {
  return (
    <>
        <HeaderNav />
        <BaseLayout>
        <HomeCategories />
          <div className='flex items-center justify-center flex-wrap gap-8 mb-36'>
          { data.map(( property, i ) => <PropertyCard property={property} key={i} /> )}
          </div>
        </BaseLayout>
        {/* <Footer /> */}
        <SearchNav />
    </>
  )
}
