import React, { useEffect, useState } from 'react';
import { Search, Car, BadgeDollarSign, Calendar, Gauge, MapPin } from 'lucide-react';
import axios from 'axios';

const Vehicles: React.FC = () => {
  const [vehicles, setVehicles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [year, setYear] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [expandedId, setExpandedId] = useState<string | number | null>(null);

  useEffect(() => {
    axios.get('/api/listings').then(res => {
      const saleListings = (res.data.content || res.data).filter((l: any) => l.listingType === 'SALE' || l.listing_type === 'SALE');
      setVehicles(saleListings);
      setLoading(false);
    });
  }, []);

  const filteredVehicles = vehicles.filter((v: any) => {
    const matchesSearch =
      v.make?.toLowerCase().includes(search.toLowerCase()) ||
      v.model?.toLowerCase().includes(search.toLowerCase());
    const matchesYear = year ? String(v.year) === year : true;
    const matchesMinPrice = minPrice ? v.price >= Number(minPrice) : true;
    const matchesMaxPrice = maxPrice ? v.price <= Number(maxPrice) : true;
    return matchesSearch && matchesYear && matchesMinPrice && matchesMaxPrice;
  });

  if (loading) return <div className="p-8 text-blue-700 dark:text-blue-200">Loading...</div>;

  return (
    <div className="px-4 py-8 bg-white dark:bg-gray-950 min-h-screen transition-colors duration-300">
      <h1 className="text-3xl font-bold text-blue-800 dark:text-white mb-6">Available Cars for Sale</h1>
      {/* Search/Filter */}
      <div className="mb-8 flex flex-col md:flex-row gap-4 items-center bg-blue-50 dark:bg-gray-900 p-4 rounded-lg shadow">
        <div className="flex items-center gap-2 w-full md:w-1/3">
          <Search className="w-5 h-5 text-blue-700 dark:text-blue-300" />
          <input
            type="text"
            placeholder="Search by make or model"
            value={search}
            onChange={e => setSearch(e.target.value)}
            className="w-full px-3 py-2 rounded border border-blue-200 dark:border-gray-700 bg-white dark:bg-gray-950 text-blue-800 dark:text-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <input
          type="number"
          placeholder="Year"
          value={year}
          onChange={e => setYear(e.target.value)}
          className="w-full md:w-24 px-3 py-2 rounded border border-blue-200 dark:border-gray-700 bg-white dark:bg-gray-950 text-blue-800 dark:text-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="number"
          placeholder="Min Price"
          value={minPrice}
          onChange={e => setMinPrice(e.target.value)}
          className="w-full md:w-28 px-3 py-2 rounded border border-blue-200 dark:border-gray-700 bg-white dark:bg-gray-950 text-blue-800 dark:text-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="number"
          placeholder="Max Price"
          value={maxPrice}
          onChange={e => setMaxPrice(e.target.value)}
          className="w-full md:w-28 px-3 py-2 rounded border border-blue-200 dark:border-gray-700 bg-white dark:bg-gray-950 text-blue-800 dark:text-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      {/* Vehicle Cards */}
      <div className="grid md:grid-cols-3 gap-8">
        {filteredVehicles.length === 0 && (
          <div className="col-span-full text-center text-blue-700 dark:text-blue-200">No vehicles found.</div>
        )}
        {filteredVehicles.map((v: any) => (
          <div key={v.id} className="border border-blue-100 dark:border-gray-800 rounded-lg p-4 bg-blue-50 dark:bg-gray-900 shadow hover:shadow-lg transition group flex flex-col mb-4">
            {/* Card Cover Image */}
            {v.images && v.images.length > 0 && (
              <img src={v.images[0]} alt="Vehicle" className="w-full h-40 object-cover rounded mb-2" />
            )}
            console.log(r.images[0]);
            <div className="flex-1 flex flex-col items-center justify-center mb-4">
              <Car className="w-16 h-16 text-blue-700 dark:text-blue-400 mb-2" />
              <h2 className="text-xl font-bold text-blue-800 dark:text-white mb-1">{v.make} {v.model}</h2>
              <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mb-2">
                <Calendar className="w-4 h-4" /> {v.year}
                <Gauge className="w-4 h-4 ml-3" /> {v.mileage} km
              </div>
              <div className="flex items-center gap-2 text-lg font-semibold text-blue-800 dark:text-blue-200">
                <BadgeDollarSign className="w-5 h-5" /> ₹{v.price}
                {v.priceNegotiable && <span className="ml-2 text-xs bg-green-100 text-green-700 px-2 py-0.5 rounded">Negotiable</span>}
              </div>
              <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mt-2">
                <MapPin className="w-4 h-4" /> {v.location}
              </div>
            </div>
            <button
              className="mt-auto inline-block px-4 py-2 bg-blue-700 text-white rounded font-medium hover:bg-blue-800 dark:bg-blue-600 dark:hover:bg-blue-700 transition text-center"
              onClick={() => setExpandedId(expandedId === v.id ? null : v.id)}
            >
              {expandedId === v.id ? 'Hide Details' : 'View Details'}
            </button>
            {expandedId === v.id && (
              <div className="mt-4 p-4 bg-white dark:bg-gray-800 rounded shadow-inner w-full">
                <h3 className="text-lg font-bold mb-2 text-blue-800 dark:text-white">Full Details</h3>
                <ul className="text-blue-800 dark:text-blue-100 space-y-1">
                  <li><b>Make:</b> {v.make}</li>
                  <li><b>Model:</b> {v.model}</li>
                  <li><b>Year:</b> {v.year}</li>
                  <li><b>Type:</b> {v.type}</li>
                  <li><b>Color:</b> {v.color}</li>
                  <li><b>Mileage:</b> {v.mileage} km</li>
                  <li><b>KM Driven:</b> {v.kmDriven}</li>
                  <li><b>Vehicle Number:</b> {v.vehicleNumber}</li>
                  <li><b>Owner Name:</b> {v.ownerName}</li>
                  <li><b>Price:</b> ₹{v.price}</li>
                  <li><b>Price Negotiable:</b> {v.priceNegotiable ? 'Yes' : 'No'}</li>
                  <li><b>Location:</b> {v.location}</li>
                  <li><b>Description:</b> {v.description}</li>
                  <li><b>Contact Number:</b> {v.contactNumber}</li>
                  <li><b>Status:</b> {v.status}</li>
                  <li><b>Features:</b> {v.features?.join(', ')}</li>
                </ul>
                {v.images && v.images.length > 0 && (
                  <div className="mt-4">
                    <h4 className="font-semibold mb-2">Images:</h4>
                    <div className="flex gap-2 overflow-x-auto">
                      {v.images.map((img: string, idx: number) => (
                        <img key={idx} src={img} alt="Vehicle" className="w-32 h-24 object-cover rounded" />
                      ))}
                    </div>
                  </div>
                )}
                {v.vehicleDocuments && v.vehicleDocuments.length > 0 && (
                  <div className="mt-4">
                    <h4 className="font-semibold mb-2">Documents:</h4>
                    <ul>
                      {v.vehicleDocuments.map((doc: string, idx: number) => (
                        <li key={idx}><a href={doc} target="_blank" rel="noopener noreferrer" className="text-blue-600 underline">Document {idx + 1}</a></li>
                      ))}
                    </ul>
                  </div>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Vehicles;
