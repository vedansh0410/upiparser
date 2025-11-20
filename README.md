📌 UPI SMS Parsing System

A lightweight Spring Boot backend that extracts UPI transaction details from SMS text using Regex parsing and provides structured REST APIs for further analysis.

🚀 Features

✅ Extract amount from SMS

✅ Extract merchant name

✅ Completely backend-driven UPI SMS parsing

✅ Global API Response format

✅ Global Exception Handler

✅ Endpoints for:

● Parsing SMS

● Fetching all transactions

● Total expenditure

● Merchant-wise count

🧠 Tech Stack

| Layer           | Technology                    |
| --------------- | ----------------------------- |
| Backend         | Spring Boot (Web)             |
| Language        | Java 21                       |
| Parsing Engine  | Regex Pattern + Matcher       |
| Response Format | Custom `ApiResponse<T>`       |
| Storage         | In-Memory (List<Transaction>) |
| Tools           | Postman / cURL                |

📂 Project Structure

src/main/java/com/example/upiparser

│

├── controller

│   └── TransactionController.java

│

├── service

│   └── TransactionService.java

│

├── parser

│   └── SMSParser.java

│

├── model

│   └── Transaction.java

│

├── dto

│   └── TransactionDTO.java

│

├── exception

│   ├── GlobalExceptionHandler.java

│   └── InvalidSMSFormatException.java

│

├── payload

│   └── ApiResponse.java

│

└── UpiParserApplication.java

🔥 API Endpoints

1️⃣ Parse SMS

POST /api/transactions/parse

Request Body:

{
  "smsText": "Paid ₹250 to Amazon"
}

Response:

{
  "success": true,
  "message": "Parsed successfully",
  "data": {
    "amount": 250,
    "merchant": "Amazon"
  }
}

2️⃣ Get All Transactions

GET /api/transactions

3️⃣ Get Total Amount Spent

GET /api/transactions/total

4️⃣ Get Merchant-wise Count

GET /api/transactions/merchant-count

🛠️ Local Setup

🔷 1. Clone Repository

git clone https://github.com/your-username/upi-sms-parser.git
cd upi-sms-parser

🔷 2. Run the Project

mvn spring-boot:run

Project runs at:

http://localhost:8080

📬 Test Using Postman

POST → /api/transactions/parse

Body (JSON):

{
  "smsText": "Debited ₹520 to Flipkart"
}

🌱 Future Enhancements

📌 Save transactions to MySQL

📌 Add timestamp, transaction ID

📌 Detect UPI apps (GPay/Paytm/PhonePe)

📌 Monthly analytics

📌 Daily/Weekly spending summary

📌 User authentication (JWT)

📌 Dashboard metrics

📌 Export CSV/PDF reports

📌 Duplicate SMS detection

👤 Author

Vedansh Singhal

Backend Developer (Java | Spring Boot)

Building financial automation tools & UPI parsing systems.

⭐ Support the Project

If this project helped you, please ⭐ star the repo!
