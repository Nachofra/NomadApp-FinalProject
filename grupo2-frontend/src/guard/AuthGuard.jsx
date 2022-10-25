import { Navigate, Outlet } from 'react-router-dom'
import { useUserContext } from '../context/UserContext'
import { PublicRoutes } from './routes'

export const AuthGuard = () => {
  const { user } = useUserContext()
  console.log('entro', 'user', user)
  return user ? <Outlet /> : <Navigate to={PublicRoutes.LOGIN} />
}
