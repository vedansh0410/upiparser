# UPI Parser — Frontend Setup & Connection Guide

## Project Structure

```
upi-frontend/
├── index.html
├── vite.config.js        ← Proxy config (routes /api → localhost:8080)
├── package.json
├── CorsConfig.java       ← Copy this to your Spring Boot project
└── src/
    ├── main.jsx
    ├── index.css
    ├── App.jsx
    └── App.css
```

---

## Step 1 — Add CorsConfig.java to Spring Boot

Copy `CorsConfig.java` into your backend at:

```
src/main/java/com/example/upiparser/config/CorsConfig.java
```

This allows the React frontend to call the Spring Boot API.

---

## Step 2 — Start Spring Boot Backend

In your Spring Boot project folder, run:

```bash
# Maven
mvn spring-boot:run

# OR Gradle
./gradlew bootRun
```

Backend will start at: http://localhost:8080

Verify it works:
```bash
curl http://localhost:8080/api/transactions
```

---

## Step 3 — Install Frontend Dependencies

Make sure you have Node.js 16+ installed. Then in this folder:

```bash
npm install
```

---

## Step 4 — Run the Frontend

```bash
npm run dev
```

Open your browser at: **http://localhost:5173**

---

## How the Connection Works

Vite's dev proxy (configured in `vite.config.js`) forwards all `/api` requests
from the React app to Spring Boot:

```
Browser → http://localhost:5173/api/transactions
          ↓ (Vite proxy)
Backend → http://localhost:8080/api/transactions
```

This means **no CORS issues** during development and no hardcoded backend URL in the React code.

---

## API Endpoints Used

| Method | URL                              | Used For                   |
|--------|----------------------------------|----------------------------|
| POST   | /api/transactions/parse          | Parse an SMS               |
| GET    | /api/transactions                | List all transactions      |
| GET    | /api/transactions/total          | Total spend amount         |
| GET    | /api/transactions/merchant-count | Merchant frequency map     |
| GET    | /api/transactions/count          | Total transaction count    |

---

## Build for Production

```bash
npm run build
```

Output goes to the `dist/` folder. You can serve it with any static file server,
or configure Spring Boot to serve it by copying `dist/` into
`src/main/resources/static/`.

---

## Troubleshooting

| Problem | Fix |
|---------|-----|
| "Backend unreachable" banner | Start Spring Boot first on port 8080 |
| CORS error in browser console | Make sure CorsConfig.java is added to backend |
| Parse returns "Unable to parse SMS" | SMS format must contain ₹amount + merchant keyword |
| Port 5173 already in use | Change port in vite.config.js → `server: { port: 3000 }` |
