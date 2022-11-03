import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/Routes";
import { Footer } from "../../components/partials/footer/Footer";
import { useUserContext } from "../../context/UserContext";
import Email from "../../components/login/Email";
import PasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordValidation";
import { Link } from "react-router-dom";
import { HeaderNav } from "../../components/partials";
import './auth.css'

export const SignIn = () => {
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
      <HeaderNav />
      <div className="pt-32 flex items-center justify-center">
        <div className="w-full max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-md ">
            <div className="px-6 py-4">
              <h2 className="text-3xl font-bold text-center text-gray-700">Group 2</h2>

              <h3 className="mt-1 text-xl font-medium text-center text-gray-600">Welcome Back</h3>

              <p className="mt-1 text-center text-gray-500 ">Create account</p>
              <form onSubmit={handleSubmit}>
                <Email email={ingressedUser.email} setUser={setIngressedUser} />
                <PasswordValidation password={ingressedUser.password} setUser={setIngressedUser} />
                {usuarioIncorrecto && <p>Por favor vuelva a intentarlo, sus credenciales son inválidas</p>}
                <div className="flex items-center justify-center mt-4">
                  {/* <a href="#" className="text-sm text-gray-600 hover:text-gray-500">Forget Password?</a> */}
                  <button className="px-4 py-2 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded hover:bg-gray-600 focus:outline-none" type="submit">Login</button>
                </div>
                {/*<Footer />*/}
              </form>
          </div>

          <div className="flex items-center justify-center py-4 text-center bg-gray-50">
              <span className="text-sm text-gray-600">Don't have an account? </span>
              <Link to={PublicRoutes.REGISTER} className="mx-2 text-sm font-bold text-blue-500 hover:underline">Register</Link>
          </div>
      </div>
    </div>
  </>
  );
};
