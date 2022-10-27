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
    
    function handleDateChange (dates) {
      setDateFrom(dates[0])
      setDateTo(dates[1])
    }

    const [dateFrom, setDateFrom] = useState(null)
    const [dateTo, setDateTo] = useState(null)

      return (
        <ReactDatePicker
          selected={dateFrom}
          onChange={handleDateChange}
          startDate={dateFrom}
          endDate={dateTo}
          monthsShown={monthsDisplayed}
          selectsRange
          inline
        />
    )
  }