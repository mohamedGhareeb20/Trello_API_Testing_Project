# 🧪 Trello API Testing Project

A comprehensive API testing suite for the **Trello REST API**, combining **manual testing with Postman**, **automated test execution via Newman**, and **Java-based test automation using RestAssured**. The project covers end-to-end scenarios including boards, lists, and cards management.

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
  - [Java Automation (RestAssured)](#java-automation-restassured)
- [Test Coverage](#-test-coverage)
- [Reports](#-reports)
- [Documentation](#-documentation)

---

## 📌 Project Overview

This project tests the **Trello REST API** (`https://api.trello.com/1/`) and validates core functionality including:

- Creating, updating, and deleting **Boards**
- Managing **Lists** within boards
- CRUD operations on **Cards**
- End-to-end (E2E) workflow scenarios
- Automated CLI execution via **Newman** with HTML report generation
- CI-ready structure for integration with **Jenkins** or GitHub Actions

---

## 🛠 Tech Stack

| Tool / Technology | Purpose |
|---|---|
| **Java** | Primary automation language |
| **RestAssured** | API test automation framework |
| **TestNG / JUnit** | Test runner |
| **Maven** | Build & dependency management |
| **Postman** | Manual API testing & collection creation |
| **Newman** | CLI runner for Postman collections |
| **Newman HTML Extra** | HTML test report generation |
| **Jenkins** *(optional)* | CI/CD pipeline integration |

---

## 📁 Project Structure

```
Trello_API_Testing_Project/
│
├── TrelloAutomation/          # Java + RestAssured automation project
│   ├── src/
│   │   └── test/
│   │       └── java/          # Test classes
│   └── pom.xml                # Maven configuration
│
├── Postman/                   # Postman collection & environment files
│   ├── Trello_Collection.json
│   └── Trello_Environment.json
│
├── Documentation/             # Test plan, test cases, and project docs
│
├── newman                     # Newman HTML report output
│
└── README.md
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
| **E2E Scenarios** | Full board-to-card workflow |

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

- Test Plan
- Test Cases
- Bug Reports *(if applicable)*

---

## 🔗 References

- [Trello REST API Documentation](https://developer.atlassian.com/cloud/trello/rest/)
- [RestAssured Documentation](https://rest-assured.io/)
- [Newman Documentation](https://learning.postman.com/docs/collections/using-newman-cli/command-line-integration-with-newman/)
