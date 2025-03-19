# Microservices Project

## Microservices Included
The project consists of the following services:
- **Authorization Service** – Handles authentication and user registration.
- **Client Service** – Provides user-related functionalities.
- **Service Registry** – Manages service discovery and registration (Eureka or similar).

## Technologies Used
- **Spring Boot** – Framework for building microservices.
- **Spring Boot Authorization Server** – Implements OAuth2 authentication.
- **Docker** – Containerization for easy deployment.

## Installation and Setup
To set up and run the project locally using **Docker Compose**, follow these steps:

1. Clone the repository:
   ```sh
   git clone https://github.com/VladyslavSydiuk/Microservices.git
   cd Microservices
   ```
2. Run the services using Docker Compose:
   ```sh
   docker-compose up --build
   ```
3. The services should now be accessible.

---

## API Documentation

### 1. Authorization Service
Handles user authentication and registration.

#### **Register a new user**
- **Endpoint:** `POST /auth/register`
- **Request Body:**
  ```json
  {
    "username": "exampleUser",
    "password": "securePassword"
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "username": "exampleUser"
  }
  ```
- **Possible Errors:**
  - `400 Bad Request` if username or password is missing.

---

### 2. Client Service
Provides user details based on OAuth2 authentication.

#### **Get current authenticated user**
- **Endpoint:** `GET /client/me`
- **Headers:**  
  `Authorization: Bearer <access_token>`
- **Response (Success, authenticated user):**
  ```json
  {
    "sub": "user123",
    "client-id": "my-client-id",
    "issuer": "https://auth-server.com",
    "roles": ["ROLE_USER"]
  }
  ```
- **Response (Unauthorized request):**
  ```json
  {
    "error": "User is not authenticated"
  }
  ```
- **Possible Errors:**
  - `401 Unauthorized` if the token is missing or invalid.

---

### 3. Service Registry
Used for microservice discovery (e.g., Eureka). No public API endpoints.

---

Let me know if you'd like any modifications or additions! 🚀
```  

This includes request and response examples for both the **Authorization Service** and **Client Service**. Let me know if you’d like to add error handling details or any additional endpoints! 😊
