# 🚀 Shortify.ai — AI-Powered URL Shortener

> Transforming simple links into **intelligent, trackable, and scalable digital assets**

---

## 🔥 Overview

Shortify.ai is an **AI-powered URL shortening platform** that goes beyond traditional link shortening by providing:

* 📊 Real-time analytics
* ⚡ High-performance redirection
* 🤖 AI-driven insights *(planned/enhanced)*
* 🧠 Scalable backend architecture

---

## ✨ Features

### 🔗 Core Features

* URL shortening with unique key generation
* Instant redirection (<200ms latency)
* Expiry-based links
* Custom aliases *(optional)*

### 📊 Analytics

* Click tracking
* Top URLs
* Usage insights
* Future: Geo & device tracking

### 🤖 AI Features (Planned / In Progress)

* Smart link categorization
* Spam / malicious URL detection
* Click prediction engine
* Personalized link suggestions

---

## 🏗️ Architecture Diagram



**Flow:**
User → API Gateway → Spring Boot Service → DB
↘ Kafka (Analytics Pipeline)

---

## ⚙️ Tech Stack

* **Backend:** Java, Spring Boot
* **Database:** MongoDB
* **Messaging:** Apache Kafka *(for analytics pipeline)*
* **AI Layer:** Python / LLM (planned integration)
* **Deployment:** Docker / Cloud (future-ready)

---

## 🧠 System Design

### Key Components:

* URL Service → Generates & resolves short URLs
* Analytics Service → Tracks clicks & usage
* AI Service → Enhances insights *(future)*

### Design Decisions:

* Used **PostgreSQL**
* Optimized read-heavy operations for redirection
* Decoupled analytics using **Kafka (event-driven architecture)**

---

## ⚡ Scaling Strategy

### 🚀 Performance Optimizations:

* Efficient key generation (Base62 / hashing)
* Indexed database queries for fast lookups
* Low-latency redirect handling

### 📈 Scalability Improvements:

* Kafka for async analytics processing
* Redis caching *(can be added)*
* Horizontal scaling via stateless services

### 🛡️ Reliability:

* Fault-tolerant event pipeline
* Retry mechanisms for analytics

---

## 📡 API Documentation

### 🔗 Create Short URL

POST /api/shorten

**Request:**
{
"originalUrl": "https://example.com"
}

**Response:**
{
"shortUrl": "http://localhost:8080/abc123"
}

---

### 🔁 Redirect

GET /{shortCode}

---

### 📊 Analytics

GET /api/analytics

---

## 📸 Screenshots / Demo



* Postman API responses
* Analytics output
* UI dashboard *(if available)*

---

## 🧪 How to Run Locally

git clone https://github.com/ankit31199/shortify-ai.git
cd shortify-ai
mvn clean install
mvn spring-boot:run

---

## 🌍 Future Enhancements

* AI-powered recommendations
* Geo-location analytics
* Rate limiting & security
* Custom domains
* Dashboard UI

---

## ⭐ Support

If you like this project, consider giving it a ⭐ on GitHub!

---

## 👨‍💻 Author

**Ankit Tiwari**
Backend Developer | AI Enthusiast
