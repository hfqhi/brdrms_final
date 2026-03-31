# 🏠 BRDRMS — Boarding Room Management System

A desktop-based **Boarding Room Management System** developed in **Java** as a final System Development activity for **NC-3 (Java Programming)**.

![Language](https://img.shields.io/badge/language-Java-orange?logo=java)
![IDE](https://img.shields.io/badge/IDE-NetBeans-1B6AC6?logo=apache-netbeans-ide)
![Build](https://img.shields.io/badge/build-Apache%20Ant-A81C7D)
![License](https://img.shields.io/badge/license-unlicensed-red)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Running the App](#running-the-app)
- [Database Setup](#database-setup)
- [Diagrams](#diagrams)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

**BRDRMS** (Boarding Room Management System) is a Java-based desktop application built as the final output for a System Development course under the **NC-3 Java track**. The system is designed to manage boarding room operations including room management, tenant records, and reservations through a graphical user interface built with Java Swing.

The project was developed using **NetBeans IDE** and uses **Apache Ant** as its build system, following a standard NetBeans project structure.

---

## Tech Stack

| Technology | Role |
|---|---|
| Java | Core programming language |
| Java Swing | Graphical User Interface (GUI) |
| Apache Ant | Build automation (`build.xml`) |
| NetBeans IDE | Development environment |
| SQL Database | Data persistence (via `db/` folder) |

---

## Project Structure

```
brdrms_final/
│
├── src/
│   └── com/joysistvi/brdrms/     # Java source files (main package)
│
├── build/
│   └── classes/                   # Compiled .class files (generated)
│
├── db/                            # Database scripts / SQL files
│
├── nbproject/                     # NetBeans project configuration files
│
├── Database Design.png            # Entity-Relationship / database design diagram
├── Use Case Diagram.jpeg          # UML Use Case Diagram
└── build.xml                      # Apache Ant build script
```

---

## Prerequisites

Make sure the following are installed before running the project:

- **[Java JDK](https://www.oracle.com/java/technologies/downloads/)** (JDK 8 or higher recommended)
- **[NetBeans IDE](https://netbeans.apache.org/)** (recommended for opening the project directly)
- **[Apache Ant](https://ant.apache.org/)** (if building from the command line)
- A compatible **SQL database server** (e.g., MySQL or Apache Derby) for the database scripts in `db/`

---

## Setup & Installation

1. **Clone the repository:**

```bash
git clone https://github.com/hfqhi/brdrms_final.git
```

2. **Open in NetBeans:**
   - Launch NetBeans IDE
   - Go to **File → Open Project**
   - Navigate to the cloned `brdrms_final/` folder and open it
   - NetBeans will automatically recognize it as an Ant-based Java project

3. **Or build via command line (Apache Ant):**

```bash
cd brdrms_final
ant build
```

---

## Running the App

**From NetBeans:**
- Press **F6** or click **Run → Run Project**

**From the command line:**

```bash
ant run
```

---

## Database Setup

The `db/` folder contains the SQL scripts required to set up the database.

1. Open your SQL database client (e.g., MySQL Workbench, phpMyAdmin, or the command line)
2. Create a new database (e.g., `brdrms_db`)
3. Import/execute the SQL script(s) found in the `db/` folder
4. Update the database connection settings in the source code to match your local credentials (host, port, username, password)

---

## Diagrams

The repository includes the following system design documents:

| File | Description |
|---|---|
| `Database Design.png` | Visual representation of the database schema and table relationships |
| `Use Case Diagram.jpeg` | UML Use Case Diagram showing system actors and interactions |

---

## Contributing

This project was developed as an academic activity. If you'd like to build on it:

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

---

## License

This project currently has no license declared. All rights are reserved by the author unless stated otherwise. If you wish to use or build upon this project, please contact the repository owner.
