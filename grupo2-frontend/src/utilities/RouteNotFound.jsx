import React, {FC} from 'react'
import { Route, Routes } from 'react-router-dom'

export const RouteNotFound = (props) => {
  return (
    <Routes>
        {props.children}
        <Route path='*' element={<div>Not found</div>}></Route>
    </Routes>
  )
}
