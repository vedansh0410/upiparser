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

[Features](#-key-features) • [Tech Stack](#-tech-stack) • [Project Structure](#-project-structure) • [API Docs](#-api-endpoints) • [Setup](#-local-setup) • [Frontend Guide](#-frontend-setup-guide) • [Roadmap](#-roadmap)

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
| Response Format | Custom `ApiResponse<T>` |
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
upiparser/                               ← Monorepo root
│
├── README.md
│
├── upiparser/                           ← Spring Boot Backend
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
└── upi-frontend/                        ← React + Vite Frontend
    ├── index.html
    ├── vite.config.js                   ← Proxy config (/api → localhost:8080)
    ├── package.json
    └── src/
        ├── main.jsx
        ├── index.css
        ├── App.jsx
        └── App.css
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

### Step 2 — Add CorsConfig to Spring Boot

Copy `CorsConfig.java` into your backend at:

```
upiparser/src/main/java/com/example/upiparser/config/CorsConfig.java
```

This allows the React frontend to call the Spring Boot API without CORS errors.

---

### Step 3 — Start the Backend

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

Verify it works:
```bash
curl http://localhost:8080/api/transactions
```

---

### Step 4 — Install Frontend Dependencies

```bash
cd upi-frontend
npm install
```

---

### Step 5 — Run the Frontend

```bash
npm run dev
```

> Frontend runs at: **http://localhost:5173**

Open **http://localhost:5173** in your browser.

---

## 🖥️ Frontend Setup Guide

### How the Connection Works

Vite's dev proxy (configured in `vite.config.js`) forwards all `/api` requests from React to Spring Boot:

```
Browser → http://localhost:5173/api/transactions
                    ↓ (Vite proxy)
Backend → http://localhost:8080/api/transactions
```

No CORS issues during development. No hardcoded backend URL in React code.

---

### API Endpoints Used by Frontend

| Method | URL | Used For |
|--------|-----|----------|
| POST | /api/transactions/parse | Parse an SMS |
| GET | /api/transactions | List all transactions |
| GET | /api/transactions/total | Total spend amount |
| GET | /api/transactions/merchant-count | Merchant frequency map |
| GET | /api/transactions/count | Total transaction count |

---

### Build for Production

```bash
npm run build
```

Output goes to the `dist/` folder. You can either serve it with any static file server, or configure Spring Boot to serve it by copying `dist/` contents into:

```
upiparser/src/main/resources/static/
```

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

## 🔧 Troubleshooting

| Problem | Fix |
|---------|-----|
| "Backend unreachable" banner in UI | Start Spring Boot first on port 8080 |
| CORS error in browser console | Make sure `CorsConfig.java` is added to backend |
| Parse returns "Unable to parse SMS" | SMS must contain ₹amount + merchant keyword |
| Port 5173 already in use | Change port in `vite.config.js` → `server: { port: 3000 }` |
| `git push` rejected | Run `git pull origin main --allow-unrelated-histories` first |
| Maven not found | Use `mvnw.cmd` (Windows) or `./mvnw` (Mac/Linux) instead |

---

## 🌱 Roadmap

- [x] Regex-based SMS parsing
- [x] REST API with global response wrapper
- [x] Spring Data JPA persistence
- [x] React dashboard with analytics
- [x] Raw SMS file logging
- [x] CORS configuration for frontend-backend connection
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
