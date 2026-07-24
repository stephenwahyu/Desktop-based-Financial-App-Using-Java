# 💼 Trada — Desktop Financial Management App

> A full-featured desktop-based financial management application built with **Java Swing**, designed for small-to-medium enterprises to manage accounts, budgets, transactions, and generate accounting reports.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Configuration](#database-configuration)
- [Running the Application](#running-the-application)
- [Building a Distributable JAR](#building-a-distributable-jar)
- [Module Descriptions](#module-descriptions)
- [Architecture](#architecture)
- [Dependencies](#dependencies)

---

## 🗂️ Overview

**Trada** (`trada-self`) is a Java-based desktop financial application developed for **PT. SPR Trada**. It provides a modern, dark/light-mode-ready GUI built on top of the FlatLaf look-and-feel framework. The system supports complete double-entry bookkeeping, including income and expense tracking, journal entries, balance sheets, profit/loss statements, and cash flow reports — all exportable to PDF, Excel, and Word formats via JasperReports.

- **Version:** `1.0.0`
- **Organization:** PT. SPR Trada
- **Director:** Bemi Hendrias
- **Java Version:** Java 8+
- **Build Tool:** Apache Maven

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **Authentication** | Secure login with BCrypt password hashing and role-based access |
| 📊 **Dashboard** | Interactive charts (Bar, Pie, Spider, Time Series) with monthly & yearly summaries |
| 🏦 **Account Management** | Create and manage Chart of Accounts (Akun) |
| 💰 **Budget Management** | Plan and monitor budget allocations (Anggaran) |
| 📥 **Income Transactions** | Record, edit, delete, and export income entries (Pemasukan) |
| 📤 **Expense Transactions** | Record, edit, delete, and export expense entries (Pengeluaran) |
| 📒 **Journal** | General ledger journal view (Jurnal) |
| ⚖️ **Balance Sheet** | Full balance sheet report (Neraca) |
| 📈 **Profit & Loss** | Income statement / profit-loss report (Laba Rugi) |
| 💧 **Cash Flow** | Cash flow statement (Arus Kas) |
| 🖨️ **Report Export** | Export reports to PDF, XLSX (Excel), DOCX (Word), and PPTX via JasperReports |
| 🌗 **Light/Dark Theme** | Toggle between light and dark modes with FlatLaf IntelliJ themes |
| 🔍 **Form Search** | Global keyboard shortcut to search and navigate between forms |
| ↩️ **Undo/Redo Navigation** | Navigate back and forward through previously opened forms |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 8 |
| **UI Framework** | Java Swing |
| **Look & Feel** | FlatLaf 3.5.4 + IntelliJ Themes + Roboto Font |
| **Layout Manager** | MigLayout 5.3 |
| **Charts** | JFreeChart 1.5.5 |
| **Database** | MySQL |
| **DB Connection Pool** | Apache Commons DBCP2 2.13.0 |
| **Reporting** | JasperReports 7.0.1 (PDF, Excel, Word, PowerPoint) |
| **PDF Generation** | OpenPDF 2.0.3 |
| **Password Hashing** | jBCrypt 0.4 |
| **Logging** | Apache Log4j 2.20.0 |
| **UI Extras** | swing-datetime-picker, modal-dialog, RSyntaxTextArea |
| **Build Tool** | Apache Maven |

---

## 📁 Project Structure

```
trada-self/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── raven/modal/demo/
│   │   │       ├── Demo.java                  # Entry point / Main JFrame
│   │   │       ├── auth/
│   │   │       │   └── Login.java             # Login form with BCrypt auth
│   │   │       ├── component/                 # Reusable UI components
│   │   │       │   ├── About.java
│   │   │       │   ├── chart/                 # Chart components (Bar, Pie, Spider, TimeSeries)
│   │   │       │   └── dashboard/             # Dashboard card components
│   │   │       ├── connection/
│   │   │       │   └── DatabaseConnection.java # Singleton MySQL connection pool
│   │   │       ├── create/                    # Form dialogs for creating records
│   │   │       ├── forms/                     # Main application screens (one per module)
│   │   │       │   ├── FormDashboard.java
│   │   │       │   ├── FormAkun.java
│   │   │       │   ├── FormAnggaran.java
│   │   │       │   ├── FormPemasukan.java
│   │   │       │   ├── FormPengeluaran.java
│   │   │       │   ├── FormJurnal.java
│   │   │       │   ├── FormNeraca.java
│   │   │       │   ├── FormLaba.java
│   │   │       │   └── FormArusKas.java
│   │   │       ├── menu/
│   │   │       │   └── MyDrawerBuilder.java   # Side navigation drawer
│   │   │       ├── model/                     # Data model (POJO) classes
│   │   │       │   ├── ModelAkun.java
│   │   │       │   ├── ModelAnggaran.java
│   │   │       │   ├── ModelTransaksi.java
│   │   │       │   ├── ModelJurnal.java
│   │   │       │   ├── ModelNeraca.java
│   │   │       │   ├── ModelLaba.java
│   │   │       │   ├── ModelArusKas.java
│   │   │       │   ├── ModelUser.java
│   │   │       │   └── ...
│   │   │       ├── modelreport/               # Report-specific data models
│   │   │       ├── report/
│   │   │       │   ├── ReportManager.java     # JasperReports engine wrapper
│   │   │       │   ├── LabaRugi.jrxml         # Profit/Loss report template
│   │   │       │   ├── Neraca.jrxml           # Balance sheet report template
│   │   │       │   ├── Pemasukan.jrxml        # Income report template
│   │   │       │   └── Pengeluaran.jrxml      # Expense report template
│   │   │       ├── service/                   # Business logic / DAO services
│   │   │       │   ├── ServiceDashboard.java
│   │   │       │   ├── ServiceAkun.java
│   │   │       │   ├── ServiceAnggaran.java
│   │   │       │   ├── ServicePemasukan.java
│   │   │       │   ├── ServicePengeluaran.java
│   │   │       │   ├── ServiceJurnal.java
│   │   │       │   ├── ServiceNeraca.java
│   │   │       │   ├── ServiceLaba.java
│   │   │       │   ├── ServiceArusKas.java
│   │   │       │   └── ServiceUser.java
│   │   │       ├── system/                    # Core app framework
│   │   │       │   ├── Form.java              # Base class for all screens
│   │   │       │   ├── FormManager.java       # Navigation/session manager
│   │   │       │   ├── AllForms.java          # Form registry
│   │   │       │   ├── FormSearch.java        # Global search
│   │   │       │   └── MainForm.java          # Main content area
│   │   │       ├── themes/                    # FlatLaf theme configuration
│   │   │       └── utils/                     # Utilities (Preferences, UndoRedo, etc.)
│   │   └── resources/
│   │       └── raven/modal/demo/
│   │           ├── drawer/                    # Sidebar icons and images
│   │           ├── icons/                     # Application icons
│   │           ├── images/                    # Image assets
│   │           ├── report/                    # Compiled report assets
│   │           └── themes/                    # Theme property files
│   └── test/                                  # Unit tests (JUnit 5)
├── pom.xml                                    # Maven build config
├── mvnw / mvnw.cmd                            # Maven wrapper scripts
└── README.md
```

---

## ✅ Prerequisites

Before running this application, make sure you have the following installed:

- **Java JDK 8** or higher
  → Download from https://www.oracle.com/java/technologies/downloads/

- **Apache Maven 3.6+** *(or use the included Maven Wrapper)*
  → Download from https://maven.apache.org/download.cgi

- **MySQL 8.0+**
  → Download from https://dev.mysql.com/downloads/

---

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/stephenwahyu/Desktop-based-Financial-App-Using-Java.git
cd trada-self
```

### 2. Install Dependencies

```bash
mvn install
# or using the Maven wrapper:
./mvnw install        # Linux/macOS
mvnw.cmd install      # Windows
```

---

## 🗄️ Database Configuration

### 1. Create the Database

Log in to MySQL and run:

```sql
CREATE DATABASE trada_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configure the Connection

Open `src/main/java/raven/modal/demo/connection/DatabaseConnection.java` and update the credentials:

```java
private String host     = "localhost";
private String port     = "3306";
private String database = "trada_db";
private String username = "root";
private String password = "";      // <- update with your MySQL password
```

### 3. Seed Initial Users

Passwords are stored using **BCrypt** hashing. Insert a user manually into your database:

```sql
-- Example: insert an admin user (generate bcrypt hash first)
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$...bcrypt_hash_here...', 'admin');
```

> **Tip:** Generate a BCrypt hash using a small Java utility:
> ```java
> String hash = BCrypt.hashpw("yourpassword", BCrypt.gensalt());
> ```

---

## ▶️ Running the Application

### Via Maven

```bash
mvn exec:java -Dexec.mainClass="raven.modal.demo.Demo"
```

### Via IDE (IntelliJ IDEA / Eclipse)

1. Open the project as a **Maven project**
2. Let the IDE resolve all dependencies
3. Run the `main()` method in `Demo.java`

---

## 📦 Building a Distributable JAR

To produce a standalone fat JAR (includes all dependencies):

```bash
mvn package
```

The output file will be located at:

```
target/trada-1.0-SNAPSHOT.jar
```

Run the JAR directly:

```bash
java -jar target/trada-1.0-SNAPSHOT.jar
```

---

## 📚 Module Descriptions

### 🔐 Authentication (`auth`)
- **Login.java** — The login screen. Fetches all users from the database, validates credentials against BCrypt-hashed passwords, and sets the active user role upon success.

### 📊 Dashboard (`forms/FormDashboard`)
- Displays summary cards (total income, expenses, net balance).
- Renders interactive **time series**, **bar**, **pie**, and **spider charts** sourced from `ServiceDashboard`.
- Supports daily and monthly data breakdowns via toolbar toggles.

### 🏦 Accounts (`forms/FormAkun`)
- Manage the **Chart of Accounts** (Akun).
- Create, update, and delete account categories used throughout journal entries.

### 💰 Budget (`forms/FormAnggaran`)
- Set and monitor budget targets per account category.
- Compares allocated budget vs. actual spending.

### 📥 Income Transactions (`forms/FormPemasukan`)
- Full CRUD operations for income entries (Pemasukan) with date filtering.
- Export to **PDF, Excel (.xlsx), Word (.docx), PowerPoint (.pptx)** via JasperReports.

### 📤 Expense Transactions (`forms/FormPengeluaran`)
- Full CRUD operations for expense entries (Pengeluaran) with date filtering.
- Export to **PDF, Excel (.xlsx), Word (.docx), PowerPoint (.pptx)** via JasperReports.

### 📒 Journal (`forms/FormJurnal`)
- View all journal entries generated from income/expense transactions.
- Displays debit/credit postings per account.

### ⚖️ Balance Sheet (`forms/FormNeraca`)
- Generates a full **Neraca (Balance Sheet)** showing assets vs. liabilities and equity.
- Supports date-range filtering and PDF export.

### 📈 Profit & Loss (`forms/FormLaba`)
- Generates **Laba Rugi (Profit & Loss Statement)**.
- Summarizes revenues, expenses, and net profit over a selected period.

### 💧 Cash Flow (`forms/FormArusKas`)
- Generates **Arus Kas (Cash Flow Statement)**.
- Categorizes operating, investing, and financing cash activities.

---

## 🏗️ Architecture

The application follows a **layered MVC-like architecture**:

```
+---------------------------------------------+
|            UI Layer (View)                  |
|  Forms (FormXxx.java) - Swing JPanel-based  |
+---------------------------------------------+
|        Navigation / Controller              |
|  FormManager  - screen routing & session   |
|  MyDrawerBuilder - sidebar navigation       |
+---------------------------------------------+
|          Service Layer (Model)              |
|  ServiceXxx.java - business logic & DAO    |
+---------------------------------------------+
|           Data / Model Layer                |
|  ModelXxx.java - plain Java POJOs           |
+---------------------------------------------+
|         Database Connection                 |
|  DatabaseConnection (Singleton + DBCP2)    |
|  MySQL via JDBC                             |
+---------------------------------------------+
```

### Key Design Patterns

| Pattern | Usage |
|---|---|
| **Singleton** | `DatabaseConnection` — single shared connection pool instance |
| **MVC** | Forms (View) / Services (Model) / FormManager (Controller) |
| **Observer** | `UIManager.addPropertyChangeListener` for live theme switching |
| **Command** | Drawer menu events via `MenuEvent` / `action.consume()` |
| **Template Method** | `Form` base class defines `formInit()`, `formOpen()`, `formCheck()`, `formRefresh()` hooks |
| **Undo/Redo Stack** | `UndoRedo<Form>` for back/forward navigation between screens |

---

## 📦 Dependencies

| Library | Version | Purpose |
|---|---|---|
| `flatlaf` | 3.5.4 | Modern Swing look-and-feel |
| `flatlaf-extras` | 3.5.4 | AvatarIcon and extra FlatLaf components |
| `flatlaf-intellij-themes` | 3.2.5 | Additional UI themes (One Dark, Nord, etc.) |
| `flatlaf-fonts-roboto` | 2.137 | Roboto font integration |
| `miglayout-swing` | 5.3 | Flexible and powerful layout manager |
| `swing-datetime-picker` | 2.0.0 | Date/time picker widget |
| `modal-dialog` | 2.2.0 | Modal dialog and drawer/sidebar system |
| `jfreechart` | 1.5.5 | Chart rendering (bar, pie, time series) |
| `mysql-connector-j` | 9.1.0 | MySQL JDBC driver |
| `commons-dbcp2` | 2.13.0 | Database connection pooling |
| `commons-pool2` | 2.12.0 | Object pooling (used internally by DBCP2) |
| `commons-logging` | 1.3.4 | Logging abstraction for Apache Commons |
| `jbcrypt` | 0.4 | BCrypt password hashing |
| `openpdf` | 2.0.3 | PDF generation |
| `jasperreports` | 7.0.1 | Report generation engine |
| `jasperreports-pdf` | 7.0.1 | PDF export support |
| `jasperreports-excel-poi` | 7.0.1 | Excel (.xlsx) export support |
| `jasperreports-groovy` | 7.0.1 | Groovy expression support in reports |
| `log4j-api` / `log4j-core` | 2.20.0 | Application logging |
| `rsyntaxtextarea` | 3.4.0 | Syntax-highlighted text areas |
| `junit-jupiter` | 5.9.3 | Unit testing framework |

---

## 📝 License

This project is intended for internal use by **PT. SPR Trada**. Please contact the project maintainer for licensing inquiries.

---

## 👤 Author

**Stephen Wahyu**
GitHub: [@stephenwahyu](https://github.com/stephenwahyu)

---

*Built with Java Swing and FlatLaf.*
