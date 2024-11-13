# Simple Webhook API 
An Order Processing Service using WebClient.
This versatile webhook processing API can be used not only for order processing but also for various other services.

# Project Summary 
An Asynchronous Webhook System Using Webhooks after a Request.
The API supports real-time, asynchronous communication between systems, ensuring efficient data flow and integration.
# Key Features
- Automated Order Processin
  
  A (e-commerce platform) → B (Order Processing Service, the API we developed) → C (Shipping System / Customer Notification System)
  
      a) A → B : A sends a standard API request to B.
  
      b) B → C : B processes the request and asynchronously sends a webhook to C using WebClient.
  
      c) B → A : B communicates with C via the webhook URL, and upon successfully sending the webhook, B sends a "200" success response to A.
  
## Various Use Cases:
- Customer Marketing Notifications

  A (Customer Management System) → B → C (Marketing System) to send personalized advertisements.
- Real-Time Inventory Management

  A (Inventory Management System) → B → C (Supply Chain Management System) to update inventory information.

This API automates real-time data synchronization between systems, with B acting as the central hub for processing and relaying data, making it an efficient solution for various services.

# Development Environment
- JAVA: 8
- Framework : Springboot(3.x)
- Database : mysql








