function ConfirmPasswordInputField({
  handleValidation,
  handlePasswordChange,
  confirmPasswordValue,
  confirmPasswordError,
}) {
  return (
    <>
      <div>
        <label>
          <strong>Confirm Password</strong>
        </label>
        <input
          type="password"
          value={confirmPasswordValue}
          onChange={handlePasswordChange}
          onKeyUp={handleValidation}
          name="confirmPassword"
          placeholder="Confirm Password"
        />
        <p>{confirmPasswordError}</p>
      </div>
    </>
  );
}

export default ConfirmPasswordInputField;
