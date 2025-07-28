import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const Register: React.FC = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    mobileNumber: '',
  });
  const [error, setError] = useState('');
  const { register, loading } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    try {
      await register({
        username: formData.username,
        email: formData.email,
        password: formData.password,
        mobileNumber: formData.mobileNumber,
      });
      navigate('/dashboard');
    } catch (err: any) {
      setError(err.message);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="max-w-md w-full bg-white p-8 rounded-lg shadow border border-blue-100">
        <div className="mb-6 text-center">
          <h2 className="text-3xl font-bold text-blue-800 mb-2">Create Account</h2>
          <p className="text-blue-700 text-base">Sign up to get started.</p>
        </div>
        <form className="space-y-4" onSubmit={handleSubmit}>
          {error && (
            <div className="bg-red-100 border border-red-200 text-red-700 px-4 py-2 rounded">
              {error}
            </div>
          )}
          <div>
            <label htmlFor="username" className="block text-sm font-medium text-blue-800 mb-1">
              Username *
            </label>
            <input
              id="username"
              name="username"
              type="text"
              required
              className="w-full px-3 py-2 border border-blue-200 rounded text-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Choose a username"
              value={formData.username}
              onChange={handleChange}
            />
          </div>
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-blue-800 mb-1">
              Email Address *
            </label>
            <input
              id="email"
              name="email"
              type="email"
              required
              className="w-full px-3 py-2 border border-blue-200 rounded text-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Email address"
              value={formData.email}
              onChange={handleChange}
            />
          </div>
          <div>
            <label htmlFor="mobileNumber" className="block text-sm font-medium text-blue-800 mb-1">
              Mobile Number *
            </label>
            <input
              id="mobileNumber"
              name="mobileNumber"
              type="tel"
              pattern="^[6-9]\d{9}$"
              required
              className="w-full px-3 py-2 border border-blue-200 rounded text-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="10-digit Indian mobile number"
              value={formData.mobileNumber}
              onChange={handleChange}
            />
          </div>
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-blue-800 mb-1">
              Password *
            </label>
            <input
              id="password"
              name="password"
              type="password"
              minLength={8}
              required
              className="w-full px-3 py-2 border border-blue-200 rounded text-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Password (min 8 characters)"
              value={formData.password}
              onChange={handleChange}
            />
          </div>
          <div>
            <label htmlFor="confirmPassword" className="block text-sm font-medium text-blue-800 mb-1">
              Confirm Password *
            </label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              minLength={8}
              required
              className="w-full px-3 py-2 border border-blue-200 rounded text-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Confirm Password"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 px-4 bg-blue-600 text-white text-base font-semibold rounded hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
          >
            {loading ? 'Creating account...' : 'Create Account'}
          </button>
          <div className="text-center mt-4">
            <Link to="/login" className="text-blue-600 hover:text-blue-500 font-medium hover:underline">
              Already have an account? Sign in
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;