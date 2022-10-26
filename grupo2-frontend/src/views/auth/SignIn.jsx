import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/routes";
import { Footer } from "../../components/partials/Footer";
import Email from "../../components/login/email";
import PasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordValidation";

export const SignIn = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({ email: "", password: "" });
  function handleSubmit(e) {
    e.preventDefault();
    //navigate(PublicRoutes.HOME);
  }

  return (
    <form onSubmit={handleSubmit}>
      <Email email={user.email} setUser={setUser} />
      <PasswordValidation password={user.password} setUser={setUser} />
      <button type="submit">Sign In</button>
      {/*<Footer />*/}
    </form>
  );
};
