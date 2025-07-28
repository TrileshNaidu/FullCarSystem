import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Sun, Moon, User, LogIn, LogOut, Car, ShoppingCart, Home as HomeIcon, LayoutDashboard } from 'lucide-react';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [darkMode, setDarkMode] = useState(false);

  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  }, [darkMode]);

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const isActive = (path: string) => location.pathname === path;

  return (
    <div className="min-h-screen bg-white dark:bg-gray-950 transition-colors duration-300">
      {/* Navigation */}
      <nav className="sticky top-0 z-50 bg-white/90 dark:bg-gray-950/90 backdrop-blur border-b border-blue-100 dark:border-gray-800 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <Link to="/" className="flex items-center space-x-2">
              <span className="text-2xl font-bold text-blue-700 dark:text-white flex items-center"><Car className="w-7 h-7 mr-1" />CarVerse</span>
            </Link>
            {/* Desktop Nav */}
            <div className="hidden md:flex items-center space-x-2 lg:space-x-4">
              <Link to="/" className={`flex items-center px-4 py-2 rounded font-medium transition ${isActive('/') ? 'bg-blue-700 text-white dark:bg-blue-600' : 'text-blue-700 dark:text-blue-100 hover:bg-blue-100 dark:hover:bg-gray-900'}`}><HomeIcon className="w-5 h-5 mr-1" />Home</Link>
              <Link to="/vehicles" className={`flex items-center px-4 py-2 rounded font-medium transition ${isActive('/vehicles') ? 'bg-blue-700 text-white dark:bg-blue-600' : 'text-blue-700 dark:text-blue-100 hover:bg-blue-100 dark:hover:bg-gray-900'}`}><ShoppingCart className="w-5 h-5 mr-1" />Buy Cars</Link>
              <Link to="/rentals" className={`flex items-center px-4 py-2 rounded font-medium transition ${isActive('/rentals') ? 'bg-blue-700 text-white dark:bg-blue-600' : 'text-blue-700 dark:text-blue-100 hover:bg-blue-100 dark:hover:bg-gray-900'}`}><Car className="w-5 h-5 mr-1" />Rent Cars</Link>
              {user && (
                <Link to="/dashboard" className={`flex items-center px-4 py-2 rounded font-medium transition ${isActive('/dashboard') ? 'bg-blue-700 text-white dark:bg-blue-600' : 'text-blue-700 dark:text-blue-100 hover:bg-blue-100 dark:hover:bg-gray-900'}`}><LayoutDashboard className="w-5 h-5 mr-1" />Dashboard</Link>
              )}
              {/* {user && (
                <Link to="/profile" className={`flex items-center px-4 py-2 rounded font-medium transition ${isActive('/profile') ? 'bg-blue-700 text-white dark:bg-blue-600' : 'text-blue-700 dark:text-blue-100 hover:bg-blue-100 dark:hover:bg-gray-900'}`}><User className="w-5 h-5 mr-1" />Profile</Link>
              )} */}
            </div>
            {/* Right Side */}
            <div className="flex items-center space-x-2">
              <button
                onClick={() => setDarkMode(!darkMode)}
                className="p-2 rounded-full bg-blue-100 dark:bg-gray-800 text-blue-700 dark:text-yellow-300 hover:bg-blue-200 dark:hover:bg-gray-700 transition"
                title="Toggle theme"
              >
                {darkMode ? <Moon className="w-5 h-5" /> : <Sun className="w-5 h-5" />}
              </button>
              {user ? (
                <>
                  <span className="hidden md:inline-block mr-2 text-blue-700 dark:text-blue-200 font-semibold">Welcome, {user.firstName}</span>
                  <button
                    onClick={handleLogout}
                    className="flex items-center px-4 py-2 bg-blue-700 text-white rounded font-medium hover:bg-blue-800 dark:bg-blue-600 dark:hover:bg-blue-700 transition"
                  >
                    <LogOut className="w-4 h-4 mr-1" />Logout
                  </button>
                </>
              ) : (
                <>
                  <Link
                    to="/login"
                    className="flex items-center px-4 py-2 bg-blue-700 text-white rounded font-medium hover:bg-blue-800 dark:bg-blue-600 dark:hover:bg-blue-700 transition"
                  >
                    <LogIn className="w-4 h-4 mr-1" />Login
                  </Link>
                </>
              )}
            </div>
            {/* Mobile Nav Toggle (future) */}
          </div>
        </div>
      </nav>
      {/* Main Content */}
      <main className="pt-4 pb-8 px-2 sm:px-4 bg-white dark:bg-gray-950 min-h-[calc(100vh-4rem)] transition-colors duration-300">
        {children}
      </main>
    </div>
  );
};

export default Layout;