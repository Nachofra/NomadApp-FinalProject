import React from 'react'
import DeskNavBarModal from './components/DeskNavBarModal'
import { PublicRoutes } from '../../../guard/routes'

export const HeaderNav = () => {
  return (
    <section 
    className='top-0 
    w-screen flex items-end justify-between' >
        <div className='w-screen
        shadow-xl cursor-pointer h-auto relative
        flex justify-end items-end p-6
        rounded-md bg-shape-navbar
        ring-1 ring-violet-700 ring-opacity-5 bg-white mb-5' >
            <nav className='flex items-end'>
              <DeskNavBarModal
              active={false}
              route={PublicRoutes.LOGIN}
              placeholder="Sign In">
              </DeskNavBarModal>

              <DeskNavBarModal
              active={false}
              route={PublicRoutes.REGISTER}
              placeholder="Sign Up">
              </DeskNavBarModal>
            </nav>
        </div>
    </section>
  )
}
