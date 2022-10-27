import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PublicRoutes } from "../../guard/routes";
import { Footer } from "../../components/partials/footer/Footer";
import { useUserContext } from "../../context/UserContext";
import Email from "../../components/login/email";
import PasswordValidation from "../../components/login/passwordAndConfirmPassword/PasswordValidation";

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
    <form onSubmit={handleSubmit}>
      <Email email={ingressedUser.email} setUser={setIngressedUser} />
      <PasswordValidation password={ingressedUser.password} setUser={setIngressedUser} />
      {usuarioIncorrecto && <p>Por favor vuelva a intentarlo, sus credenciales son inválidas</p>}
      <button type="submit">Sign In</button>
      {/*<Footer />*/}
    </form>
  );
};
