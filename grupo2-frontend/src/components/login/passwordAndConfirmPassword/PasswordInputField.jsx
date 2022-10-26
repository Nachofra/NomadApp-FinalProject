function PasswordInputField({
  handleValidation,
  handlePasswordChange,
  passwordValue,
  passwordError,
}) {
  return (
    <>
      <div>
        <label>
          <strong>Password</strong>
        </label>
        <input
          type="password"
          value={passwordValue}
          onChange={handlePasswordChange}
          onKeyUp={handleValidation}
          name="password"
          placeholder="Password"
        />
        <p>{passwordError}</p>
      </div>
    </>
  );
}

export default PasswordInputField;
