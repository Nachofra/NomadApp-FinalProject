import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/routes";
import { Footer } from "../../components/partials/Footer";
import { useUserContext } from "../../context/UserContext";
import { UserInfo } from "../../components/login/UserInfo";
import Email from "../../components/login/email";
import PasswordAndConfirmPasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordAndConfirmPasswordValidation.js.jsx";

export const SignUp = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  console.log(user);
  function handleSubmit(e) {
    e.preventDefault();
    navigate(PublicRoutes.LOGIN);
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
