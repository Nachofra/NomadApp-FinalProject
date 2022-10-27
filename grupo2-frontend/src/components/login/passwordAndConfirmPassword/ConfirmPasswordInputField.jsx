function ConfirmPasswordInputField({
  handleValidation,
  handlePasswordChange,
  confirmPasswordValue,
  confirmPasswordError,
}) {
  return (
    <div className="w-full mt-4">
      <input
        type="password"
        value={confirmPasswordValue}
        onChange={handlePasswordChange}
        onKeyUp={handleValidation}
        name="confirmPassword"
        placeholder="Confirm Password"
        className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-400 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300"
      />
      <p>{confirmPasswordError}</p>
    </div>
  );
}

export default ConfirmPasswordInputField;
