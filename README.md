# Agency Booking REST API

This is a hotel reservation booking system that allows users to manage hotel bookings seamlessly.

## Features

- **Guest Registration:** Register new hotel guests in the system.
- **Hotel Listings:** Retrieve a paginated list of available hotels. For demo purposes, these hotels are prepopulated via a post-construct method.
- **Hotel Registration:** Register new hotels in the system.
- **Hotel Reservations:** Reserve a hotel for registered guests.
- **Reservation Management:** Update reservation dates or delete reservations for guests.
- **User Reservations:** Retrieve all reservations made by a specific guest.
- **Hotel Reservations:** Get a paginated list of reservations for a specific hotel.

## How to Run the Application

This REST application is built using Spring 3 and requires Java 17 to be installed on your machine. Follow the steps below to get it running:

1. **Clone the Repository**  
   Clone the repository to your local machine.

2. **Navigate to Project Root Dir.**  
   On your terminal, navigate to the root directory of your project.

3. **Build the Project**
   First, ensure that the project has been compiled and the JAR file has been generated in the `target` directory.

   Run the command
   `mvn install`

   If you do not have mvn in your environment, then execute the following command to install it on your MacOS.
   `brew install maven`

   Then run the following command to build the docker image:
   `docker build -t agencybooking .`

4.  **Run the Project**
    To run the application, execute:
    `docker run -p 8080:8080 agencybooking      `


## Main Endpoints

The reservation module is the central integration point of the system. Below are the key endpoints related to reservations. However, feel free to explore the hotel and guest endpoints as well to gain a more comprehensive understanding of the system's capabilities.

# API Endpoints

**URL**: DELETE `http://localhost:8080/reservation/{reservationId}/delete`
##### 200 Response
status code 200 for successful deletion.

**URL**: PATCH `/reservation/{reservationId}/update?checkInDate={DateTimeFormat}&checkOutDate={DateTimeFormat}`

##### 200 Response
```json
	"response": [
{
"guest": {
"firstName": "Nqobani",
"lastName": "Nhlengethwa",
"email": "nqobani2730@gmail.com",
"phone": "+27623791885"
},
"hotel": {
"name": "The Grand Hotel",
"address": "123 Main St, Springfield",
"phone": "+27611111111",
"email": "info@grandhotel.com"
},
"checkIn": "2024-08-25T14:30:00",
"checkOut": "2024-08-25T19:30:00"
}
],
"error": null
```

**URL**: POST `http://localhost:8080/reservation/add`

**Request Data**
```json
{
   "hotel_id" : "1",
   "guest_email" : "nqobani2730gmail.com",
   "check_in_date_time" : "2024-08-25T14:30:00",
   "check_out_date_time" : "2024-08-25T19:30:00"
}
```

##### 200 Response
```json
{
   "response": {
      "guest": {
         "firstName": "Nqobani",
         "lastName": "Nhlengethwa",
         "email": "nqobani2730@gmail.com",
         "phone": "+27623791885"
      },
      "hotel": {
         "name": "The Grand Hotel",
         "address": "123 Main St, Springfield",
         "phone": "+27611111111",
         "email": "info@grandhotel.com"
      },
      "checkIn": "2024-08-25T14:30:00",
      "checkOut": "2024-08-25T19:30:00"
   },
   "error": null
}
```

**URL**: GET `/reservation/1/hotel/all?page=0&size=2`

##### 200 Response
```json
{
   "response": [
      {
         "guest": {
            "firstName": "Nqobani",
            "lastName": "Nhlengethwa",
            "email": "nqobani2730@gmail.com",
            "phone": "+27623791885"
         },
         "hotel": {
            "name": "The Grand Hotel",
            "address": "123 Main St, Springfield",
            "phone": "+27611111111",
            "email": "info@grandhotel.com"
         },
         "checkIn": "2024-08-25T14:30:00",
         "checkOut": "2024-08-25T19:30:00"
      },
      {
         "guest": {
            "firstName": "Nqobani",
            "lastName": "Nhlengethwa",
            "email": "nqobani2730@gmail.com",
            "phone": "+27623791885"
         },
         "hotel": {
            "name": "The Grand Hotel",
            "address": "123 Main St, Springfield",
            "phone": "+27611111111",
            "email": "info@grandhotel.com"
         },
         "checkIn": "2024-08-25T14:30:00",
         "checkOut": "2024-08-25T19:30:00"
      }
   ],
   "error": null
}
```

# Nice to Have
# Future Improvements

Due to time constraints, several features and improvements were not implemented. However, the application would significantly benefit from the following enhancements:

1. **Enhanced Unit Testing**  
   Increasing the unit test coverage to ensure more comprehensive testing across the application.

2. **Reservation Conflict Prevention**  
   Implementing functionality to prevent users from reserving dates and times that are already booked.

3. **Guest and Room Management**  
   Consider the number of guests and the various room sizes available in a hotel, instead of treating a hotel as a single room. In reality, hotels can have multiple rooms with different capacities.

# Thank you for your consideration.
