# **Khaopiyoji - Tiffin Subscription Platform**

Welcome to **Khaopiyoji**, a platform that enables **customers** to subscribe to **vendors** offering tiffin services. This project integrates various technologies like **Spring Boot**, **Kafka**, **Redis**, **Swagger**, and **Docker**, delivering a seamless subscription experience for customers and vendors alike.

---

## **Table of Contents**

1. [Key Features](#key-features-)
2. [Technologies Used](#technologies-used)
3. [Setup Instructions](#setup-instructions)
4. [API Documentation](#api-documentation)
5. [Run with Docker](#run-with-docker)
6. [License](#license)

---

## **Key Features ⭐**

Khaopiyoji is a feature-rich platform designed for **tiffin subscription management**, with an intuitive interface for customers and vendors alike. Below are the detailed features implemented:

### **1. Customer Registration & Login**
- **Customer Registration**: Customers can register on the platform with their details, including name, email, and other relevant information.
- **Profile Picture Upload**: During registration, customers can upload their profile images.
- **JWT-based Authentication**: Secure login for customers using **JSON Web Tokens (JWT)** for authentication and authorization.

### **2. Vendor Registration & Login**
- **Vendor Registration**: Vendors can register on the platform by providing necessary details, such as name, business details, and email.
- **JWT-based Authentication for Vendors**: Vendors can log in securely using **JWT** authentication.
  
### **3. Vendor and Customer Dashboard**
- **Vendor Dashboard**: Vendors can view and manage customer subscriptions, check payment statuses, and update their profile.
- **Customer Dashboard**: Customers can view their subscription details, renew or cancel subscriptions, and manage their account settings.

### **4. Subscription Management**
- **Subscription Creation**: Customers can create subscriptions to vendors. Vendors are notified about new subscriptions.
- **Subscription Expiry Alerts**: Customers receive email alerts when their subscription is about to expire.
- **Subscription Renewal**: Customers can renew their subscriptions, and the platform updates the subscription status.
- **Subscription Deletion**: Subscriptions can be deleted, with both customers and vendors notified about the changes.

### **5. Email Notifications 📧**
- **Subscription Expiry Reminder**: Automated email notifications sent to customers when their subscription is nearing expiration.
- **Registration Confirmation**: Vendors and customers receive a welcome email upon successful registration.

### **6. Payment Gateway Integration 💳**
- **Razorpay Integration**: Integrated **Razorpay** to handle payments for subscriptions. The system processes payments securely and updates subscription statuses accordingly.
- **Payment Success & Failure Handling**: Real-time notification of successful or failed payment transactions.

### **7. Kafka Integration 🛠️**
- **Kafka Messaging**: Kafka is used for real-time communication between microservices. Asynchronous events such as subscription updates, payment status changes, and email notifications are processed via Kafka.
- **Message Queuing**: Kafka ensures high availability and fault tolerance in message delivery.

### **8. Redis Integration ⚡**
- **Data Caching**: Frequently accessed data such as vendor details, subscription statuses, and user profiles are cached in **Redis** for faster data retrieval.
- **Cache Expiry**: Redis cache uses TTL (Time To Live) to ensure that the data remains fresh and avoids displaying outdated information.

### **9. Real-Time Subscription Updates**
- **Automatic Updates**: When a subscription is created, updated, or canceled, the system reflects the changes in real-time, both on the vendor’s and the customer’s side.
- **Vendor Subscriptions List**: Vendors can fetch a list of all their active subscriptions in real-time.

### **10. Security**
- **Spring Security**: The platform is secured using **Spring Security**, ensuring that only authorized users (customers and vendors) can access their respective dashboards.
- **Role-Based Access Control**: Different roles (e.g., **CUSTOMER** and **VENDOR**) are assigned to users to control access to certain parts of the application.
  
### **11. Swagger API Documentation 📖**
- **Interactive API Documentation**: All API endpoints are documented using **Swagger** for easy exploration and testing of the APIs.
- **API Versioning**: Provides versioned API documentation for better management and backward compatibility.
  
### **12. Dockerized Environment 🐋**
- **Dockerized App**: The entire application is containerized using **Docker**, making it easier to deploy and scale in any environment (local, cloud, or production).
- **Docker Compose**: A `docker-compose.yml` file is provided to simplify multi-container deployments, including services like the application server, database, and cache.

### **13. Scalability**
- **Kafka for Scalability**: Kafka enables the platform to handle large numbers of events asynchronously, making it highly scalable.
- **Redis for Performance**: Redis caching minimizes the number of database calls, boosting the performance of the platform.

### **14. Subscription Expiry Management**
- **Automated Expiry Check**: A scheduled task runs periodically to check for expiring subscriptions. Customers are notified of impending expiration, and expired subscriptions are deactivated.
- **Subscription Deletion**: After a set period, expired subscriptions are deleted automatically.

### **15. Vendor Subscription Overview**
- **Subscription Overview**: Vendors can view all the customers subscribed to their services along with subscription status, start date, and end date.
- **Vendor's Ability to Track Subscriptions**: Vendors can track, update, and delete subscriptions that belong to their service, allowing for better management.

### **16. User Experience Enhancements**
- **Profile Management**: Customers and vendors can manage their profile details, including updating contact information and changing passwords.
- **Mobile Responsiveness**: The application’s design is responsive, providing an optimal experience on both desktop and mobile devices.

### **17. Scheduled Jobs**
- **Automated Subscription Expiry and Renewal**: Scheduled tasks are set up to periodically check subscription expiry, send reminders, and automatically deactivate or renew subscriptions.

---

## **Technologies Used 🛠️**

The project uses a variety of modern technologies to ensure scalability, security, and efficiency. Below is a list of the key technologies:

| **Technology**          | **Description**                                            |
|-------------------------|------------------------------------------------------------|
| **Spring Boot**          | Backend framework for building RESTful web services       |
| **Kafka**                | Real-time event streaming and messaging service            |
| **Redis**                | In-memory data store for caching frequently accessed data |
| **Swagger**              | API documentation for easy exploration of endpoints        |
| **Docker**               | Containerization of the application for simplified deployment |
| **JWT (JSON Web Tokens)**| Secure authentication mechanism for customers and vendors |
| **Razorpay**             | Payment gateway integration for subscription payments      |
| **Spring Security**      | Secure user authentication and role-based access control  |
| **PostgreSQL**           | Relational database to store customer and vendor data      |

---

## **Setup Instructions 🔧**

### **1. Clone the repository**
```bash
git clone https://github.com/Vikrant25122004/Khaopiyoji.git
