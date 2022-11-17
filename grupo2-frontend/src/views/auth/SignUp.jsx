import React, { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/Routes";
import { Footer } from "../../components/partials/footer/Footer";
import { useUserContext } from "../../context/UserContext";
import { UserInfo } from "../../components/login/UserInfo";
import Email from "../../components/login/Email";
import PasswordAndConfirmPasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordAndConfirmPasswordValidation.jsx";

export const SignUp = (props) => {

  const regex = new RegExp("[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+")
  const navigate = useNavigate();
  const { register } = useUserContext();
  
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
      register({
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
      {props.heading &&
      <>
        <h3 className="text-xl font-thin text-center text-gray-500">Welcome</h3>
        <p className="mt-2 text-lg text-center text-gray-500 ">Create account</p>
      </>
      }
      <form onSubmit={handleSubmit}>
        <UserInfo firstName={user.firstName} lastName={user.lastName} setUser={setUser} />
        <Email email={user.email} setUser={setUser} />
        <PasswordAndConfirmPasswordValidation password={user.password} confirmPassword={user.confirmPassword} setUser={setUser}/>
        <div className="flex items-center justify-center mt-4">
          <button className="w-32 py-3 leading-5 text-white text-lg 
          font-medium transition-colors duration-300 
          transform bg-violet-700 rounded-lg hover:bg-violet-600 
          focus:outline-none" 
          type="submit">Register</button>
        </div>
      </form>     
    </>
  );
};
