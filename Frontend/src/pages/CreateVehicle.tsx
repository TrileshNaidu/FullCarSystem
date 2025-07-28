import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fileService, listingService } from '../services/apiService';
import FileUpload from '../components/FileUpload';
import axios from 'axios';

interface CreateListingProps {
  mode: 'SALE' | 'RENTAL';
}

const CreateListing: React.FC<CreateListingProps> = ({ mode }) => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    make: '',
    model: '',
    year: '',
    mileage: '',
    kmDriven: '',
    price: '',
    dailyRate: '',
    type: '',
    color: '',
    location: '',
    description: '',
    vehicleNumber: '',
    ownerName: '',
    priceNegotiable: false,
    contactNumber: '',
    features: [],
    availabilityStartDate: '',
    availabilityEndDate: '',
    selfDriving: false,
    withDriver: false,
  });
  const [images, setImages] = useState<File[]>([]);
  const [documents, setDocuments] = useState<File[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleFeatureChange = (feature: string) => {
    setFormData((prev) => {
      const features = prev.features.includes(feature)
        ? prev.features.filter((f: string) => f !== feature)
        : [...prev.features, feature];
      return { ...prev, features };
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      // Create listing for sale or rental
      const listingData: any = {
        ...formData,
        year: parseInt(formData.year),
        mileage: parseInt(formData.mileage),
        price: mode === 'SALE' ? parseFloat(formData.price) : undefined,
        dailyRate: mode === 'RENTAL' ? parseFloat(formData.dailyRate) : undefined,
        listingType: mode,
        images: [],
        vehicleDocuments: [],
      };
      const createdListing = await listingService.createListing(listingData);
      const listingId = createdListing.id;

      // Upload images
      const imageUrls = await Promise.all(
        images.map(async (file) => {
          const response = await fileService.uploadFile(file, 'image', 'listings', listingId);
          return response.url;
        })
      );

      // Upload documents
      const documentUrls = await Promise.all(
        documents.map(async (file) => {
          const response = await fileService.uploadFile(file, 'document', 'listings', listingId);
          return response.url;
        })
      );

      // Update listing with image/document URLs
      await listingService.updateListing(listingId, {
        images: imageUrls,
        vehicleDocuments: documentUrls,
      });

      navigate(mode === 'SALE' ? '/vehicles' : '/rentals');
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="px-4 py-8 bg-white min-h-screen">
      <div className="max-w-3xl mx-auto">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-blue-800">{mode === 'SALE' ? 'List Your Car for Sale' : 'List Your Car for Rent'}</h1>
          <p className="mt-2 text-base text-blue-700">
            Fill in the details about your vehicle to create a {mode === 'SALE' ? 'sale' : 'rental'} listing.{mode === 'RENTAL' ? ' Your listing will be pending approval.' : ''}
          </p>
        </div>
        {error && (
          <div className="mb-4 bg-red-100 border border-red-200 text-red-700 px-4 py-2 rounded">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="bg-white border border-blue-100 rounded-lg p-6">
            <h2 className="text-lg font-semibold text-blue-800 mb-4">Vehicle Information</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label htmlFor="make" className="block text-sm font-medium text-blue-800">
                  Make *
                </label>
                <input
                  type="text"
                  id="make"
                  name="make"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.make}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <label htmlFor="model" className="block text-sm font-medium text-blue-800">
                  Model *
                </label>
                <input
                  type="text"
                  id="model"
                  name="model"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.model}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <label htmlFor="year" className="block text-sm font-medium text-blue-800">
                  Year *
                </label>
                <input
                  type="number"
                  id="year"
                  name="year"
                  required
                  min="1900"
                  max="2025"
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.year}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <label htmlFor="mileage" className="block text-sm font-medium text-blue-800">
                  Mileage *
                </label>
                <input
                  type="number"
                  id="mileage"
                  name="mileage"
                  required
                  min="0"
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.mileage}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <label htmlFor="price" className="block text-sm font-medium text-blue-800">
                  Price (₹) *
                </label>
                <input
                  type="number"
                  id="price"
                  name="price"
                  required
                  min="0"
                  step="0.01"
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.price}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <label htmlFor="fuelType" className="block text-sm font-medium text-blue-800">
                  Fuel Type *
                </label>
                <select
                  id="fuelType"
                  name="fuelType"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.fuelType}
                  onChange={handleChange}
                >
                  <option value="">Select fuel type</option>
                  <option value="GASOLINE">Gasoline</option>
                  <option value="DIESEL">Diesel</option>
                  <option value="HYBRID">Hybrid</option>
                  <option value="ELECTRIC">Electric</option>
                </select>
              </div>
              
              <div>
                <label htmlFor="transmission" className="block text-sm font-medium text-blue-800">
                  Transmission *
                </label>
                <select
                  id="transmission"
                  name="transmission"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.transmission}
                  onChange={handleChange}
                >
                  <option value="">Select transmission</option>
                  <option value="MANUAL">Manual</option>
                  <option value="AUTOMATIC">Automatic</option>
                  <option value="CVT">CVT</option>
                </select>
              </div>
              
              <div>
                <label htmlFor="bodyType" className="block text-sm font-medium text-blue-800">
                  Body Type *
                </label>
                <select
                  id="bodyType"
                  name="bodyType"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.bodyType}
                  onChange={handleChange}
                >
                  <option value="">Select body type</option>
                  <option value="SEDAN">Sedan</option>
                  <option value="SUV">SUV</option>
                  <option value="HATCHBACK">Hatchback</option>
                  <option value="COUPE">Coupe</option>
                  <option value="CONVERTIBLE">Convertible</option>
                  <option value="TRUCK">Truck</option>
                  <option value="VAN">Van</option>
                </select>
              </div>
              
              <div>
                <label htmlFor="color" className="block text-sm font-medium text-blue-800">
                  Color *
                </label>
                <input
                  type="text"
                  id="color"
                  name="color"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.color}
                  onChange={handleChange}
                />
              </div>
              
              <div>
                <label htmlFor="location" className="block text-sm font-medium text-blue-800">
                  Location *
                </label>
                <input
                  type="text"
                  id="location"
                  name="location"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.location}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label htmlFor="vehicleNumber" className="block text-sm font-medium text-blue-800">
                  Vehicle Number *
                </label>
                <input
                  type="text"
                  id="vehicleNumber"
                  name="vehicleNumber"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.vehicleNumber}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label htmlFor="ownerName" className="block text-sm font-medium text-blue-800">
                  Owner Name *
                </label>
                <input
                  type="text"
                  id="ownerName"
                  name="ownerName"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.ownerName}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label htmlFor="type" className="block text-sm font-medium text-blue-800">
                  Type *
                </label>
                <select
                  id="type"
                  name="type"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.type}
                  onChange={handleChange}
                >
                  <option value="">Select type</option>
                  <option value="SEDAN">Sedan</option>
                  <option value="SUV">SUV</option>
                  <option value="HATCHBACK">Hatchback</option>
                  <option value="COUPE">Coupe</option>
                  <option value="CONVERTIBLE">Convertible</option>
                  <option value="TRUCK">Truck</option>
                  <option value="VAN">Van</option>
                </select>
              </div>
              <div>
                <label htmlFor="kmDriven" className="block text-sm font-medium text-blue-800">
                  KM Driven *
                </label>
                <input
                  type="number"
                  id="kmDriven"
                  name="kmDriven"
                  required
                  min="0"
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.kmDriven}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label htmlFor="contactNumber" className="block text-sm font-medium text-blue-800">
                  Contact Number *
                </label>
                <input
                  type="text"
                  id="contactNumber"
                  name="contactNumber"
                  required
                  className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                  value={formData.contactNumber}
                  onChange={handleChange}
                />
              </div>
              <div className="flex items-center mt-2">
                <input
                  type="checkbox"
                  id="priceNegotiable"
                  name="priceNegotiable"
                  checked={formData.priceNegotiable}
                  onChange={e => setFormData({ ...formData, priceNegotiable: e.target.checked })}
                  className="mr-2"
                />
                <label htmlFor="priceNegotiable" className="text-blue-800">Price Negotiable</label>
              </div>
              <div>
                <label className="block text-sm font-medium text-blue-800 mb-1">Features</label>
                <div className="flex flex-wrap gap-2">
                  {['Air Conditioning', 'Power Steering', 'Power Windows', 'ABS', 'Airbags', 'Bluetooth', 'Backup Camera', 'Cruise Control', 'Navigation System', 'Leather Seats'].map((feature) => (
                    <label key={feature} className="flex items-center text-blue-700">
                      <input
                        type="checkbox"
                        checked={formData.features.includes(feature)}
                        onChange={() => handleFeatureChange(feature)}
                        className="mr-1"
                      />
                      {feature}
                    </label>
                  ))}
                </div>
              </div>
            </div>
            <div className="mt-6">
              <label htmlFor="description" className="block text-sm font-medium text-blue-800">
                Description
              </label>
              <textarea
                id="description"
                name="description"
                rows={4}
                className="mt-1 block w-full rounded-md border-blue-200 focus:border-blue-500 focus:ring-blue-500 text-blue-800"
                value={formData.description}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="bg-white border border-blue-100 rounded-lg p-6">
            <h2 className="text-lg font-semibold text-blue-800 mb-4">Images</h2>
            <FileUpload
              files={images}
              onChange={setImages}
              accept="image/*"
              maxFiles={10}
              maxSize={10 * 1024 * 1024} // 10MB
              label="Upload vehicle images"
            />
          </div>
          <div className="bg-white border border-blue-100 rounded-lg p-6">
            <h2 className="text-lg font-semibold text-blue-800 mb-4">Documents</h2>
            <FileUpload
              files={documents}
              onChange={setDocuments}
              accept=".pdf,.doc,.docx"
              maxFiles={5}
              maxSize={10 * 1024 * 1024} // 10MB
              label="Upload vehicle documents (registration, maintenance records, etc.)"
            />
          </div>
          <div className="flex justify-end space-x-4">
            <button
              type="button"
              onClick={() => navigate('/dashboard')}
              className="px-4 py-2 border border-blue-200 rounded text-sm font-medium text-blue-800 hover:bg-blue-50"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading}
              className="px-4 py-2 border border-transparent rounded text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 disabled:opacity-50"
            >
              {loading ? 'Creating...' : 'Create Listing'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateListing;