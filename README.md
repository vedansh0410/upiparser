# 🚀 UPI SMS Parsing System 

A clean and upgraded **Spring Boot backend** that extracts UPI transaction details from raw SMS using Regex parsing, logs raw messages into files, stores transactions in memory, and provides multiple REST APIs for analytics.

---

## 🔥 Features

### ✔ SMS Parsing Engine  
Parses:
- Amount  
- Merchant  
- Raw SMS text  
- Timestamp (System.currentTimeMillis())

### ✔ Raw SMS Logging (NEW)  
Every incoming SMS is saved inside:

src/main/resources/static/logs/raw_sms_log.txt

Using:

util/FileUtil.java


### ✔ Upgraded Transaction Model  
Now contains:

| Field | Description |
|------|-------------|
| amount | Parsed ₹ amount |
| merchant | Extracted merchant |
| smsText | Raw full SMS |
| timestamp | Time of parsing |

### ✔ Enhanced Service Layer  
TransactionService now:
- Logs raw SMS  
- Calls parser  
- Stores fully-built Transaction objects  
- Provides analytics endpoints  

### ✔ Updated Parser  
Regex unchanged, but parser now returns:

new Transaction(amount, merchant, rawSms, timestamp)


### ✔ Better Logging Using SLF4J  
- `info()` → important events  
- `debug()` → regex + parsing internals  
- `warn()` → when no match found  

---

## 📂 Project Structure

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

├── repository

│    └── TransactionRepository.java

│

├── util

│    └── FileUtil.java

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

src/main/resources

│

├── application.properties

│

└── static

└── logs

└── raw_sms_log.txt ← NEW (SMS log file)

## 🔥 API Endpoints

### 1️⃣ Parse SMS

* **Method:** `POST`
* **Path:** `/api/transactions/parse`
* **Request Body:**
    ```json
    {
      "smsText": "Debited ₹1600 to Meemansa via GooglePay"
    }
    ```
* **Response:**
    ```json
    {
  "success": true,
  "message": "Parsed successfully",
  "data": {
    "id": 12,
    "amount": 1600,
    "merchant": "Meemansa",
    "smsText": "Debited ₹1600 to Meemansa via GooglePay",
    "timestamp": 1735752942000
  }
}
    ```

### 2️⃣ Get All Transactions

* **Method:** `GET`
* **Path:** `/api/transactions`

### 3️⃣ Get Total Amount Spent

* **Method:** `GET`
* **Path:** `/api/transactions/total`

### 4️⃣ Get Merchant-wise Count

* **Method:** `GET`
* **Path:** `/api/transactions/merchant-count`

## 🛠️ Local Setup

1.  **Clone Repository**
    ```bash
    git clone [https://github.com/your-username/upi-sms-parser.git](https://github.com/your-username/upi-sms-parser.git)
    cd upi-sms-parser
    ```
2.  **Run the Project**
    ```bash
    mvn spring-boot:run
    ```
    > Project runs at: `http://localhost:8080`

### 📬 Test Using Postman

* **Request:** `POST` → `/api/transactions/parse`
* **Body (JSON):**
    ```json
    {
      "smsText": "Debited ₹520 to Flipkart"
    }
    ```

## 🌱 Future Enhancements

* 📌 Save transactions to **MySQL** (Completed)
* 📌 Add timestamp, transaction (Completed)
* 📌 Detect UPI apps (GPay/Paytm/PhonePe)
* 📌 Monthly analytics
* 📌 Daily/Weekly spending summary
* 📌 User authentication (JWT)
* 📌 Dashboard metrics
* 📌 Export CSV/PDF reports
* 📌 Duplicate SMS detection

## 👤 Author

**Vedansh Singhal**

Backend Developer (Java | Spring Boot)

*Building financial automation tools & UPI parsing systems.*

---
⭐ **Support the Project**
If this project helped you, please ⭐ star the repo!
