import { ShieldExclamationIcon } from '@heroicons/react/24/outline'
import React, { useState } from 'react'
import { Link, Route, Routes, useLocation } from 'react-router-dom'
import { BaseLayout } from '../../components/layout/BaseLayout'
import { HeaderNav } from '../../components/partials'
import { FetchRoutes, PublicRoutes } from '../../guard/Routes'
import { SignIn } from './SignIn'
import { SignUp } from './SignUp'

export const Auth = () => {

  const {state} = useLocation()
  const [message, setMessage] = useState(state)

  return (
    <>
      <div className="fixed inset-0 z-[-1]">
        <video 
        src={`${FetchRoutes.BUCKET}/media/nomad_login_bg_720.mp4`}
        className="object-cover w-full h-full"
        autoPlay muted loop
        />
      </div>
      <HeaderNav transparent />
      <BaseLayout
      padding="pt-0"
      className='min-h-screen flex flex-col items-center justify-center'
      >

        <div className="w-full max-w-sm
        overflow-hidden bg-white rounded-lg shadow-md ">
            <div className="px-6 py-4">
              <h2 className="text-3xl font-bold text-center text-violet-700">Hi nomad :)</h2>
              
              {message?.error &&
              <div className='py-2 px-4 bg-red-400 rounded-lg
              flex flex-col items-center justify-between text-white my-4'>
                <ShieldExclamationIcon className='w-10 h-10 mb-2' />
                  <p className='text-lg font-medium'>{message.error.title}</p>
                  <p className=''>{message.error.description}</p>
              </div>}

              <Routes>
                <Route path={'/signin'} element={<SignIn heading={ message?.error === undefined } />}  />
                <Route path={'/signup'} element={<SignUp heading={ message?.error === undefined } />}  />
              </Routes>
            </div>

            <Routes>
              <Route path={'/signin'} element={
                <div className="flex items-center justify-center py-4 text-center bg-gray-50">
                  <span className="text-sm text-gray-600">Don't an account? </span>
                  <Link to={PublicRoutes.REGISTER} className="mx-2 text-sm font-bold text-violet-700 hover:underline">Register</Link>
                </div>
              } />
              <Route path={'/signup'} element={
                <div className="flex items-center justify-center py-4 text-center bg-gray-50">
                  <span className="text-sm text-gray-600">Already an account? </span>
                  <Link to={PublicRoutes.LOGIN} className="mx-2 text-sm font-bold text-violet-700 hover:underline">Login</Link>
                </div>
              } />
            </Routes>
        </div>
      </BaseLayout>
    </>
  )
}
