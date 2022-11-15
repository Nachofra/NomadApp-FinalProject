import React from 'react'

export const BaseLayout = ({
  wrapperClassName,
  className,
  padding = "pt-20 lg:pt-24",
  ...props
}) => {
  return (
    <section className={`w-screen ${wrapperClassName}`} >
      <div className={`${padding} px-3 max-w-[1500px] mx-auto ${className}`}>
        {props.children}
      </div>
    </section>
  )
}
