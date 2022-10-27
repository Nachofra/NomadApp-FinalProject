import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/routes";
import { Footer } from "../../components/partials/footer/Footer";
import { useUserContext } from "../../context/UserContext";
import { UserInfo } from "../../components/login/UserInfo";
import Email from "../../components/login/email";
import { Link } from "react-router-dom";
import PasswordAndConfirmPasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordAndConfirmPasswordValidation.js.jsx";
import { HeaderNav } from "../../components/partials";

export const SignUp = () => {
  const regex = new RegExp("[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+")
  const navigate = useNavigate();
  const { login } = useUserContext();
  
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  function handleSubmit(e) {
    e.preventDefault();
    if(user.password.length > 6 && user.password === user.confirmPassword && regex.test(user.email)){
      login({
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        password: user.password,
      });
      navigate(PublicRoutes.LOGIN);
    }
  }

  return (
    <>
      <HeaderNav />
      <div className="h-screen flex items-center justify-center">
        <div className="w-full max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-md ">
            <div className="px-6 py-4">
              <h2 className="text-3xl font-bold text-center text-gray-700">Group 2</h2>

              <h3 className="mt-1 text-xl font-medium text-center text-gray-600">Welcome</h3>

              <p className="mt-1 text-center text-gray-500 ">Create account</p>
              <form onSubmit={handleSubmit}>
                <UserInfo firstName={user.firstName} lastName={user.lastName} setUser={setUser} />
                <Email email={user.email} setUser={setUser} />
                <PasswordAndConfirmPasswordValidation password={user.password} confirmPassword={user.confirmPassword} setUser={setUser}/>
                <div className="flex items-center justify-center mt-4">
                  <button className="px-4 py-2 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded hover:bg-gray-600 focus:outline-none" type="submit">Register</button>
                </div>
                {/*<Footer />*/}
              </form>
          </div>

          <div className="flex items-center justify-center py-4 text-center bg-gray-50">
              <span className="text-sm text-gray-600">Do you have an account? </span>
              <Link to={PublicRoutes.LOGIN} className="mx-2 text-sm font-bold text-blue-500 hover:underline">Login</Link>
          </div>
      </div>
    </div>
  </>
  );
};
