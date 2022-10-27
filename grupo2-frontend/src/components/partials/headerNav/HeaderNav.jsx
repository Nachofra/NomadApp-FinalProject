import React, { useState } from 'react'
import DeskNavBarModal from './components/DeskNavBarModal'
import { PublicRoutes } from '../../../guard/routes'
import { useUserContext } from '../../../context/UserContext'
import { UserInitials } from './components/UserInitials'
import { ArrowRightOnRectangleIcon } from '@heroicons/react/24/outline'
import { Link } from 'react-router-dom'
import { Bars2Icon, XMarkIcon } from '@heroicons/react/20/solid'
import {motion, AnimatePresence } from 'framer-motion'
import { MobileOpen } from './components/MobileOpen'

export const HeaderNav = () => {
  const { user, logout } = useUserContext();
  const [open, setOpen] = useState(false);

  return (
    <section 
    className='top-0 fixed z-50
    w-screen flex items-center justify-between' >
        <div className='w-screen
        shadow-xl cursor-pointer h-auto relative
        flex justify-end items-end p-4 lg:p-6
        ring-1 ring-violet-700 ring-opacity-5 bg-white mb-5' >
          <div className='flex flex-col items-center lg:hidden h-auto w-full'>
            <div className='flex items-center justify-between w-full'>
              <Link to={PublicRoutes.HOME} >
                <img src='/logo/logo_demo.svg' className='h-10' />
              </Link>
              <div className='p-2 w-10 h-10 flex items-center justify-center'
                onClick={() => setOpen(!open)}
              >
                {!open &&
                  <Bars2Icon className='w-6 h-6 text-violet-700' />}
                {open &&
                  <XMarkIcon className='w-6 h-6 text-red-400' />}
              </div>
            </div>
            <AnimatePresence initial={false} mode="wait" >
              {open &&  
                  <MobileOpen user={user} />
              }
            </AnimatePresence>
          </div>
          <nav className='hidden lg:flex items-center justify-between w-full max-w-[1500px] mx-auto'>
            <Link to={PublicRoutes.HOME} >
              <img src='/logo/logo_demo.svg' className='h-10' />
            </Link>
            <div className='flex items-center'>
            {
              user ?
              <>
                <h2 className='mr-4 text-xl'>Hola <span className='font-bold'>{user.firstName}</span></h2>

                <UserInitials initials={user?.firstName[0] + user?.lastName[0]} />
                <Link to={PublicRoutes.HOME}>
                  <ArrowRightOnRectangleIcon
                  className='w-8 h-8 text-red-400'
                    onClick={()=>{logout()}}
                  />
                </Link>
              </>
              :
              <>
                <DeskNavBarModal
                alt
                route={PublicRoutes.REGISTER}
                placeholder="Sign Up">
                </DeskNavBarModal>
                <DeskNavBarModal
                route={PublicRoutes.LOGIN}
                placeholder="Sign In">
                </DeskNavBarModal>
              </> 
            }
            </div>
          </nav>
        </div>
    </section>
  )
}
