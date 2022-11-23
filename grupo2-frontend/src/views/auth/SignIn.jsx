import React, { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FetchRoutes, PublicRoutes } from "../../guard/Routes";
import { useUserContext } from "../../context/UserContext";
import Email from "../../components/login/Email";
import PasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordValidation";
import { Link } from "react-router-dom";
import axios from "axios";

export const SignIn = ({setStatus, ...props}) => {

  const navigate = useNavigate();
  const { login }  = useUserContext();
  const [ fields, setFields] = useState({ 
    email: "", 
    password: "" 
  });
  const [ usuarioIncorrecto, setUsuarioIncorrecto ] = useState(false);

  const regex = new RegExp("[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+")

  const postUser = async () => {
      try {
        setStatus('LOADING');
        const { data } = await axios.post(`${FetchRoutes.BASEURL}/user/login`,
        {
          email: fields.email,
          password: fields.password
        })

        console.log(data)
        login(data);
        //if everything ok
        navigate(PublicRoutes.HOME);

      } catch (error) {
        console.error(error.message);
        navigate(PublicRoutes.LOGIN, { state:{
          error :{
            title: 'Something went wrong', 
            description: 'Please verify your credentials and try again. If the problem persist, contact support.'
          }
        }});
      } finally{ setStatus('IDLE') };
  }

  function verifyCredentials (){
    return regex.test(fields.email)
  }

  function handleSubmit(e) {
    e.preventDefault();
    if(verifyCredentials()){
      postUser();
    }
    if(!verifyCredentials()){
      console.log('validation error')
    }
  }

  return (
    <>      
      {props.heading &&
        <>
          <h3 className="text-xl font-thin text-center text-gray-500">Welcome Back</h3>
          <p className="mt-2 text-lg text-center text-gray-500 ">Access into your account</p>
      </>
      }
      <form onSubmit={handleSubmit}>
        <Email email={fields.email} setUser={setFields} />
        <PasswordValidation password={fields.password} setUser={setFields} />
        {usuarioIncorrecto && <p>Por favor vuelva a intentarlo, sus credenciales son inválidas</p>}
        <div className="flex items-center justify-center mt-4">
          {/* <a href="#" className="text-sm text-gray-600 hover:text-gray-500">Forget Password?</a> */}
          <button className="w-32 py-3 leading-5 text-white text-lg font-medium transition-colors duration-300 
          transform bg-violet-700 rounded-lg hover:bg-violet-600 focus:outline-none" type="submit">Login</button>                </div>
      </form> 
    </>
  );
};
