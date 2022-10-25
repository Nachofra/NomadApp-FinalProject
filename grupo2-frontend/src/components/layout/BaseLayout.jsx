import React from 'react'

export const BaseLayout = (props) => {
  return (
    <section className='w-screen max-w-[1500px]' >
        {props.children}
    </section>
  )
}
