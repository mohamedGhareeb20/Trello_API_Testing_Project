# 🧪 Trello REST API Automation Framework

A comprehensive, production-grade test validation suite for the **Trello REST API**, combining **headless exploratory testing via Newman CLI** and an isolated, self-cleaning **Java-based Test Automation Framework (TAF)** built on REST Assured and TestNG.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Trello API Credentials](#trello-api-credentials)
- [Running the Tests](#-running-the-tests)
  - [Postman / Newman (Manual & CLI)](#postman--newman-manual--cli)
  - [Java Automation (REST Assured)](#java-automation-restassured)
- [Test Coverage](#-test-coverage)
- [Defect Tracking](#-defect-tracking)
- [Reports & Observability](#-reports--observability)

---

## 📌 Project Overview

This project implements a dual-track strategy to validate Trello's core REST endpoints, focusing on test case isolation, contract verification, and performance SLAs:

*   **Manual & Headless CLI Phase**: Request building in Postman and command-line execution using Newman CLI.
*   **Automated Phase**: Modular, parallel-ready Java test classes using REST Assured and TestNG.
*   **Design Paradigm**: Employs the **API Object Model (AOM)** to decouple low-level HTTP network calls from test scripts.
*   **Data Lifecycle**: Leverages self-cleaning lifecycles where each test class programmatically creates its own preconditions in `@BeforeClass` and purges them in `@AfterClass` to prevent sandbox data pollution.

---

## 🛠 Tech Stack

| Tool / Technology | Purpose |
|---|---|
| **Java 21 (LTS)** | Primary programming language |
| **REST Assured (6.0.0)** | HTTP client execution, JSON assertions, and contract validations |
| **TestNG (7.12.0)** | Test runner, lifecycle configuration hooks, and suite execution |
| **Maven** | Build, compiler target, and dependency management |
| **Lombok & Jackson** | POJO payload serialization (Builders) and response deserialization |
| **Allure (2.27.0 / 2.29.1)** | Interactive, graphical HTML reporting and HTTP transaction logging |
| **Log4j2 (2.26.0)** | Structural console and file execution logging |
| **Postman & Newman** | Manual verification and command-line collection execution |

---

## 📁 Project Structure

This outline represents the actual compiled layout of the repository (excluding target build folders):

```text
Trello_API_Testing_Project
├───Documentation/
│       Manual Testing Artifacts.xlsx     # Test Plan, Test Cases, Defect Report
│
├───Postman/
│       TrelloAPI.postman_collection.json # 22 Manual collection test cases
│       Trello-API-Sandbox.postman_environment.json # Sandbox environment configurations
│
└───TrelloAutomation/                     # Java REST Assured Framework
    │   pom.xml                           # Maven dependencies & compile targets
    │   testng.xml                        # TestNG suite runner XML
    │
    └───src/
        ├───main/
        │   ├───java/com/trello/api/
        │   │   ├───assertions/           # ApiAssertions (status codes, JSON paths)
        │   │   ├───clients/              # RestClient & specific clients (Board, List, Card)
        │   │   ├───listeners/            # TestNGListeners (Allure error captures)
        │   │   ├───models/               # Payloads (request) & Responses (POJOs)
        │   │   ├───specs/                # TrelloSpecBuilder & Global Response Time SLA Filters
        │   │   └───utils/                # ConfigManager, JsonReader, SchemaValidator
        │   │
        │   └───resources/
        │       │   environment.properties# Active API Base URL, Key, and Token
        │       │   log4j2.properties     # Logging formats and logfile targets
        │       └───schemas/              # JSON Schema contract validation files
        │
        └───test/
            └───java/com/trello/api/tests/# 17 Isolated Automated Test Classes
            
```
---
## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- **Java JDK 11+**
- **Maven 3.6+**
- **Node.js & npm** (for Newman)
- **Postman** (for manual test execution)

Install Newman and the HTML Extra reporter globally:

```bash
npm install -g newman
npm install -g newman-reporter-htmlextra
```

---

### Trello API Credentials

1. Log in to [Trello](https://trello.com) and navigate to the [Trello Developer API Key page](https://trello.com/app-key).
2. Copy your **API Key**.
3. Generate a **Token** by clicking the Token link on the same page.

---

## ▶️ Running the Tests

### Postman / Newman (Manual & CLI)

**Option 1 — Run in Postman:**

1. Open Postman and import the collection from `Postman/Trello_Collection.json`.
2. Import the environment from `Postman/Trello_Environment.json`.
3. Set your `key` and `token` variables in the environment.
4. Click **Run Collection**.

**Option 2 — Run via Newman (CLI):**

```bash
newman run Postman/Trello_Collection.json \
  -e Postman/Trello_Environment.json \
  --env-var "key=YOUR_API_KEY" \
  --env-var "token=YOUR_TOKEN" \
  -r htmlextra \
  --reporter-htmlextra-export newman/report.html
```
---

### Java Automation (RestAssured)

1. Clone the repository:

```bash
git clone https://github.com/mohamedGhareeb20/Trello_API_Testing_Project.git
cd Trello_API_Testing_Project/TrelloAutomation
```

2. Set your Trello credentials in the project's configuration (environment variables or config file):

```bash
export TRELLO_KEY=your_api_key_here
export TRELLO_TOKEN=your_token_here
```

3. Run the tests with Maven:

```bash
mvn test
```

---

## ✅ Test Coverage

| Feature | Operations Covered |
|---|---|
| **Boards** | Create, Get, Update, Delete |
| **Lists** | Create, Get, Update, Archive |
| **Cards** | Create, Get, Update, Move, Delete |
| **Checklists** | Create, Get, Update, Delete |

---

## 📊 Reports

Newman generates an HTML report after each CLI run. You can find the generated report at:

```
newman/report.html
```

Open it in any browser to view detailed results including:

- Pass/fail status per request
- Request & response details
- Assertion results
- Test execution summary

---

## 📄 Documentation

All test documentation is located in the `Documentation/` folder, including:

- Test Plan & Analysis
- Test Cases
- Test Execution Shedule
- Test Execution
- Defect Reports 
- Test Completion Reprt

---

## 🔗 References

- [Trello REST API Documentation](https://developer.atlassian.com/cloud/trello/rest/)
- [RestAssured Documentation](https://rest-assured.io/)
- [Newman Documentation](https://learning.postman.com/docs/collections/using-newman-cli/command-line-integration-with-newman/)
