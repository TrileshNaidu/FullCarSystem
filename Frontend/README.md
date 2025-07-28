# CarVerse Frontend

A comprehensive React.js application for buying, selling, and renting cars.

## Features

- **Authentication System**: Login/Register with JWT tokens
- **Role-based Access**: USER and ADMIN roles with different permissions
- **Vehicle Marketplace**: Browse, search, and filter cars for sale
- **Rental System**: List cars for rent with admin approval workflow
- **File Uploads**: Support for images and documents
- **Reviews & Ratings**: 5-star rating system with comments
- **Admin Panel**: Manage users, approve rentals, view bookings
- **Responsive Design**: Works on all devices

## Demo Accounts

For testing purposes, you can use these pre-configured accounts:

### Admin Account
- **Email**: `admin@carverse.com`
- **Password**: `admin123`
- **Role**: ADMIN (can access admin panel, approve rentals)

### User Accounts
- **Email**: `user@carverse.com`
- **Password**: `user123`
- **Role**: USER

- **Email**: `jane@carverse.com`
- **Password**: `jane123`
- **Role**: USER

## Getting Started

1. **Install Dependencies**
   ```bash
   npm install
   ```

2. **Start Development Server**
   ```bash
   npm run dev
   ```

3. **Login with Demo Account**
   - Use any of the demo accounts above
   - Or register a new account (will be created as USER role)

## Mock Data

The application uses mock data stored in localStorage to simulate a backend:

- **Users**: Pre-configured admin and user accounts
- **Vehicles**: Sample cars for sale with images
- **Rentals**: Sample rental vehicles (some pending approval)
- **Reviews**: Sample reviews and ratings
- **Bookings**: Sample rental bookings

## Key Features Demonstrated

### Authentication
- JWT token simulation
- Role-based routing
- Protected routes for authenticated users
- Admin-only routes

### Vehicle Management
- Create, read, update, delete vehicles
- Image and document uploads
- Search and filtering
- Detailed vehicle pages

### Rental System
- Create rental listings (requires admin approval)
- Browse approved rentals
- Book rentals with date selection
- Admin approval workflow

### Admin Panel
- User management
- Rental approval/rejection
- Booking management
- System overview

### Reviews & Ratings
- 5-star rating system
- Comment system
- User-generated reviews

## Technology Stack

- **React 18** with TypeScript
- **React Router v6** for navigation
- **Tailwind CSS** for styling
- **Local Storage** for data persistence
- **Mock Services** simulating REST API

## File Structure

```
src/
├── components/          # Reusable components
├── contexts/           # React contexts (Auth)
├── pages/              # Page components
├── services/           # API services and mock data
└── App.tsx            # Main application component
```

## Production Deployment

To deploy this application:

1. **Build for Production**
   ```bash
   npm run build
   ```

2. **Replace Mock Services**
   - Update `src/services/authService.ts`
   - Update `src/services/apiService.ts`
   - Point to your actual backend API endpoints

3. **Environment Variables**
   - Add API base URL
   - Add any required configuration

## Backend Integration

When connecting to a real backend:

1. Replace mock services with actual HTTP calls
2. Update authentication to use real JWT tokens
3. Implement proper file upload endpoints
4. Add error handling for network failures
5. Add loading states for better UX

The current mock implementation provides the exact same interface as the real backend would, making the transition seamless.