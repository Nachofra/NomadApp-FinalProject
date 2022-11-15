import { Navigate, Outlet } from 'react-router-dom'
import { useUserContext } from '../context/UserContext'
import { PublicRoutes } from './Routes'

export const AuthGuard = () => {
  const { user } = useUserContext();

  // console.log('entro', 'user', user)
  return user ? <Outlet /> : <Navigate to={PublicRoutes.LOGIN} state={{error :{
    title: 'Authentication required', 
    description: 'You must login to acess this content'
  }}} />

}


