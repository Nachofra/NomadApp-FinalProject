import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/routes";
import { Footer } from "../../components/partials/footer/Footer";
import { useUserContext } from "../../context/UserContext";
import { UserInfo } from "../../components/login/UserInfo";
import Email from "../../components/login/email";
import PasswordAndConfirmPasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordAndConfirmPasswordValidation.js.jsx";

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
    <form onSubmit={handleSubmit}>
      <UserInfo firstName={user.firstName} lastName={user.lastName} setUser={setUser} />
      <Email email={user.email} setUser={setUser} />
      <PasswordAndConfirmPasswordValidation password={user.password} confirmPassword={user.confirmPassword} setUser={setUser}/>
      <button type="submit">Add User</button>
      {/*<Footer />*/}
    </form>
  );
};
