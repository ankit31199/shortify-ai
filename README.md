# 🚀 Shortify.ai — Simple & Scalable URL Shortener

> A clean, high-performance URL shortening service built with modern backend practices.

---

---

## 📌 Overview
**Shortify.ai** is a backend-focused project that converts long URLs into short, shareable links with reliable redirection and a scalable architecture.

This project highlights:
- Strong backend fundamentals
- Clean API design
- Scalable system thinking

---

## ✨ Features

- 🔗 Shorten long URLs into compact links
- ⚡ Fast and reliable redirection
- 🧩 Clean REST API structure
- 🔐 Unique short code generation
- 📦 Modular and maintainable codebase

---

## 🏗️ Tech Stack

- **Backend:** Java, Spring Boot
- **Database:** MongoDB *(or your actual DB — update accordingly)*
- **Build Tool:** Maven
- **API Testing:** Postman

---

## 📂 Project Structure
shortify-ai/
│── src/main/java/
│ ├── controller/
│ ├── service/
│ ├── repository/
│ ├── model/
│ └── config/
│
│── src/main/resources/
│── pom.xml
│── README.md


---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MongoDB *(if used)*

### Run Locally

```bash
git clone https://github.com/your-username/shortify-ai.git
cd shortify-ai
mvn clean install
mvn spring-boot:run

| Method | Endpoint       | Description              |
| ------ | -------------- | ------------------------ |
| POST   | `/shorten`     | Create a short URL       |
| GET    | `/{shortCode}` | Redirect to original URL |
```
⚙️ How It Works

* User sends a long URL to /shorten
* System generates a unique short code
* URL is stored in the database
* Visiting the short link redirects to the original URL

📈 What This Project Demonstrates

* Backend development using Spring Boot
* REST API design best practices
* Database integration
* Clean and maintainable architecture

👨‍💻 About Me

Ankit Tiwari

1. Java Backend Developer @ TCS
Skilled in Spring Boot, APIs, and system design
2. Exploring scalable backend systems

⭐ Support

If you found this useful:

⭐ Star the repo
🤝 Connect with me