<div align="center">

# 💸 UPI Parser

### Full-Stack UPI SMS Transaction Intelligence System

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react)
![Vite](https://img.shields.io/badge/Vite-5.x-646CFF?style=for-the-badge&logo=vite)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

 Paste a UPI SMS → Get structured transaction data, analytics, and insights — instantly.

[Features](#-key-features) • [Tech Stack](#-tech-stack) • [Project Structure](#-project-structure) • [API Docs](#-api-endpoints) • [Setup](#-local-setup)

</div>

---

## ✨ Key Features

| Feature | Description |
|--------|-------------|
| 📲 SMS Parsing | Extracts amount & merchant from raw UPI SMS using Regex |
| 🗄️ Persistence | Saves every transaction to database via Spring Data JPA |
| 📊 Analytics | Total spend, merchant frequency, average per transaction |
| 🌐 REST APIs | Clean, structured endpoints with global `ApiResponse<T>` wrapper |
| ⚠️ Error Handling | Global exception handler with meaningful error responses |
| 📝 SMS Logging | Raw SMS saved to flat file via `FileUtil` |
| 🖥️ React Dashboard | Live dashboard with Parse, Transactions and Analytics tabs |
| 🔗 Proxy Setup | Vite dev proxy — zero CORS issues in development |

---

## 🧠 Tech Stack

### Backend
| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Parsing Engine | Regex (Pattern / Matcher) |
| ORM | Spring Data JPA |
| Database | H2 (in-memory) |
| Response Format | Custom ApiResponse |
| Logging | SLF4J + Logback |
| Build Tool | Maven |

### Frontend
| Layer | Technology |
|-------|-----------|
| Library | React 18 |
| Build Tool | Vite 5 |
| Styling | Pure CSS (custom dark theme) |
| Fonts | Syne + DM Mono (Google Fonts) |
| HTTP | Fetch API |
| Dev Proxy | Vite proxy to localhost:8080 |

---

## 📂 Project Structure

```
upiparser/                          ← Monorepo root
│
├── upiparser/                      ← Spring Boot Backend
│   ├── src/main/java/com/example/upiparser/
│   │   ├── controller/
│   │   │   └── TransactionController.java
│   │   ├── service/
│   │   │   └── TransactionService.java
│   │   ├── parser/
│   │   │   └── SMSParser.java
│   │   ├── model/
│   │   │   └── Transaction.java
│   │   ├── dto/
│   │   │   └── TransactionDTO.java
│   │   ├── repository/
│   │   │   └── TransactionRepository.java
│   │   ├── config/
│   │   │   └── CorsConfig.java
│   │   ├── payload/
│   │   │   └── ApiResponse.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── InvalidSMSFormatException.java
│   │   ├── util/
│   │   │   └── FileUtil.java
│   │   └── UpiParserApplication.java
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
└── upi-frontend/                   ← React + Vite Frontend
    ├── src/
    │   ├── App.jsx
    │   ├── App.css
    │   ├── main.jsx
    │   └── index.css
    ├── index.html
    ├── vite.config.js
    └── package.json
```

---

## 🔥 API Endpoints

### POST /api/transactions/parse
Parse a raw UPI SMS and extract transaction details.

**Request Body:**
```json
{
  "smsText": "Debited ₹520 to Swiggy via Google Pay"
}
```

**Success Response:**
```json
{
  "success": true,
  "message": "Parsed successfully",
  "data": {
    "id": 1,
    "amount": 520.0,
    "merchant": "Swiggy",
    "smsText": "Debited ₹520 to Swiggy via Google Pay",
    "timestamp": 1712900000000
  }
}
```

**Failure Response:**
```json
{
  "success": false,
  "message": "Unable to parse SMS",
  "data": null
}
```

---

### GET /api/transactions
Returns all saved transactions.

---

### GET /api/transactions/total
Returns total amount spent across all transactions.

```json
1820.0
```

---

### GET /api/transactions/count
Returns total number of transactions parsed.

```json
{
  "success": true,
  "message": "Total transactions count fetched",
  "data": 5
}
```

---

### GET /api/transactions/merchant-count
Returns a frequency map of merchants.

```json
{
  "Swiggy": 3,
  "Amazon": 1,
  "Zomato": 2
}
```

---

### GET /api/transactions/merchant/{name}
Returns all transactions for a specific merchant.

```
GET /api/transactions/merchant/Swiggy
```

---

## 🛠️ Local Setup

### Prerequisites
- Java 21+
- Node.js 16+
- npm
- Maven (or use the included `mvnw` wrapper)

---

### Step 1 — Clone the repo

```bash
git clone https://github.com/vedansh0410/upiparser.git
cd upiparser
```

---

### Step 2 — Start the Backend

```bash
cd upiparser
```

**Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**Mac / Linux:**
```bash
./mvnw spring-boot:run
```

> Backend runs at: **http://localhost:8080**

---

### Step 3 — Start the Frontend

Open a **new terminal:**

```bash
cd upi-frontend
npm install
npm run dev
```

> Frontend runs at: **http://localhost:5173**

---

### Step 4 — Open the Dashboard

Visit **http://localhost:5173** in your browser.

---

## 🔗 How Frontend Connects to Backend

Vite's dev proxy forwards all API calls silently:

```
Browser  →  http://localhost:5173/api/transactions
                      ↓  Vite Proxy
Backend  →  http://localhost:8080/api/transactions
```

No CORS errors. No hardcoded backend URLs in React code.

---

## 📬 Test with Postman

| Field | Value |
|-------|-------|
| Method | POST |
| URL | http://localhost:8080/api/transactions/parse |
| Body type | raw → JSON |

```json
{
  "smsText": "Paid ₹1200 to Amazon via PhonePe"
}
```

### Supported SMS Formats

```
Debited ₹1600 to Swiggy via Google Pay
Paid ₹500 to Amazon via PhonePe
Sent ₹1200 to Rahul via BHIM UPI
₹299 debited to Netflix via ICICI UPI
```

---

## 🌱 Roadmap

- [x] Regex-based SMS parsing
- [x] REST API with global response wrapper
- [x] Spring Data JPA persistence
- [x] React dashboard with analytics
- [x] Raw SMS file logging
- [ ] MySQL / PostgreSQL integration
- [ ] JWT-based user authentication
- [ ] UPI app detection (GPay / Paytm / PhonePe)
- [ ] Monthly and weekly spending summaries
- [ ] Duplicate SMS detection
- [ ] Export transactions as CSV / PDF
- [ ] Mobile app (React Native)

---

## 👤 Author

<div align="center">

**Vedansh Singhal**

Backend Developer — Java · Spring Boot · React

[![GitHub](https://img.shields.io/badge/GitHub-vedansh0410-181717?style=for-the-badge&logo=github)](https://github.com/vedansh0410)

</div>

---

<div align="center">

⭐ **If this project helped you, give it a star — it means a lot!**

</div>
