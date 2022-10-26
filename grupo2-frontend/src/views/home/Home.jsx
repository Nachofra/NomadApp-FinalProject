import React from 'react'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { SearchNav } from '../../components/partials'
import { Footer } from '../../components/partials/footer/Footer'
import { HeaderNav } from '../../components/partials/headerNav/HeaderNav'
import { PropertyCard } from '../../components/propertyCard/PropertyCard'
import data from '@/staticJSON/listBlock.json'
export const Home = () => {
  return (
    <>
        <HeaderNav />
        <BaseLayout>
        <div className='flex items-center justify-center flex-wrap gap-8 pb-[100px]'>
        { data.map(( property, i ) => <PropertyCard property={property} key={i} /> )}
        </div>
        </BaseLayout>
        <Footer />
        <SearchNav />
    </>
  )
}
