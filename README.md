# Asia_Power_Analysis
# Dynamic Ranking System for National Power Analysis

This repository contains the code for the project **"Dynamic Ranking System for National Power Analysis"** . The project focuses on computing dynamic rankings of countries based on multiple weighted parameters that reflect a nation's power and potential.

🔗 GitHub Repository: [https://github.com/Thakurnikhill/Asia_Power_Analysis](https://github.com/Thakurnikhill/Asia_Power_Analysis)

## 🧠 Project Summary

**Dynamic Ranking System for National Power Analysis** is inspired by the Asia Power Index concept. Instead of focusing on geopolitical power, this implementation evaluates countries based on key indicators such as economic capability, military strength, and cultural influence. These indicators are weighted to produce a total score, which is used to rank 27 countries.

Built using **Spring Boot** and **PostgreSQL**, the system provides RESTful APIs to submit data, calculate rankings, and fetch analysis results. It has been thoroughly tested with Postman to ensure robust performance and accuracy.

## 🔍 Key Features

- REST APIs to submit country-specific metrics and fetch rankings
- Auto-recalculation of rankings upon new data submission
- Trend analysis to show UPWARD or DOWNWARD movement
- Covers 27 countries/territories with comprehensive parameter tracking
- Data stored persistently using PostgreSQL
- Tested and validated using Postman collections

## 🛠️ Tech Stack

| Component         | Technology/Tool        |
|------------------|------------------------|
| Backend Framework| Spring Boot (Java 17)  |
| Database         | PostgreSQL 15          |
| Testing Tool     | Postman                |
| Build Tool       | Maven                  |
| IDE              | IntelliJ IDEA          |

## 🗂️ System Architecture

The project follows a layered Spring Boot architecture:

- **Controller Layer**: Exposes endpoints such as `/measures`, `/rankings`, and `/countries`.
- **Service Layer**: Handles ranking logic and score calculations.
- **Repository Layer**: Manages PostgreSQL interactions using Spring Data JPA.
- **Database Layer**: Includes two main tables:
  - `country_measures`: Stores raw input metrics.
  - `country_rankings`: Stores computed rankings and trends.

## 📦 API Endpoints

Base path: `/api/power-index`

| Method | Endpoint                         | Description                                  |
|--------|----------------------------------|----------------------------------------------|
| POST   | `/measures`                      | Submit/update metrics for a country          |
| GET    | `/rankings/comprehensive`        | Get overall rankings sorted by total score   |
| GET    | `/rankings/{measure}`            | Get rankings based on a specific measure     |
| GET    | `/countries/{countryName}`       | Get all rankings for a specific country      |

Example request body for `/measures`:

```json
{
  "countryName": "India",
  "economicCapability": 65.0,
  "militaryCapability": 45.2,
  "resilience": 56.5,
  "futureResources": 55.9,
  "economicRelationships": 17.5,
  "defenceNetworks": 19.0,
  "diplomaticInfluence": 70.2,
  "culturalInfluence": 38.7
}
