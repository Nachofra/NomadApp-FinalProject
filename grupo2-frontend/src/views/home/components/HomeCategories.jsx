import React from 'react'
import listCategory  from '@/staticJSON/listCategory.json'
import { useSearchContext, emptyFilters } from '../../../context/SearchContext'
export const HomeCategories = () => {
    const {filters, setFilters, reset } = useSearchContext();
    // {
    //     "id":3,
    //     "title":"Cottage",
    //     "description":"Cabañas con las mejores vistas y comodidades",
    //     "imageUrl":"https://images.unsplash.com/photo-1576874748772-584aa2bee2d4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"
    // },
  return (
    <section className='w-full flex flex-col mb-20' >
        <h2 className='text-2xl font-medium mb-6'>
            Search from one of our categories
        </h2>
        <div className='grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4'>
        {
            listCategory.map((category, i) => { return(
                <article key={i} className='rounded-xl shadow-md overflow-hidden h-40 flex items-center justify-center'
                onClick={() => setFilters((prev => ({...prev, category: {
                    ...emptyFilters.category, [`${category.title}`]: !prev.category[`${category.title}`]
                    }
                })))}
                >
                    <img
                    className={`h-full w-full object-cover transition-all
                    ${filters?.category[`${category.title}`] ? '' : 'grayscale'}`}
                    src={category.imageUrl} 
                    alt={category.description}
                    />
                    <p className='absolute text-3xl font-semibold drop-shadow-md text-white'>
                        {category.title}
                    </p>
                </article>
            )})
        }
        </div>
        
    </section>
  )
}
