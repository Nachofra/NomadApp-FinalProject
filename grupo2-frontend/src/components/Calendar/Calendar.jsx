import React, { useState } from "react"
import ReactDatePicker from "react-datepicker"
import './calendarStyles.scss'


export const Calendar = ({
    startDate,
    endDate,
    setStartDate,
    setEndDate,
    monthsDisplayed
  }) => {
    const [selectingStart, setSelectingStart] = useState(true)
    function handleDateChange (dates) {
        setStartDate(dates[0].toLocaleDateString('en-US'))

        setEndDate(dates[1].toLocaleDateString('en-US'))

      }
    
      return (
        <ReactDatePicker
          selected={startDate}
          onChange={handleDateChange}
          startDate={startDate}
          endDate={endDate}
          monthsShown={monthsDisplayed}
          selectsRange
          inline
        />
    )
  }