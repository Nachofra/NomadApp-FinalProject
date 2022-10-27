import React from 'react'
import listBlock from '@/staticJSON/listBlock.json'
import { Datalist, DatalistItem } from '../../../datalist'
import { useSearchContext } from '../../../../context/SearchContext'

export const LocationDatalist = ({flowTop}) => {

    const {filters, setFilters, reset} = useSearchContext()
    const locations = listBlock.map((elm => elm.location));

  return (
    <Datalist
    flowTop={flowTop}
    data={locations}
    value={filters.location ? `${filters.location?.city.name},  ${filters.location?.city.province.country.name}` : null}
    id='locations_dsk_filter'
    // label='Company / Organization'
    placeholder='Search around the world'
    onChange={(e) => setFilters({...filters, location: e})}
    resultRenderer={(location, i) => (
      <DatalistItem
        
        key={i}
        name={`${location.city.name},  ${location.city.province.country.name}`}
        id={location.id}
        value={location}
      />)
    }
  />
  )
}
