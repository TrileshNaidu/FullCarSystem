import axios from 'axios';

const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
};

export const vehicleService = {
  // Only keep getUserVehicles and getVehicle for dashboard/details if needed
  async getUserVehicles() {
    const response = await axios.get(`/api/vehicles/user`, { headers: getAuthHeader() });
    return response.data;
  },
  async getVehicle(id: any) {
    const response = await axios.get(`/api/vehicles/${id}`);
    return response.data;
  },
};

export const rentalService = {
  // Only keep getUserRentals for dashboard if needed
  async getUserRentals() {
    const response = await axios.get(`/api/rental-vehicles/user`, { headers: getAuthHeader() });
    return response.data;
  },
};

export const bookingService = {
  async getBookings(page = 0, size = 10) {
    const response = await axios.get(`/api/bookings`, { params: { page, size }, headers: getAuthHeader() });
    return response.data;
  },
  async getBooking(id: any) {
    const response = await axios.get(`/api/bookings/${id}`, { headers: getAuthHeader() });
    return response.data;
  },
  async createBooking(bookingData: any) {
    const response = await axios.post(`/api/bookings`, bookingData, { headers: getAuthHeader() });
    return response.data;
  },
  async updateBooking(id: any, bookingData: any) {
    const response = await axios.put(`/api/bookings/${id}`, bookingData, { headers: getAuthHeader() });
    return response.data;
  },
  async deleteBooking(id: any) {
    await axios.delete(`/api/bookings/${id}`, { headers: getAuthHeader() });
  },
};

export const fileService = {
  async uploadFile(file: any, type: any, entity: any, entityId: any) {
    // type: 'image' | 'document', entity: 'vehicles' | 'listings' | 'rentalvehicles', entityId: number
    const formData = new FormData();
    formData.append('file', file);
    const response = await axios.post(`/uploads/${type}s/${entity}/${entityId}`, formData, {
      headers: { ...getAuthHeader(), 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },
};

export const reviewService = {
  async getReviews(vehicleId: any) {
    const response = await axios.get(`/api/vehicles/${vehicleId}/reviews`);
    return response.data;
  },
  async createReview(vehicleId: any, reviewData: any) {
    const response = await axios.post(`/api/vehicles/${vehicleId}/reviews`, reviewData, { headers: getAuthHeader() });
    return response.data;
  },
  async updateReview(vehicleId: any, reviewId: any, reviewData: any) {
    const response = await axios.put(`/api/vehicles/${vehicleId}/reviews/${reviewId}`, reviewData, { headers: getAuthHeader() });
    return response.data;
  },
  async deleteReview(vehicleId: any, reviewId: any) {
    await axios.delete(`/api/vehicles/${vehicleId}/reviews/${reviewId}`, { headers: getAuthHeader() });
  },
};

export const userService = {
  async getUsers(page = 0, size = 10) {
    const response = await axios.get(`/api/users`, { params: { page, size }, headers: getAuthHeader() });
    return response.data;
  },
  async getUserById(id: any) {
    const response = await axios.get(`/api/users/${id}`, { headers: getAuthHeader() });
    return response.data;
  },
  async updateUser(id: any, userData: any) {
    const response = await axios.put(`/api/users/${id}`, userData, { headers: getAuthHeader() });
    return response.data;
  },
  async deleteUser(id: any) {
    await axios.delete(`/api/users/${id}`, { headers: getAuthHeader() });
  },
};

export const listingService = {
  async createListing(listingData: any) {
    const response = await axios.post(`/api/listings`, listingData, { headers: getAuthHeader() });
    return response.data;
  },
  async updateListing(id: any, listingData: any) {
    const response = await axios.put(`/api/listings/${id}`, listingData, { headers: getAuthHeader() });
    return response.data;
  },
};