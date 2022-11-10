import React, { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/Routes";
import { useUserContext } from "../../context/UserContext";
import Email from "../../components/login/Email";
import PasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordValidation";
import { Link } from "react-router-dom";

export const SignIn = () => {

  // const {
  //   startLoading,
  //   loadDone,
  // } = useLoadingViewContext()
  
  // useEffect(() => {
  //   startLoading();
  //   window.addEventListener('load', loadDone)
  //   const timer = setTimeout(loadDone, 2000);

  //   // this will clean up the event every time the component is re-rendered
  //   return function cleanup() {
  //     window.removeEventListener('load', loadDone);
  //     clearTimeout(timer)
  //   }
  // }, [])

  const navigate = useNavigate();
  const { user, login }  = useUserContext();
  const [ ingressedUser, setIngressedUser] = useState({ email: "", password: "" });
  const [ usuarioIncorrecto, setUsuarioIncorrecto ] = useState(false);

  function handleSubmit(e) {
    e.preventDefault();
    if(user?.email === ingressedUser.email && user?.password === ingressedUser.password){
      navigate(PublicRoutes.HOME);
    }else{
      setUsuarioIncorrecto(true);
    }
  }

  return (
    <>

              <h3 className="text-xl font-thin text-center text-gray-500">Welcome Back</h3>

              <p className="mt-2 text-lg text-center text-gray-500 ">Access into your account</p>
              <form onSubmit={handleSubmit}>
                <Email email={ingressedUser.email} setUser={setIngressedUser} />
                <PasswordValidation password={ingressedUser.password} setUser={setIngressedUser} />
                {usuarioIncorrecto && <p>Por favor vuelva a intentarlo, sus credenciales son inválidas</p>}
                <div className="flex items-center justify-center mt-4">
                  {/* <a href="#" className="text-sm text-gray-600 hover:text-gray-500">Forget Password?</a> */}
                  <button className="w-32 py-3 leading-5 text-white text-lg font-medium transition-colors duration-300 
                  transform bg-violet-700 rounded-lg hover:bg-violet-600 focus:outline-none" type="submit">Login</button>                </div>
              </form>

          
    </>
  );
};
