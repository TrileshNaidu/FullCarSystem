import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { vehicleService, reviewService } from '../services/apiService';
import { useAuth } from '../contexts/AuthContext';
import ReviewForm from '../components/ReviewForm';
import ReviewList from '../components/ReviewList';

const VehicleDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [vehicle, setVehicle] = useState<any>(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  useEffect(() => {
    if (id) {
      fetchVehicle();
      fetchReviews();
    }
  }, [id]);

  const fetchVehicle = async () => {
    try {
      const data = await vehicleService.getVehicle(id!);
      setVehicle(data);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchReviews = async () => {
    try {
      const data = await reviewService.getReviews(id!);
      setReviews(data);
    } catch (err: any) {
      console.error('Failed to fetch reviews:', err);
    }
  };

  const handleContactSeller = () => {
    if (!user) {
      navigate('/login');
      return;
    }
    
    // In a real app, this would open a contact form or messaging system
    alert(`Contact ${vehicle.owner.firstName} ${vehicle.owner.lastName} at ${vehicle.owner.email}`);
  };

  const handleReviewSubmit = async (reviewData: any) => {
    try {
      await reviewService.createReview({
        ...reviewData,
        vehicleId: id,
      });
      setShowReviewForm(false);
      fetchReviews();
    } catch (err: any) {
      setError(err.message);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="rounded-full h-12 w-12 border-b-2 border-blue-500 border-4 animate-spin"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-100 border border-red-200 text-red-700 px-4 py-2 rounded">
        {error}
      </div>
    );
  }

  if (!vehicle) {
    return (
      <div className="text-center py-12">
        <p className="text-blue-500">Vehicle not found.</p>
      </div>
    );
  }

  return (
    <div className="px-4 py-8 bg-white min-h-screen">
      <div className="max-w-6xl mx-auto">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Images */}
          <div>
            <div className="aspect-w-16 aspect-h-9 bg-blue-100 rounded-lg mb-4">
              {vehicle.images && vehicle.images.length > 0 ? (
                <img
                  src={vehicle.images[currentImageIndex]}
                  alt={`${vehicle.year} ${vehicle.make} ${vehicle.model}`}
                  className="w-full h-96 object-cover rounded-lg"
                />
              ) : (
                <div className="w-full h-96 bg-blue-100 rounded-lg flex items-center justify-center">
                  <span className="text-blue-400">No Image</span>
                </div>
              )}
            </div>
            {vehicle.images && vehicle.images.length > 1 && (
              <div className="flex space-x-2 overflow-x-auto">
                {vehicle.images.map((image: string, index: number) => (
                  <img
                    key={index}
                    src={image}
                    alt={`View ${index + 1}`}
                    className={`w-20 h-20 object-cover rounded cursor-pointer ${index === currentImageIndex ? 'ring-2 ring-blue-500' : ''}`}
                    onClick={() => setCurrentImageIndex(index)}
                  />
                ))}
              </div>
            )}
          </div>

          {/* Details */}
          <div>
            <h1 className="text-3xl font-bold text-blue-800">
              {vehicle.year} {vehicle.make} {vehicle.model}
            </h1>
            <p className="text-4xl font-bold text-blue-600 mt-2">
              ₹{vehicle.price.toLocaleString('en-IN')}
            </p>

            <div className="mt-6 grid grid-cols-2 gap-4">
              <div>
                <h3 className="text-sm font-medium text-blue-700">Mileage</h3>
                <p className="text-lg text-blue-800">{vehicle.mileage.toLocaleString('en-IN')} km</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-blue-700">Fuel Type</h3>
                <p className="text-lg text-blue-800">{vehicle.fuelType}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-blue-700">Transmission</h3>
                <p className="text-lg text-blue-800">{vehicle.transmission}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-blue-700">Body Type</h3>
                <p className="text-lg text-blue-800">{vehicle.bodyType}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-blue-700">Color</h3>
                <p className="text-lg text-blue-800">{vehicle.color}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-blue-700">Location</h3>
                <p className="text-lg text-blue-800">{vehicle.location}</p>
              </div>
            </div>

            {vehicle.description && (
              <div className="mt-6">
                <h3 className="text-sm font-medium text-blue-700">Description</h3>
                <p className="mt-2 text-blue-800">{vehicle.description}</p>
              </div>
            )}

            <div className="mt-8 space-y-4">
              <button
                onClick={handleContactSeller}
                className="w-full bg-blue-600 text-white py-3 px-4 rounded-md hover:bg-blue-700 font-medium"
              >
                Contact Seller
              </button>
              {user && user.id !== vehicle.owner.id && (
                <button
                  onClick={() => setShowReviewForm(true)}
                  className="w-full bg-blue-100 text-blue-800 py-3 px-4 rounded-md hover:bg-blue-200 font-medium"
                >
                  Write a Review
                </button>
              )}
            </div>

            {/* Seller Info */}
            <div className="mt-6 bg-blue-50 p-4 rounded-lg">
              <h3 className="text-lg font-medium text-blue-800">Seller Information</h3>
              <div className="mt-2">
                <p className="text-blue-700">{vehicle.owner.firstName} {vehicle.owner.lastName}</p>
                <p className="text-blue-600">{vehicle.owner.email}</p>
                {vehicle.owner.phone && (
                  <p className="text-blue-600">{vehicle.owner.phone}</p>
                )}
              </div>
            </div>
          </div>
        </div>

        {/* Reviews */}
        <div className="mt-12">
          <h2 className="text-2xl font-bold text-blue-800">Reviews</h2>
          <ReviewList reviews={reviews} />
        </div>

        {/* Review Form Modal */}
        {showReviewForm && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
            <div className="bg-white rounded-lg p-6 max-w-md w-full">
              <h3 className="text-lg font-medium text-blue-800 mb-4">Write a Review</h3>
              <ReviewForm
                onSubmit={handleReviewSubmit}
                onCancel={() => setShowReviewForm(false)}
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default VehicleDetails;