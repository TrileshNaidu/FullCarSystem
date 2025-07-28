import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { ShieldCheck, BadgeDollarSign, Clock, TrendingUp, Users, MapPin, Smile, ArrowRight, User } from 'lucide-react';

const Home: React.FC = () => {
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-white dark:bg-gray-950 transition-colors duration-300">
      {/* Hero Section */}
      <section className="relative py-28 px-4 text-center bg-gradient-to-br from-blue-50 via-white to-blue-100 dark:from-gray-900 dark:via-gray-950 dark:to-gray-900 overflow-hidden">
        <div className="absolute inset-0 pointer-events-none select-none opacity-10 dark:opacity-20" aria-hidden="true">
          <svg width="100%" height="100%" viewBox="0 0 800 400" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="700" cy="100" r="120" fill="#2563eb" fillOpacity="0.15" />
            <circle cx="100" cy="350" r="80" fill="#1e3a8a" fillOpacity="0.10" />
          </svg>
        </div>
        <div className="relative max-w-4xl mx-auto z-10">
          <h1 className="text-5xl sm:text-6xl font-extrabold mb-6 text-blue-800 dark:text-white drop-shadow-lg">Welcome to CarVerse</h1>
          <p className="text-xl sm:text-2xl text-blue-700 dark:text-blue-200 mb-10 max-w-2xl mx-auto font-medium">Your destination for buying, selling, and renting premium vehicles in India. Experience a modern automotive marketplace with great service.</p>
          <div className="flex flex-col sm:flex-row gap-6 justify-center items-center">
            <Link
              to="/vehicles"
              className="flex items-center gap-2 px-8 py-3 bg-blue-700 text-white text-lg font-semibold rounded shadow hover:bg-blue-800 transition"
            >
              <TrendingUp className="w-5 h-5" /> Browse Vehicles <ArrowRight className="w-4 h-4 ml-1" />
            </Link>
            <Link
              to="/rentals"
              className="flex items-center gap-2 px-8 py-3 bg-blue-100 text-blue-800 text-lg font-semibold rounded shadow hover:bg-blue-200 dark:bg-gray-800 dark:text-blue-200 dark:hover:bg-gray-900 transition"
            >
              <Clock className="w-5 h-5" /> Rent a Car <ArrowRight className="w-4 h-4 ml-1" />
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16 px-4">
        <div className="max-w-5xl mx-auto">
          <h2 className="text-3xl font-bold text-center mb-10 text-blue-800 dark:text-white">Why Choose CarVerse?</h2>
          <div className="grid md:grid-cols-3 gap-8">
            <div className="bg-white dark:bg-gray-900 border border-blue-100 dark:border-gray-800 p-8 rounded-lg text-center shadow hover:shadow-lg transition group">
              <ShieldCheck className="mx-auto mb-3 w-10 h-10 text-blue-700 dark:text-blue-400 group-hover:scale-110 transition" />
              <h3 className="text-xl font-bold text-blue-800 dark:text-white mb-2">Trusted Platform</h3>
              <p className="text-blue-700 dark:text-blue-200 text-base">Verified sellers, secure transactions, and comprehensive vehicle history reports for your peace of mind.</p>
            </div>
            <div className="bg-white dark:bg-gray-900 border border-blue-100 dark:border-gray-800 p-8 rounded-lg text-center shadow hover:shadow-lg transition group">
              <BadgeDollarSign className="mx-auto mb-3 w-10 h-10 text-blue-700 dark:text-blue-400 group-hover:scale-110 transition" />
              <h3 className="text-xl font-bold text-blue-800 dark:text-white mb-2">Best Prices</h3>
              <p className="text-blue-700 dark:text-blue-200 text-base">Competitive pricing with transparent costs. No hidden fees, just honest deals on quality vehicles.</p>
            </div>
            <div className="bg-white dark:bg-gray-900 border border-blue-100 dark:border-gray-800 p-8 rounded-lg text-center shadow hover:shadow-lg transition group">
              <Clock className="mx-auto mb-3 w-10 h-10 text-blue-700 dark:text-blue-400 group-hover:scale-110 transition" />
              <h3 className="text-xl font-bold text-blue-800 dark:text-white mb-2">Quick Service</h3>
              <p className="text-blue-700 dark:text-blue-200 text-base">Fast approvals, instant bookings, and 24/7 customer support to get you on the road quickly.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-16 px-4 bg-blue-50 dark:bg-gray-900">
        <div className="max-w-4xl mx-auto">
          <div className="grid md:grid-cols-4 gap-8 text-center">
            <div>
              <Users className="mx-auto mb-2 w-8 h-8 text-blue-700 dark:text-blue-400" />
              <div className="text-3xl font-bold text-blue-800 dark:text-white mb-1">10,000+</div>
              <div className="text-base text-blue-700 dark:text-blue-200">Vehicles Listed</div>
            </div>
            <div>
              <Smile className="mx-auto mb-2 w-8 h-8 text-blue-700 dark:text-blue-400" />
              <div className="text-3xl font-bold text-blue-800 dark:text-white mb-1">50,000+</div>
              <div className="text-base text-blue-700 dark:text-blue-200">Happy Customers</div>
            </div>
            <div>
              <MapPin className="mx-auto mb-2 w-8 h-8 text-blue-700 dark:text-blue-400" />
              <div className="text-3xl font-bold text-blue-800 dark:text-white mb-1">25+</div>
              <div className="text-base text-blue-700 dark:text-blue-200">Cities Covered</div>
            </div>
            <div>
              <TrendingUp className="mx-auto mb-2 w-8 h-8 text-blue-700 dark:text-blue-400" />
              <div className="text-3xl font-bold text-blue-800 dark:text-white mb-1">99.9%</div>
              <div className="text-base text-blue-700 dark:text-blue-200">Satisfaction Rate</div>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      {!user && (
        <section className="py-16 px-4 text-center bg-gradient-to-r from-blue-100 via-white to-blue-50 dark:from-gray-900 dark:via-gray-950 dark:to-gray-900">
          <div className="max-w-2xl mx-auto">
            <h2 className="text-3xl font-bold mb-6 text-blue-800 dark:text-white">Ready to Get Started?</h2>
            <p className="text-lg text-blue-700 dark:text-blue-200 mb-8">Join thousands of satisfied customers and experience the CarVerse difference today!</p>
            <div className="flex flex-col sm:flex-row gap-6 justify-center">
              <Link
                to="/register"
                className="flex items-center gap-2 px-8 py-3 bg-blue-700 text-white text-lg font-semibold rounded shadow hover:bg-blue-800 transition"
              >
                <User className="w-5 h-5" /> Sign Up Now
              </Link>
              <Link
                to="/login"
                className="flex items-center gap-2 px-8 py-3 bg-blue-100 text-blue-800 text-lg font-semibold rounded shadow hover:bg-blue-200 dark:bg-gray-800 dark:text-blue-200 dark:hover:bg-gray-900 transition"
              >
                <ArrowRight className="w-5 h-5" /> Login
              </Link>
            </div>
          </div>
        </section>
      )}
    </div>
  );
};

export default Home;