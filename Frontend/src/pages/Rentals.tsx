import React, { useEffect, useState } from 'react';
import { Car, BadgeDollarSign, Calendar, MapPin, Gauge, Search } from 'lucide-react';
import axios from 'axios';

const Rentals: React.FC = () => {
  const [rentals, setRentals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [search, setSearch] = useState('');
  const [year, setYear] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [expandedId, setExpandedId] = useState<string | number | null>(null);

  useEffect(() => {
    axios.get('/api/listings').then(res => {
      const rentListings = (res.data.content || res.data)
        .filter((l: any) => (l.listingType === 'RENTAL' || l.listing_type === 'RENTAL') && (l.status === 'APPROVED' || l.status === 'ACTIVE'));
      setRentals(rentListings);
      setLoading(false);
    }).catch(err => {
      setError('Failed to load rentals. Please check your login or try again later.');
      setLoading(false);
    });
  }, []);

  if (loading) return <div className="p-8 text-blue-700 dark:text-blue-200">Loading...</div>;
  if (error) return <div className="p-8 text-red-700 dark:text-red-300">{error}</div>;

  const filteredRentals = rentals.filter((r: any) => {
    const matchesSearch =
      r.make?.toLowerCase().includes(search.toLowerCase()) ||
      r.model?.toLowerCase().includes(search.toLowerCase());
    const matchesYear = year ? String(r.year) === year : true;
    const matchesMinPrice = minPrice ? (r.price || r.dailyRate) >= Number(minPrice) : true;
    const matchesMaxPrice = maxPrice ? (r.price || r.dailyRate) <= Number(maxPrice) : true;
    return matchesSearch && matchesYear && matchesMinPrice && matchesMaxPrice;
  });

  return (
    <div className="px-4 py-8 bg-white dark:bg-gray-950 min-h-screen transition-colors duration-300">
      <h1 className="text-3xl font-bold text-blue-800 dark:text-white mb-6">Available Cars for Rent</h1>
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
      {/* Rental Cards */}
      <div className="grid md:grid-cols-3 gap-8">
        {filteredRentals.length === 0 && (
          <div className="col-span-full text-center text-blue-700 dark:text-blue-200">No rental vehicles found.</div>
        )}
        {filteredRentals.map((r: any) => (
          <div key={r.id} className="border border-blue-100 dark:border-gray-800 rounded-lg p-4 bg-blue-50 dark:bg-gray-900 shadow hover:shadow-lg transition group flex flex-col mb-4">
            {/* Card Cover Image */}
            {r.images && r.images.length > 0 && (
              <img src={r.images[0]} alt="Vehicle" className="w-full h-40 object-cover rounded mb-2" />

            )}
        console.log(r.images[0]);
            <div className="flex-1 flex flex-col items-center justify-center mb-4">
              <Car className="w-16 h-16 text-blue-700 dark:text-blue-400 mb-2" />
              <h2 className="text-xl font-bold text-blue-800 dark:text-white mb-1">{r.make} {r.model}</h2>
              <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mb-2">
                <Calendar className="w-4 h-4" /> {r.year}
                <Gauge className="w-4 h-4 ml-3" /> {r.mileage} km
              </div>
              <div className="flex items-center gap-2 text-lg font-semibold text-blue-800 dark:text-blue-200">
                <BadgeDollarSign className="w-5 h-5" /> ₹{r.price || r.dailyRate}/day
                {r.priceNegotiable && <span className="ml-2 text-xs bg-green-100 text-green-700 px-2 py-0.5 rounded">Negotiable</span>}
              </div>
              <div className="flex items-center gap-2 text-blue-700 dark:text-blue-200 text-sm mt-2">
                <MapPin className="w-4 h-4" /> {r.location}
              </div>
            </div>
            <button
              className="mt-auto inline-block px-4 py-2 bg-blue-700 text-white rounded font-medium hover:bg-blue-800 dark:bg-blue-600 dark:hover:bg-blue-700 transition text-center"
              onClick={() => setExpandedId(expandedId === r.id ? null : r.id)}
            >
              {expandedId === r.id ? 'Hide Details' : 'View Details'}
            </button>
            {expandedId === r.id && (
              <div className="mt-4 p-4 bg-white dark:bg-gray-800 rounded shadow-inner w-full">
                <h3 className="text-lg font-bold mb-2 text-blue-800 dark:text-white">Full Details</h3>
                <ul className="text-blue-800 dark:text-blue-100 space-y-1">
                  <li><b>Make:</b> {r.make}</li>
                  <li><b>Model:</b> {r.model}</li>
                  <li><b>Year:</b> {r.year}</li>
                  <li><b>Type:</b> {r.type}</li>
                  <li><b>Color:</b> {r.color}</li>
                  <li><b>Mileage:</b> {r.mileage} km</li>
                  <li><b>KM Driven:</b> {r.kmDriven}</li>
                  <li><b>Vehicle Number:</b> {r.vehicleNumber}</li>
                  <li><b>Owner Name:</b> {r.ownerName}</li>
                  <li><b>Daily Rate:</b> ₹{r.dailyRate}</li>
                  <li><b>Price Negotiable:</b> {r.priceNegotiable ? 'Yes' : 'No'}</li>
                  <li><b>Location:</b> {r.location}</li>
                  <li><b>Description:</b> {r.description}</li>
                  <li><b>Contact Number:</b> {r.contactNumber}</li>
                  <li><b>Status:</b> {r.status}</li>
                  <li><b>Availability Start:</b> {r.availabilityStartDate}</li>
                  <li><b>Availability End:</b> {r.availabilityEndDate}</li>
                  <li><b>Self Driving:</b> {r.selfDriving ? 'Yes' : 'No'}</li>
                  <li><b>With Driver:</b> {r.withDriver ? 'Yes' : 'No'}</li>
                  <li><b>Features:</b> {r.features?.join(', ')}</li>
                </ul>
                {r.images && r.images.length > 0 && (
                  <div className="mt-4">
                    <h4 className="font-semibold mb-2">Images:</h4>
                    <div className="flex gap-2 overflow-x-auto">
                      {r.images.map((img: string, idx: number) => (
                        <img key={idx} src={img} alt="Vehicle" className="w-32 h-24 object-cover rounded" />
                      ))}
                    </div>
                  </div>
                )}
                {r.vehicleDocuments && r.vehicleDocuments.length > 0 && (
                  <div className="mt-4">
                    <h4 className="font-semibold mb-2">Documents:</h4>
                    <ul>
                      {r.vehicleDocuments.map((doc: string, idx: number) => (
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

export default Rentals;
