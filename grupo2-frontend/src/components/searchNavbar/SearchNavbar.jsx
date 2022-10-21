import React, { useState } from 'react'
import './searchNavbar.scss'
import useWindowSize from '../../hooks/useWindowSize'
import { MobileSearchbar } from './mobileNav/MobileSearchbar'
import { DesktopSearchbar } from './desktopNav/DesktopSearchbar'

export const SearchNavbar = () => {
  const {width, height} = useWindowSize()
  // console.log(width,height)
  
  if (width < 768) {return <MobileSearchbar />} else {return <DesktopSearchbar /> }
  
}
