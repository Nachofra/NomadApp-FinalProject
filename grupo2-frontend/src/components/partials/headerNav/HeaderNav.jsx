import React from 'react'
import DeskNavBarModal from './components/DeskNavBarModal'
import { PublicRoutes } from '../../../guard/routes'
import { useUserContext } from '../../../context/UserContext'

export const HeaderNav = () => {
  const { user, logout } = useUserContext();
  return (
    <section 
    className='top-0 
    w-screen flex items-end justify-between' >
        <div className='w-screen
        shadow-xl cursor-pointer h-auto relative
        flex justify-end items-end p-6
        rounded-md bg-shape-navbar
        ring-1 ring-violet-700 ring-opacity-5 bg-white mb-5' >
            <nav className='flex items-center'>
              {
                user ?
                <>
                  <h2 className='mr-10 text-2xl'>Hola <span className='font-bold'>{user.firstName}</span></h2>
                  <DeskNavBarModal
                    active={false}
                    onClick={()=>{logout()}}
                    route={PublicRoutes.REGISTER}
                    placeholder="Log Out">
                  </DeskNavBarModal>
                </>
                :
                <>
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
                </> 
              }
              
            </nav>
        </div>
    </section>
  )
}
