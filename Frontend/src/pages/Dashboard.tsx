import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { vehicleService, rentalService } from '../services/apiService';

const Dashboard: React.FC = () => {
  const { user, token } = useAuth();
  const navigate = useNavigate();
  const [vehicles, setVehicles] = useState([]);
  const [rentals, setRentals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editMode, setEditMode] = useState(false);
  const [userInfo, setUserInfo] = useState({
    firstName: user?.firstName || '',
    lastName: user?.lastName || '',
    email: user?.email || '',
    phone: user?.phone || '',
  });
  const [updateLoading, setUpdateLoading] = useState(false);
  const [updateError, setUpdateError] = useState('');
  const [updateSuccess, setUpdateSuccess] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const vehiclesData = await vehicleService.getUserVehicles();
        setVehicles(vehiclesData);
        const rentalsData = await rentalService.getUserRentals();
        setRentals(rentalsData);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [user]);

  const handleDeleteVehicle = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this vehicle?')) {
      try {
        await vehicleService.deleteVehicle(id);
        setVehicles(vehicles.filter((v: any) => v.id !== id));
      } catch (err: any) {
        setError(err.message);
      }
    }
  };

  const handleDeleteRental = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this rental?')) {
      try {
        await rentalService.deleteRental(id);
        setRentals(rentals.filter((r: any) => r.id !== id));
      } catch (err: any) {
        setError(err.message);
      }
    }
  };

  const handleUserInfoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserInfo({ ...userInfo, [e.target.name]: e.target.value });
  };

  const handleUserInfoUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    setUpdateLoading(true);
    setUpdateError('');
    setUpdateSuccess('');
    try {
      // Replace with your actual user update API call
      await fetch(`/api/users/${user.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(userInfo),
      });
      setUpdateSuccess('Profile updated!');
      setEditMode(false);
    } catch (err) {
      setUpdateError('Failed to update profile');
    } finally {
      setUpdateLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="rounded-full h-12 w-12 border-b-2 border-blue-500 border-4 animate-spin"></div>
      </div>
    );
  }

  return (
    <div className="px-4 py-8 bg-white min-h-screen dark:bg-gray-900">
      <div className="mb-8 flex flex-col md:flex-row md:items-center md:justify-between">
        <div>
          <h1 className="text-3xl font-bold text-blue-800 dark:text-white">Dashboard</h1>
          <p className="mt-2 text-base text-blue-700 dark:text-blue-200">
            Welcome back, <span className="font-semibold">{user?.firstName}!</span> Manage your listings and rentals.
          </p>
        </div>
        <div className="flex space-x-4 mt-4 md:mt-0">
          <button
            onClick={() => navigate('/listings/new/sale')}
            className="px-4 py-2 bg-blue-600 text-white rounded font-medium hover:bg-blue-700"
          >
            Sell Car
          </button>
          <button
            onClick={() => navigate('/listings/new/rental')}
            className="px-4 py-2 bg-green-600 text-white rounded font-medium hover:bg-green-700"
          >
            Rent Car
          </button>
        </div>
      </div>
      {error && (
        <div className="mb-4 bg-red-100 border border-red-200 text-red-700 px-4 py-2 rounded">
          {error}
        </div>
      )}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 space-y-8">
          <div className="bg-white border border-blue-100 rounded-lg p-6">
            <h3 className="text-lg font-semibold text-blue-800 mb-4">Your Vehicles for Sale</h3>
            {vehicles.length === 0 ? (
              <p className="text-blue-500">No vehicles listed for sale.</p>
            ) : (
              <div className="space-y-4">
                {vehicles.map((vehicle: any) => (
                  <div key={vehicle.id} className="border rounded-lg p-4 flex flex-col items-center bg-blue-50 dark:bg-gray-900 mb-4">
                    {/* Card Cover Image */}
                    {vehicle.images && vehicle.images.length > 0 ? (
                      <img src={vehicle.images[0]} alt="Vehicle" className="w-full h-40 object-cover rounded mb-2" />
                    ) : (
                      <div className="w-full h-40 flex items-center justify-center bg-blue-100 dark:bg-gray-800 rounded mb-2">
                        {/* Optionally, a car icon here if no image */}
                      </div>
                    )}
                    <div className="flex-1 flex flex-col items-center justify-center mb-4">
                      <h4 className="text-xl font-bold text-blue-800 dark:text-white mb-1">{vehicle.make} {vehicle.model}</h4>
                      <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mb-2">
                        <span>{vehicle.year}</span>
                        <span className="ml-3">{vehicle.mileage} km</span>
                      </div>
                      <div className="flex items-center gap-2 text-lg font-semibold text-blue-800 dark:text-blue-200">
                        ₹{vehicle.price}
                        {vehicle.priceNegotiable && <span className="ml-2 text-xs bg-green-100 text-green-700 px-2 py-0.5 rounded">Negotiable</span>}
                      </div>
                      <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mt-2">
                        {vehicle.location}
                      </div>
                      <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mt-2">
                        Status: <span className="font-medium">{vehicle.status}</span>
                      </div>
                    </div>
                    <div className="flex space-x-2 mt-2">
                      <Link
                        to={`/vehicles/${vehicle.id}/edit`}
                        className="text-blue-600 hover:text-blue-500 text-sm"
                      >
                        Edit
                      </Link>
                      <button
                        onClick={() => handleDeleteVehicle(vehicle.id)}
                        className="text-red-600 hover:text-red-500 text-sm"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
          <div className="bg-white border border-blue-100 rounded-lg p-6">
            <h3 className="text-lg font-semibold text-blue-800 mb-4">Your Rental Vehicles</h3>
            {rentals.length === 0 ? (
              <p className="text-blue-500">No vehicles listed for rent.</p>
            ) : (
              <div className="space-y-4">
                {rentals.map((rental: any) => (
                  <div key={rental.id} className="border rounded-lg p-4 flex flex-col items-center bg-blue-50 dark:bg-gray-900 mb-4">
                    {/* Card Cover Image */}
                    {rental.images && rental.images.length > 0 ? (
                      <img src={rental.images[0]} alt="Vehicle" className="w-full h-40 object-cover rounded mb-2" />
                    ) : (
                      <div className="w-full h-40 flex items-center justify-center bg-blue-100 dark:bg-gray-800 rounded mb-2">
                        {/* Optionally, a car icon here if no image */}
                      </div>
                    )}
                    <div className="flex-1 flex flex-col items-center justify-center mb-4">
                      <h4 className="text-xl font-bold text-blue-800 dark:text-white mb-1">{rental.make} {rental.model}</h4>
                      <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mb-2">
                        <span>{rental.year}</span>
                        <span className="ml-3">{rental.mileage} km</span>
                      </div>
                      <div className="flex items-center gap-2 text-lg font-semibold text-blue-800 dark:text-blue-200">
                        ₹{rental.dailyRate}/day
                        {rental.priceNegotiable && <span className="ml-2 text-xs bg-green-100 text-green-700 px-2 py-0.5 rounded">Negotiable</span>}
                      </div>
                      <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mt-2">
                        {rental.location}
                      </div>
                      <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mt-2">
                        Status: <span className="font-medium">{rental.status}</span>
                      </div>
                    </div>
                    <div className="flex space-x-2 mt-2">
                      <Link
                        to={`/rentals/${rental.id}/edit`}
                        className="text-blue-600 hover:text-blue-500 text-sm"
                      >
                        Edit
                      </Link>
                      <button
                        onClick={() => handleDeleteRental(rental.id)}
                        className="text-red-600 hover:text-red-500 text-sm"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
        <div>
          <div className="bg-white border border-blue-100 rounded-lg p-6">
            <h3 className="text-lg font-semibold text-blue-800 mb-4">Profile Information</h3>
            {editMode ? (
              <form onSubmit={handleUserInfoUpdate} className="space-y-3">
                <input
                  name="firstName"
                  value={userInfo.firstName}
                  onChange={handleUserInfoChange}
                  placeholder="First Name"
                  className="w-full border rounded px-2 py-1"
                />
                <input
                  name="lastName"
                  value={userInfo.lastName}
                  onChange={handleUserInfoChange}
                  placeholder="Last Name"
                  className="w-full border rounded px-2 py-1"
                />
                <input
                  name="email"
                  value={userInfo.email}
                  onChange={handleUserInfoChange}
                  placeholder="Email"
                  className="w-full border rounded px-2 py-1"
                />
                <input
                  name="phone"
                  value={userInfo.phone}
                  onChange={handleUserInfoChange}
                  placeholder="Phone"
                  className="w-full border rounded px-2 py-1"
                />
                <div className="flex gap-2">
                  <button type="submit" className="bg-blue-600 text-white px-4 py-1 rounded" disabled={updateLoading}>
                    {updateLoading ? 'Saving...' : 'Save'}
                  </button>
                  <button type="button" className="bg-gray-300 px-4 py-1 rounded" onClick={() => setEditMode(false)}>
                    Cancel
                  </button>
                </div>
                {updateError && <div className="text-red-600">{updateError}</div>}
                {updateSuccess && <div className="text-green-600">{updateSuccess}</div>}
              </form>
            ) : (
              <div className="space-y-3">
                <div>
                  <label className="text-sm font-medium text-blue-700">Name</label>
                  <p className="text-sm text-blue-800">{user?.firstName} {user?.lastName}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-blue-700">Email</label>
                  <p className="text-sm text-blue-800">{user?.email}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-blue-700">Phone</label>
                  <p className="text-sm text-blue-800">{user?.phone || 'Not provided'}</p>
                </div>
                <button className="bg-blue-600 text-white px-4 py-1 rounded" onClick={() => setEditMode(true)}>
                  Update Info
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;