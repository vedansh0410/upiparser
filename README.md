# UPI Parser 💸

A full-stack UPI SMS Transaction Parser built with **Spring Boot** (backend) and **React + Vite** (frontend).

It parses UPI SMS messages, extracts transaction amount and merchant name, stores them in a database, and displays analytics on a dashboard.

---

## 📁 Project Structure
upiparser/

├── upiparser/          # Spring Boot Backend

│   ├── src/

│   ├── pom.xml

│   ├── mvnw

│   └── mvnw.cmd

│

└── upi-frontend/       # React + Vite Frontend

├── src/

├── index.html

├── vite.config.js

└── package.json

---

## ⚙️ Backend — Spring Boot

### Prerequisites
- Java 17+
- Maven

### Run the backend

```bash
cd upiparser
./mvnw spring-boot:run
```

On Windows:
```cmd
cd upiparser
mvnw.cmd spring-boot:run
```

Backend starts at: **http://localhost:8080**

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/transactions/parse` | Parse a UPI SMS |
| GET | `/api/transactions` | Get all transactions |
| GET | `/api/transactions/total` | Get total spend |
| GET | `/api/transactions/count` | Get transaction count |
| GET | `/api/transactions/merchant-count` | Get merchant frequency |
| GET | `/api/transactions/merchant/{name}` | Get transactions by merchant |

### Sample SMS formats supported

Debited ₹500 to Amazon via PhonePe
Paid ₹1500 to Swiggy via Google Pay
Sent ₹800 to Zomato via BHIM UPI
₹299 debited to Netflix via ICICI UPI
---

## 🖥️ Frontend — React + Vite

### Prerequisites
- Node.js 16+
- npm

### Run the frontend

```bash
cd upi-frontend
npm install
npm run dev
```

Frontend starts at: **http://localhost:5173**

> Vite automatically proxies all `/api` requests to `http://localhost:8080` — no CORS issues.

### Features
- 📥 **Parse SMS** — paste any UPI SMS and extract transaction data
- 📋 **Transactions** — view all saved transactions in real time
- 📊 **Analytics** — merchant bar chart, total spend, average per transaction

---

## 🔗 How Frontend Connects to Backend

The `vite.config.js` has a built-in proxy:

Browser → localhost:5173/api/...
↓ Vite proxy
Backend → localhost:8080/api/...

No hardcoded URLs. No CORS errors.

---

## 🚀 Running the Full Stack

1. Start the backend first:
```cmd
cd upiparser
mvnw.cmd spring-boot:run
```

2. In a new terminal, start the frontend:
```cmd
cd upi-frontend
npm install
npm run dev
```

3. Open **http://localhost:5173** in your browser.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot, Spring Data JPA |
| Database | MySQL |
| Frontend | React 18, Vite, CSS |
| API | REST |
| Build | Maven, npm |

---

## 👤 Author

**Vedansh** — [github.com/vedansh0410](https://github.com/vedansh0410)
