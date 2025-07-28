import React, { useState, useEffect } from 'react';
import { userService, rentalService, bookingService } from '../services/apiService';
import axios from 'axios';

const AdminPanel: React.FC = () => {
  const [users, setUsers] = useState([]);
  const [pendingRentals, setPendingRentals] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [activeTab, setActiveTab] = useState('users');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [usersData, listingsData, bookingsData] = await Promise.all([
        userService.getUsers(),
        axios.get('/api/listings'),
        bookingService.getBookings(0, 100),
      ]);
      setUsers(usersData.content || []);
      const allRentals = (listingsData.data.content || listingsData.data).filter((l: any) => l.listingType === 'RENTAL');
      setPendingRentals(allRentals.filter((r: any) => r.status === 'PENDING'));
      setBookings(bookingsData.content || []);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleApproveRental = async (id: string) => {
    try {
      await rentalService.approveRental(id);
      setPendingRentals(pendingRentals.filter((r: any) => r.id !== id));
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleRejectRental = async (id: string) => {
    try {
      await rentalService.rejectRental(id);
      setPendingRentals(pendingRentals.filter((r: any) => r.id !== id));
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleDeleteUser = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await userService.deleteUser(id);
        setUsers(users.filter((u: any) => u.id !== id));
      } catch (err: any) {
        setError(err.message);
      }
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
    <div className="px-4 py-8 bg-white min-h-screen">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-blue-800">Admin Panel</h1>
        <p className="mt-2 text-base text-blue-700">
          Manage users, rental approvals, and bookings.
        </p>
      </div>
      {error && (
        <div className="mb-4 bg-red-100 border border-red-200 text-red-700 px-4 py-2 rounded">
          {error}
        </div>
      )}
      <div className="mb-8 flex space-x-4 border-b border-blue-100 pb-2">
        <button
          onClick={() => setActiveTab('users')}
          className={`py-2 px-4 border-b-2 font-medium text-sm ${activeTab === 'users' ? 'border-blue-500 text-blue-800' : 'border-transparent text-blue-500 hover:text-blue-700'}`}
        >
          Users ({users.length})
        </button>
        <button
          onClick={() => setActiveTab('rentals')}
          className={`py-2 px-4 border-b-2 font-medium text-sm ${activeTab === 'rentals' ? 'border-blue-500 text-blue-800' : 'border-transparent text-blue-500 hover:text-blue-700'}`}
        >
          Pending Rentals ({pendingRentals.length})
        </button>
        <button
          onClick={() => setActiveTab('bookings')}
          className={`py-2 px-4 border-b-2 font-medium text-sm ${activeTab === 'bookings' ? 'border-blue-500 text-blue-800' : 'border-transparent text-blue-500 hover:text-blue-700'}`}
        >
          Bookings ({bookings.length})
        </button>
      </div>
      <div>
        {activeTab === 'users' && (
          <div className="bg-white border border-blue-100 rounded-lg overflow-hidden mb-8">
            <div className="px-4 py-5">
              <h3 className="text-lg font-semibold text-blue-800 mb-4">Users</h3>
              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-blue-100">
                  <thead className="bg-blue-50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Name</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Email</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Role</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Actions</th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-blue-100">
                    {users.map((user: any) => (
                      <tr key={user.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm font-medium text-blue-800">{user.firstName} {user.lastName}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-blue-800">{user.email}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800">{user.role}</span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                          <button
                            onClick={() => handleDeleteUser(user.id)}
                            className="text-red-600 hover:text-red-800"
                          >
                            Delete
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}
        {activeTab === 'rentals' && (
          <div className="bg-white border border-blue-100 rounded-lg overflow-hidden mb-8">
            <div className="px-4 py-5">
              <h3 className="text-lg font-semibold text-blue-800 mb-4">Pending Rental Approvals</h3>
              <div className="space-y-4">
                {pendingRentals.map((rental: any) => (
                  <div key={rental.id} className="border rounded-lg p-4">
                    <div className="flex items-center justify-between">
                      <div className="flex-1">
                        <h4 className="font-medium text-blue-800">{rental.year} {rental.make} {rental.model}</h4>
                        <p className="text-sm text-blue-700">{rental.dailyRate.toLocaleString('en-IN')}/day · {rental.location}</p>
                        <p className="text-sm text-blue-700">Owner: {rental.owner?.firstName} {rental.owner?.lastName}</p>
                        <p className="text-sm text-blue-700">Email: {rental.owner?.email}</p>
                      </div>
                      <div className="flex space-x-2">
                        <button
                          onClick={() => handleApproveRental(rental.id)}
                          className="bg-blue-600 text-white px-3 py-1 rounded text-sm hover:bg-blue-700"
                        >
                          Approve
                        </button>
                        <button
                          onClick={() => handleRejectRental(rental.id)}
                          className="bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700"
                        >
                          Reject
                        </button>
                      </div>
                    </div>
                  </div>
                ))}
                {pendingRentals.length === 0 && (
                  <p className="text-blue-500 text-center py-8">No pending rentals to approve.</p>
                )}
              </div>
            </div>
          </div>
        )}
        {activeTab === 'bookings' && (
          <div className="bg-white border border-blue-100 rounded-lg overflow-hidden mb-8">
            <div className="px-4 py-5">
              <h3 className="text-lg font-semibold text-blue-800 mb-4">Bookings</h3>
              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-blue-100">
                  <thead className="bg-blue-50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Vehicle</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Renter</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Dates</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Status</th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-blue-700 uppercase tracking-wider">Total</th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-blue-100">
                    {bookings.map((booking: any) => (
                      <tr key={booking.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm font-medium text-blue-800">{booking.rental.year} {booking.rental.make} {booking.rental.model}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-blue-800">{booking.renter.firstName} {booking.renter.lastName}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-blue-800">{booking.startDate} - {booking.endDate}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800">{booking.status}</span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-blue-800">${booking.totalAmount}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
              {bookings.length === 0 && (
                <p className="text-blue-500 text-center py-8">No bookings found.</p>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminPanel;