import axios from 'axios';

export const authService = {
  async login(username: string, password: string) {
    const payload = { username, password };
    const response = await axios.post('/api/users/login', payload);
    // Backend returns { token, username, role }
    return response.data;
  },

  async register(userData: any) {
    const response = await axios.post('/api/users', userData);
    return { user: response.data };
  },

  async getProfile(token: string) {
    const response = await axios.get('/api/users/me', {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  },
};