import React from 'react'
import {motion} from 'framer-motion';
import DeskNavBarModal from './DeskNavBarModal';
import { PublicRoutes } from '../../../../guard/routes';
import { Link } from 'react-router-dom';
import { ArrowRightOnRectangleIcon } from '@heroicons/react/20/solid';

export const MobileOpen = ({user}) => {
  return (
    <motion.nav className='flex flex-col'
        initial={{
        height: 0,
        opacity: 0,
        }}
        animate={{
            height: "auto",
            opacity: 1,
            transition: {
            height: {
                    duration: 0.3,
            },
                opacity: {
                    duration: 0.25,
                    delay: 0.15,
            },
            },
        }}
        exit={{
            height: 0,
            opacity: 0,
            transition: {
            height: {
                duration: 0.3,
            },
            opacity: {
                duration: 0.25,
            },
        },
        }}
        >
            <div className=' py-10 w-full flex flex-col items-center space-y-4'>
            {
                user ?
                <>
                    <h2 className='mr-10 text-xl'>Hola <span className='font-bold'>{user.firstName}</span></h2>

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
    </motion.nav>
  )
}
