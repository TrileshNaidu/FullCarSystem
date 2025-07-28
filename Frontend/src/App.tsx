import React from 'react';
import { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { initializeMockData } from './services/mockData';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Vehicles from './pages/Vehicles';
import VehicleDetails from './pages/VehicleDetails';
import CreateListing from './pages/CreateVehicle'; // Renamed and refactored to CreateListing
import Rentals from './pages/Rentals';
import CreateRental from './pages/CreateRental';
import AdminPanel from './pages/AdminPanel';

function App() {
  useEffect(() => {
    // Initialize mock data when app starts
    initializeMockData();
  }, []);

  return (
    <AuthProvider>
      <Router>
        <Layout>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/vehicles" element={<Vehicles />} />
            <Route path="/vehicles/:id" element={<VehicleDetails />} />
            <Route path="/rentals" element={<Rentals />} />
            
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } />
            
            <Route path="/listings/new/sale" element={<ProtectedRoute><CreateListing mode="SALE" /></ProtectedRoute>} />
            <Route path="/listings/new/rental" element={<ProtectedRoute><CreateListing mode="RENTAL" /></ProtectedRoute>} />
            
            <Route path="/admin" element={
              <ProtectedRoute requiredRole="ADMIN">
                <AdminPanel />
              </ProtectedRoute>
            } />
          </Routes>
        </Layout>
      </Router>
    </AuthProvider>
  );
}

export default App;