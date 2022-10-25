import React from 'react'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { Footer } from '../../components/partials/Footer'
import { HeaderNav } from '../../components/partials/HeaderNav'
import { SearchNavbar } from  '@/components/partials/SearchNavbar/SearchNavbar'
export const Home = () => {
  return (
    <>
        <HeaderNav />
        <BaseLayout>

        </BaseLayout>
        <Footer />
        <SearchNavbar />
    </>
  )
}
