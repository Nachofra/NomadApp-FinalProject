function PasswordInputField({
  handleValidation,
  handlePasswordChange,
  passwordValue,
  passwordError,
}) {
  return (
    <div className="w-full mt-4">
      <input
        type="password"
        value={passwordValue}
        onChange={handlePasswordChange}
        onKeyUp={handleValidation}
        name="password"
        placeholder="Password"
        className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-500 bg-white border rounded-md focus:border-blue-400 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300"
      />
      <p>{passwordError}</p>
    </div>
  );
}

export default PasswordInputField;
