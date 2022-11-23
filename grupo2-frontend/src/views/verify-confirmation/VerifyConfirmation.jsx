import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom';
import { useLoadingViewContext } from '../../context/LoadingViewContext'

export const VerifyConfirmation = () => {

  const {code} = useParams();

  const {
    startLoading,
    loadDone,
    triggerError,
  } = useLoadingViewContext()

  useEffect(() => {
    const fetchData = async () =>{
      startLoading();
        try {
          const { data } = await axios.post(`${FetchRoutes.BASEURL}/user/verify`,
          { code: code});
          setData(data);
        } catch (error) {
          console.error(error.message);
          triggerError()
        }
        loadDone();
      }
      
      if ( !data ) { fetchData() };
}, [])

  return (
    <div>
      
    </div>
  )
}
