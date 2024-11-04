# Cab Booking System

An application designed for cab booking with features to automate user and application data using databases. The primary goal is to create a fast and reliable system for providing and managing real-time data related to cabs, drivers, bookings, etc. The application will handle extensive data manipulation requests, ensuring proper constraints and minimal redundancy, with data organization closely related to real-world entities.

## Technical Requirements

The application will be built using:

- **Frontend:** HTML, CSS, JavaScript, Java
- **Backend Database:** MySQL

### Data to be Stored

- **User:** Name, ID, Home Address, Phone Number, Subscription, etc.
- **Driver:** Joining Date, Name, Phone Number, Driving License ID, Car Type, RC Number, Driver ID.
- **Trip:** Source, Destination, Customer ID, Trip ID, Driver ID, Payment ID.
- **Payment:** ID, Transaction Type, Amount.
- **Vehicle:** Type, RC, Mileage, Condition, Estimated per km Price.

### Constraints

- User data, such as name and phone number, will have restricted access to the driver.
- Sensitive data cannot be accessed by the driver.
- Username and ID are not changeable, and the address is a composite type.
- Phone number is multivalued.

## Functional Requirements

### For User

- Creating Account (Setting up Profile Data)
- Entering details for source and destination
- Selecting among different vehicle types
- Google Maps functionality for navigation help
- Making Payments
- Reviews, Help & Issues
- Account history (ride history, payment history, etc.)

### For Driver

- Creating Account & Registering
- Accepting or Declining Booking
- Wallet for Receiving Payments
- Account history (ride history, payment history, etc.)

### Other Functionalities

- Entire data will be saved in the backend.
- Fast & accurate search results and fare prices maintenance
- Coupons & rewards
- Customer Subscription (Professional drivers, low fare prices & other benefits)
- InterCity or IntraCity Booking

