import React from 'react'
import useWindowSize from '../../../hooks/useWindowSize'
import { DesktopSearchbar } from './desktopNav/DesktopSearchbar'
import { MobileSearchbar } from './mobileNav'
import './searchNavbar.scss'

export const SearchNavbar = () => {
  const {width, height} = useWindowSize()
  // console.log(width,height)
  
  if (width < 1399) {return <MobileSearchbar />} else {return <DesktopSearchbar /> }
  
}
