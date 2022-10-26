import React from 'react'

export const BaseLayout = (props) => {
  return (
    <section className='w-screen ' >
      <div className={`max-w-[1500px] mx-auto ${props.className}`}>
        {props.children}
      </div>
    </section>
  )
}
