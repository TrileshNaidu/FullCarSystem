// Mock data for testing without backend
export const mockUsers = [
  {
    id: '1',
    email: 'admin@carverse.com',
    username: 'admin',
    password: 'admin123',
    firstName: 'Admin',
    lastName: 'User',
    role: 'ADMIN',
    phone: '+1-555-0123'
  },
  {
    id: '2',
    email: 'user@carverse.com',
    username: 'johndoe',
    password: 'user123',
    firstName: 'John',
    lastName: 'Doe',
    role: 'USER',
    phone: '+1-555-0456'
  },
  {
    id: '3',
    email: 'jane@carverse.com',
    username: 'janesmith',
    password: 'jane123',
    firstName: 'Jane',
    lastName: 'Smith',
    role: 'USER',
    phone: '+1-555-0789'
  }
];

export const mockVehicles = [
  {
    id: '1',
    make: 'Toyota',
    model: 'Camry',
    year: 2022,
    mileage: 15000,
    price: 2350000,
    fuelType: 'GASOLINE',
    transmission: 'AUTOMATIC',
    bodyType: 'SEDAN',
    color: 'Silver',
    location: 'Mumbai, Maharashtra',
    description: 'Excellent condition, one owner, full service history.',
    status: 'AVAILABLE',
    images: [
      'https://images.pexels.com/photos/116675/pexels-photo-116675.jpeg?auto=compress&cs=tinysrgb&w=800',
      'https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    documents: [],
    owner: mockUsers[1],
    createdAt: '2024-01-15T10:00:00Z'
  },
  {
    id: '2',
    make: 'Honda',
    model: 'Civic',
    year: 2021,
    mileage: 22000,
    price: 2050000,
    fuelType: 'GASOLINE',
    transmission: 'MANUAL',
    bodyType: 'SEDAN',
    color: 'Blue',
    location: 'Delhi, NCR',
    description: 'Great fuel economy, well maintained, clean interior.',
    status: 'AVAILABLE',
    images: [
      'https://images.pexels.com/photos/1545743/pexels-photo-1545743.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    documents: [],
    owner: mockUsers[2],
    createdAt: '2024-01-10T14:30:00Z'
  },
  {
    id: '3',
    make: 'Tesla',
    model: 'Model 3',
    year: 2023,
    mileage: 8000,
    price: 3700000,
    fuelType: 'ELECTRIC',
    transmission: 'AUTOMATIC',
    bodyType: 'SEDAN',
    color: 'White',
    location: 'Bangalore, Karnataka',
    description: 'Latest model with autopilot, supercharger included.',
    status: 'AVAILABLE',
    images: [
      'https://images.pexels.com/photos/1592384/pexels-photo-1592384.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    documents: [],
    owner: mockUsers[1],
    createdAt: '2024-01-20T09:15:00Z'
  }
];

export const mockRentals = [
  {
    id: '1',
    make: 'BMW',
    model: 'X5',
    year: 2022,
    mileage: 12000,
    dailyRate: 7300,
    fuelType: 'GASOLINE',
    transmission: 'AUTOMATIC',
    bodyType: 'SUV',
    color: 'Black',
    location: 'Pune, Maharashtra',
    description: 'Luxury SUV perfect for family trips or business travel.',
    availability: 'Available weekdays',
    requirements: 'Valid driver license, 25+ years old, ₹41,000 deposit',
    status: 'APPROVED',
    images: [
      'https://images.pexels.com/photos/1719648/pexels-photo-1719648.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    documents: [],
    owner: mockUsers[2],
    createdAt: '2024-01-12T11:20:00Z'
  },
  {
    id: '2',
    make: 'Jeep',
    model: 'Wrangler',
    year: 2021,
    mileage: 18000,
    dailyRate: 6200,
    fuelType: 'GASOLINE',
    transmission: 'MANUAL',
    bodyType: 'SUV',
    color: 'Red',
    location: 'Goa',
    description: 'Perfect for off-road adventures and mountain trips.',
    availability: 'Weekends and holidays',
    requirements: 'Valid driver license, 21+ years old, ₹25,000 deposit',
    status: 'PENDING',
    images: [
      'https://images.pexels.com/photos/1335077/pexels-photo-1335077.jpeg?auto=compress&cs=tinysrgb&w=800'
    ],
    documents: [],
    owner: mockUsers[1],
    createdAt: '2024-01-18T16:45:00Z'
  }
];

export const mockReviews = [
  {
    id: '1',
    vehicleId: '1',
    rating: 5,
    comment: 'Excellent car! Very reliable and fuel efficient. The owner was very helpful and responsive.',
    author: mockUsers[2],
    createdAt: '2024-01-16T12:00:00Z'
  },
  {
    id: '2',
    vehicleId: '1',
    rating: 4,
    comment: 'Good condition vehicle, exactly as described. Smooth transaction.',
    author: {
      id: '4',
      firstName: 'Mike',
      lastName: 'Johnson'
    },
    createdAt: '2024-01-17T15:30:00Z'
  },
  {
    id: '3',
    vehicleId: '3',
    rating: 5,
    comment: 'Amazing electric car! Super quiet and fast. Great for city driving.',
    author: mockUsers[2],
    createdAt: '2024-01-21T10:15:00Z'
  }
];

export const mockBookings = [
  {
    id: '1',
    rental: mockRentals[0],
    renter: mockUsers[1],
    startDate: '2024-02-01',
    endDate: '2024-02-05',
    totalAmount: 29200, // 4 days * ₹7300
    status: 'CONFIRMED',
    createdAt: '2024-01-25T14:20:00Z'
  },
  {
    id: '2',
    rental: mockRentals[0],
    renter: mockUsers[2],
    startDate: '2024-02-10',
    endDate: '2024-02-12',
    totalAmount: 14600, // 2 days * ₹7300
    status: 'PENDING',
    createdAt: '2024-01-26T09:45:00Z'
  }
];

// Helper functions for mock data management
export const findUserByEmailOrUsername = (emailOrUsername: string) => {
  return mockUsers.find(user => user.email === emailOrUsername || user.username === emailOrUsername);
};

export const findUserById = (id: string) => {
  return mockUsers.find(user => user.id === id);
};

export const generateMockToken = (user: any) => {
  // Simple mock JWT token (in real app, this would be generated by backend)
  return `mock-jwt-token-${user.id}-${Date.now()}`;
};

export const validateMockToken = (token: string) => {
  // Extract user ID from mock token
  const parts = token.split('-');
  if (parts.length >= 4 && parts[0] === 'mock' && parts[1] === 'jwt' && parts[2] === 'token') {
    const userId = parts[3];
    return findUserById(userId);
  }
  return null;
};

// Local storage helpers
export const getStoredData = (key: string, defaultValue: any = []) => {
  try {
    const stored = localStorage.getItem(key);
    return stored ? JSON.parse(stored) : defaultValue;
  } catch {
    return defaultValue;
  }
};

export const setStoredData = (key: string, data: any) => {
  localStorage.setItem(key, JSON.stringify(data));
};

// Initialize mock data in localStorage if not exists
export const initializeMockData = () => {
  if (!localStorage.getItem('mockVehicles')) {
    setStoredData('mockVehicles', mockVehicles);
  }
  if (!localStorage.getItem('mockRentals')) {
    setStoredData('mockRentals', mockRentals);
  }
  if (!localStorage.getItem('mockReviews')) {
    setStoredData('mockReviews', mockReviews);
  }
  if (!localStorage.getItem('mockBookings')) {
    setStoredData('mockBookings', mockBookings);
  }
};