import React from 'react'
import { motion } from 'framer-motion'
import { useSearchContext } from '../../../context/SearchContext'
import { CheckIcon } from '@heroicons/react/24/solid'
import { MobileNavModal } from './MobileNavModal'

export const MobileNavOpen = ({open, setOpen}) => {

    const {filters, setFilters, reset} = useSearchContext()
    // const {category, price, date, guests, location} = filters
  
  return (
    
    <MobileNavModal open={open} setOpen={setOpen} >

    </MobileNavModal>

    )
}
