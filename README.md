# Hotel Management System API Documentation

## Overview

This is a RESTful API for a Hotel Management System built with Spring Boot. The system manages rooms, users, bookings, and invoices.

## Base URL

```
http://localhost:9000
```

## API Endpoints

### 1. User Management

#### Create User

```http
POST /users/
```

Request Body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Pass123!",
  "phoneNumber": "+201012345678",
  "address": "123 Main St, Cairo",
  "userRole": "USER"
}
```

- `firstName`: 2-50 characters, letters only
- `lastName`: 2-50 characters, letters only
- `email`: Valid email format
- `password`: Minimum 8 characters
- `phoneNumber`: Egyptian format starting with +201
- `address`: 10-255 characters
- `userRole`: ADMIN, USER, or STAFF

#### Get All Users

```http
GET /users/
```

#### Get User by ID

```http
GET /users/{id}
```

#### Update User

```http
PUT /users/{id}
```

#### Delete User

```http
DELETE /users/{id}
```

#### Get Users by Role

```http
GET /users/role/{role}
```

### 2. Room Management

#### Create Room

```http
POST /rooms/
```

Request Body:

```json
{
  "roomNumber": "A101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "pricePerNight": 250.0,
  "roomStatus": "AVAILABLE",
  "description": "Comfortable double room with city view",
  "smokingAllowed": "NO",
  "floorNumber": 1
}
```

- `roomNumber`: 1 uppercase letter followed by 3 digits
- `roomType`: SINGLE, DOUBLE, SUITE, or DELUXE
- `capacity`: 1-6 persons
- `pricePerNight`: 100-10000
- `roomStatus`: AVAILABLE, OCCUPIED, MAINTENANCE, or RESERVED
- `smokingAllowed`: YES or NO
- `floorNumber`: 1-20

#### Get All Rooms

```http
GET /rooms/
```

#### Get Room by ID

```http
GET /rooms/{id}
```

#### Update Room

```http
PUT /rooms/{id}
```

#### Delete Room

```http
DELETE /rooms/{id}
```

#### Search Rooms

```http
GET /rooms/search
```

Query Parameters:

- `minPrice`: Minimum price per night
- `maxPrice`: Maximum price per night
- `minCapacity`: Minimum room capacity
- `roomType`: Type of room
- `smokingAllowed`: YES/NO
- `floorNumber`: Floor number

#### Get Rooms by Type

```http
GET /rooms/type/{roomType}
```

#### Get Rooms by Status

```http
GET /rooms/status/{status}
```

#### Get Available Rooms

```http
GET /rooms/available
```

### 3. Booking Management

#### Create Booking

```http
POST /bookings/
```

Request Body:

```json
{
  "bookingNumber": "BK001",
  "checkInDate": "2024-03-20",
  "checkOutDate": "2024-03-25",
  "room": {
    "id": 1
  },
  "user": {
    "id": 1
  }
}
```

- `bookingNumber`: Unique booking reference number
- `checkInDate`: Check-in date (format: yyyy-MM-dd)
- `checkOutDate`: Check-out date (format: yyyy-MM-dd)
- `room`: Room object with room ID
- `user`: User object with user ID

#### Get All Bookings

```http
GET /bookings/
```

#### Get Booking by ID

```http
GET /bookings/{id}
```

#### Update Booking

```http
PUT /bookings/{id}
```

#### Delete Booking

```http
DELETE /bookings/{id}
```

#### Get Bookings by User

```http
GET /bookings/user/{userId}
```

#### Get Bookings by Room

```http
GET /bookings/room/{roomId}
```

#### Get Bookings by Date Range

```http
GET /bookings/date-range?startDate={startDate}&endDate={endDate}
```

Date format: yyyy-MM-dd

### 4. Invoice Management

#### Create Invoice

```http
POST /invoices/
```

Request Body:

```json
{
  "booking": { "id": 1 },
  "user": { "id": 1 },
  "totalAmount": 500.0,
  "paymentMethod": "CREDIT_CARD",
  "taxAmount": 50.0,
  "discountAmount": 25.0,
  "notes": "Regular booking payment"
}
```

- `paymentStatus`: PENDING, PAID, CANCELLED, or REFUNDED
- `paymentMethod`: CASH, CREDIT_CARD, DEBIT_CARD, or BANK_TRANSFER
- `notes`: Maximum 500 characters

#### Get All Invoices

```http
GET /invoices/
```

#### Get Invoice by ID

```http
GET /invoices/{id}
```

#### Update Invoice

```http
PUT /invoices/{id}
```

#### Delete Invoice

```http
DELETE /invoices/{id}
```

#### Get Invoices by User

```http
GET /invoices/user/{userId}
```

#### Get Invoices by Booking

```http
GET /invoices/booking/{bookingId}
```

#### Get Invoices by Status

```http
GET /invoices/status/{status}
```

#### Get Invoices by Payment Method

```http
GET /invoices/payment-method/{method}
```

#### Get Invoices by Date Range

```http
GET /invoices/date-range?startDate={startDate}&endDate={endDate}
```

Date format: yyyy-MM-dd'T'HH:mm:ss

#### Get Invoices by Amount Range

```http
GET /invoices/amount-range?minAmount={minAmount}&maxAmount={maxAmount}
```

## Response Formats

### Success Response

```json
{
    "data": {
        // Response data
    },
    "status": 200/201
}
```

### Error Response

```json
{
    "error": "Error message",
    "status": 400/404/500
}
```

## Common HTTP Status Codes

- 200: Success
- 201: Created
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

## Data Validation Rules

### User Validation

#### Required Fields

- `firstName`: Required
  - Length: 2-50 characters
  - Pattern: Letters only (a-zA-Z)
  - No special characters or numbers
- `lastName`: Required
  - Length: 2-50 characters
  - Pattern: Letters only (a-zA-Z)
  - No special characters or numbers
- `email`: Required
  - Valid email format (example@domain.com)
  - Must be unique in the system
  - Maximum length: 255 characters
- `password`: Required
  - Minimum length: 8 characters
  - Maximum length: 50 characters
- `phoneNumber`: Required
  - Egyptian format starting with +201
  - Length: 13 characters
  - Pattern: +201[0-9]{9}
- `address`: Required
  - Length: 10-255 characters
- `userRole`: Required
  - Valid values: ADMIN, USER, or STAFF

#### Optional Fields

- None (all fields are required)

### Room Validation

#### Required Fields

- `roomNumber`: Required
  - Pattern: 1 uppercase letter followed by 3 digits (e.g., A101)
  - Must be unique in the system
- `roomType`: Required
  - Valid values: SINGLE, DOUBLE, SUITE, or DELUXE
- `capacity`: Required
  - Range: 1-6 persons
  - Must be a positive integer
- `pricePerNight`: Required
  - Range: 100-10000
  - Must be a positive number with up to 2 decimal places
- `roomStatus`: Required
  - Valid values: AVAILABLE, OCCUPIED, MAINTENANCE, or RESERVED
- `floorNumber`: Required
  - Range: 1-20
  - Must be a positive integer

#### Optional Fields

- `description`: Optional
  - Maximum length: 500 characters
- `smokingAllowed`: Optional
  - Valid values: YES or NO
  - Default: NO

### Booking Validation

#### Required Fields

- `bookingNumber`: Required
  - Must be unique in the system
  - Pattern: BK followed by 3 digits (e.g., BK001)
- `checkInDate`: Required
  - Format: yyyy-MM-dd
  - Must not be in the past
  - Must be before checkOutDate
- `checkOutDate`: Required
  - Format: yyyy-MM-dd
  - Must be after checkInDate
  - Maximum stay duration: 30 days
- `room`: Required
  - Must reference a valid room ID
  - Room must be AVAILABLE for the booking period
- `user`: Required
  - Must reference a valid user ID

#### Business Rules

- Cannot book an already occupied room for the same dates
- Maximum booking duration is 30 days
- Check-in time: After 14:00 (2 PM)
- Check-out time: Before 12:00 (12 PM)
- Room status will automatically change to OCCUPIED during the booking period
- Cannot modify booking dates if check-in date has passed
- Cannot delete booking if it has associated invoices

### Invoice Validation

#### Required Fields

- `booking`: Required
  - Must reference a valid booking ID
  - Cannot create multiple invoices for the same booking
- `user`: Required
  - Must reference a valid user ID
  - Must match the booking's user ID
- `totalAmount`: Required
  - Must be positive
  - Must be greater than 0
  - Maximum 2 decimal places
  - Must equal (base amount + taxAmount - discountAmount)
- `paymentStatus`: Required
  - Valid values: PENDING, PAID, CANCELLED, or REFUNDED
  - Default: PENDING
- `paymentMethod`: Required when status is PAID
  - Valid values: CASH, CREDIT_CARD, DEBIT_CARD, or BANK_TRANSFER

#### Optional Fields

- `taxAmount`: Optional
  - Must be positive or zero
  - Maximum 2 decimal places
  - Default: 0.0
- `discountAmount`: Optional
  - Must be positive or zero
  - Maximum 2 decimal places
  - Cannot exceed totalAmount
  - Default: 0.0
- `notes`: Optional
  - Maximum length: 500 characters
- `paymentDate`: Optional
  - Automatically set when payment status changes to PAID
  - Format: yyyy-MM-dd'T'HH:mm:ss

#### Business Rules

- Cannot modify invoice once payment status is PAID
- Cannot delete invoice once payment status is PAID
- Discount amount cannot exceed total amount
- Payment date is automatically set when status changes to PAID
- Refund is only possible if payment status is PAID
- Cannot change status from REFUNDED to any other status
- Tax amount calculation: configurable percentage of base amount
- Invoice number is automatically generated in sequence

### Common Validation Rules

#### Date and Time

- All dates must be in specified format
- Future dates cannot be more than 1 year in advance
- Past dates are not allowed for new bookings
- Time zones are handled in UTC

#### Numeric Values

- All monetary amounts must have maximum 2 decimal places
- All numeric IDs must be positive integers
- All percentages must be between 0 and 100

#### Text Fields

- No HTML tags allowed in text fields
- Trimmed of leading and trailing spaces
- Maximum lengths enforced for all text fields
- Special characters are escaped

#### Error Handling

- Validation errors return 400 Bad Request
- Detailed error messages for each validation failure
- Multiple validation errors can be returned at once
- Field names included in error messages

## Error Handling

All endpoints include proper error handling and return appropriate HTTP status codes and error messages.

## Testing

You can test the APIs using tools like Postman or curl. Example curl commands:

```bash
# Create a new user
curl -X POST http://localhost:9000/users/ \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com","password":"Pass123!"}'

# Create a new booking
curl -X POST http://localhost:9000/bookings/ \
  -H "Content-Type: application/json" \
  -d '{"bookingNumber":"BK001","checkInDate":"2024-03-20","checkOutDate":"2024-03-25","room":{"id":1},"user":{"id":1}}'

# Get all rooms
curl http://localhost:9000/rooms/

# Create an invoice
curl -X POST http://localhost:9000/invoices/ \
  -H "Content-Type: application/json" \
  -d '{"booking":{"id":1},"user":{"id":1},"totalAmount":500.0}'
```
