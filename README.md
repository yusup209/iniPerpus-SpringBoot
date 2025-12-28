# iniPerpus - Library Management System

A comprehensive library management system with face recognition-based student attendance. Built with Spring Boot (Java) and FastAPI (Python).

## 📋 Table of Contents

- [Features](#features)
- [Technical Stack](#technical-stack)
- [System Requirements](#system-requirements)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
  - [Database Setup](#1-database-setup)
  - [Spring Boot Application](#2-spring-boot-application)
  - [Python Face Recognition Service](#3-python-face-recognition-service)
- [Running the Application](#running-the-application)
- [Usage Guide](#usage-guide)
- [Configuration](#configuration)
- [Screenshots](#screenshots)

## ✨ Features

### Core Functionality
- **Student Management**: Add, edit, delete student records with pagination
- **Book Management**: Complete CRUD operations for book inventory with pagination
- **Lending Management**: Track book loans with due dates and return management
- **Student Presence**: Face recognition-based attendance system using camera

### Advanced Features
- Face registration and enrollment for students
- Real-time face matching for student check-ins
- Automatic face data cleanup on student deletion
- Session persistence during development with DevTools
- Responsive UI with glassmorphism design
- Client-side pagination for all data tables
- Confirmation dialogs for delete operations

## 🛠 Technical Stack

### Backend (Spring Boot Application)
- **Framework**: Spring Boot 4.0.0
- **Java Version**: Java 25 (JDK)
- **Build Tool**: Gradle
- **Database**: MariaDB 10.x+
- **ORM**: Hibernate / Spring Data JPA
- **Security**: Spring Security with form-based authentication
- **Template Engine**: Thymeleaf
- **Dependencies**:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Validation
  - Spring Boot DevTools (development)
  - MariaDB JDBC Driver
  - OpenPDF (for PDF generation)
  - Lombok

### Face Recognition Service (Python)
- **Framework**: FastAPI
- **Python Version**: Python 3.8+
- **Server**: Uvicorn
- **Dependencies**:
  - `fastapi` - Web framework
  - `uvicorn[standard]` - ASGI server
  - `face_recognition` - Face detection and recognition
  - `numpy` - Numerical computing
  - `Pillow` - Image processing
  - `python-multipart` - File upload support

### Frontend
- Vanilla JavaScript (ES6+)
- Thymeleaf templates
- CSS3 with gradients and modern styling
- Fetch API for AJAX requests

## 💻 System Requirements

### For Spring Boot Application
- **Java Development Kit (JDK)**: Version 25 or compatible
- **MariaDB**: Version 10.x or higher
- **Gradle**: 8.x+ (included via wrapper)
- **Memory**: Minimum 2GB RAM
- **IDE** (Optional): IntelliJ IDEA, Eclipse, NetBeans, or VS Code

### For Python Face Service
- **Python**: Version 3.8 or higher
- **pip**: Python package manager
- **C++ Build Tools**: Required for face_recognition library (dlib)
  - Windows: Visual Studio Build Tools or MinGW
  - Linux: gcc, g++, cmake
  - macOS: Xcode Command Line Tools
- **CMake**: Version 3.8+

## 📁 Project Structure

```
iniperpus/
├── src/
│   ├── main/
│   │   ├── java/com/kkp/iniperpus/
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── controller/       # REST and MVC controllers
│   │   │   ├── model/            # JPA entities
│   │   │   ├── repository/       # Data repositories
│   │   │   ├── service/          # Business logic
│   │   │   └── IniperpusApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql        # Database schema
│   │       ├── static/           # CSS, JS files
│   │       └── templates/        # Thymeleaf templates
│   └── test/                     # Test classes
├── python-face-service/
│   ├── main.py                   # FastAPI application
│   ├── requirements.txt          # Python dependencies
│   └── data/                     # Face encodings & images
├── build.gradle
├── gradlew                       # Gradle wrapper (Unix)
├── gradlew.bat                   # Gradle wrapper (Windows)
└── README.md
```

## 🚀 Installation & Setup

### 1. Database Setup

#### Install MariaDB

Download and install MariaDB from [mariadb.org](https://mariadb.org/download/)

#### Create Database

```sql
CREATE DATABASE iniperpus_2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### Configure Database User (Optional)

```sql
CREATE USER 'iniperpus'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON iniperpus_2.* TO 'iniperpus'@'localhost';
FLUSH PRIVILEGES;
```

#### Update Database Schema

The schema is automatically created on first run via `schema.sql`. To manually apply:

```bash
mysql -u root -p iniperpus_2 < src/main/resources/schema.sql
```

### 2. Spring Boot Application

#### Prerequisites Check

Verify Java installation:

```bash
java -version
```

Expected output: `java version "25"` or compatible.

#### Clone/Download Project

```bash
cd /path/to/iniperpus
```

#### Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/iniperpus_2?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

#### Build the Application

**Windows:**
```bash
.\gradlew.bat build
```

**Linux/macOS:**
```bash
./gradlew build
```

This will:
- Download dependencies
- Compile Java code
- Run tests
- Create executable JAR in `build/libs/`

#### Skip Tests (Optional)

```bash
./gradlew build -x test
```

### 3. Python Face Recognition Service

#### Navigate to Service Directory

```bash
cd python-face-service
```

#### Create Virtual Environment

**Windows:**
```bash
python -m venv venv
venv\Scripts\activate
```

**Linux/macOS:**
```bash
python3 -m venv venv
source venv/bin/activate
```

#### Install Dependencies

```bash
pip install -r requirements.txt
```

**Note**: Installing `face_recognition` may take time as it compiles dlib. Ensure C++ build tools are installed.

**Troubleshooting Installation:**

- **Windows**: Install Visual Studio Build Tools from [visualstudio.microsoft.com](https://visualstudio.microsoft.com/downloads/)
- **Linux**: `sudo apt-get install cmake libopenblas-dev liblapack-dev`
- **macOS**: `brew install cmake`

## ▶️ Running the Application

### Step 1: Start MariaDB

Ensure MariaDB service is running.

**Windows:**
```bash
net start MariaDB
```

**Linux:**
```bash
sudo systemctl start mariadb
```

### Step 2: Start Python Face Service

Open a terminal, navigate to `python-face-service/`, activate virtual environment:

```bash
cd python-face-service
source venv/bin/activate  # Windows: venv\Scripts\activate
uvicorn main:app --host 0.0.0.0 --port 8001 --reload
```

Service will start at: `http://localhost:8001`

API Documentation (Swagger): `http://localhost:8001/docs`

### Step 3: Start Spring Boot Application

Open another terminal, navigate to project root:

**Development Mode (with auto-restart):**

**Windows:**
```bash
.\gradlew.bat bootRun
```

**Linux/macOS:**
```bash
./gradlew bootRun
```

**Production Mode (from JAR):**

```bash
java -jar build/libs/iniperpus-0.0.1-SNAPSHOT.jar
```

Application will start at: `http://localhost:8080`

### Step 4: Access the Application

Open browser and navigate to:

```
http://localhost:8080
```

**Default Login Credentials:**
- Username: `admin@example.com`
- Password: `adminpass`

## 📖 Usage Guide

### Dashboard

- Access all features from the main dashboard
- Navigate to Student Presence, Student Management, Book Management, or Lending Management

### Student Management

1. **Add Student**: Fill form with Name, Student ID, and Class
2. **Edit Student**: Click edit icon (✎) on student row
3. **Delete Student**: Click delete icon (🗑️) with confirmation dialog
4. **Register Face**: Click camera icon (📸) to navigate to presence page

### Book Management

1. **Add Book**: Enter Title, Author, ISBN, and number of copies
2. **Edit Book**: Click edit icon to modify book details
3. **Delete Book**: Click delete icon with confirmation
4. **View Inventory**: See available vs. total copies

### Lending Management

1. **Lend Book**: Select student and book, system auto-calculates due date
2. **Return Book**: Click return button on active loan
3. **View Active Loans**: See all current borrowings with due dates

### Student Presence (Face Recognition)

1. **Start Camera**: Click "Start Camera" button to activate webcam
2. **Register Face**:
   - Select student from dropdown
   - Position face in camera
   - Click "Register Face" button
3. **Check-In**:
   - Position face in camera
   - Click "Check In" button
   - System matches face and records attendance
4. **View Check-Ins**: See recent attendance with pagination

## ⚙️ Configuration

### Application Properties

Key configurations in `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=iniperpus

# Database Configuration
spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/iniperpus_2?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect

# Python Face Service URL
face.service.url=http://localhost:8001

# DevTools (Development)
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true
server.servlet.session.persistent=true

# Security Debug (Disable in production)
spring.security.debug=true
logging.level.org.springframework.security=DEBUG
```

### Face Recognition Service Port

Change Python service port by editing uvicorn command:

```bash
uvicorn main:app --port 8001  # Change 8001 to desired port
```

Update `face.service.url` in `application.properties` accordingly.

### Database Schema Updates

For manual schema changes, edit `src/main/resources/schema.sql` and restart application or run:

```bash
mysql -u root -p iniperpus_2 < src/main/resources/schema.sql
```

## 🖼️ Screenshots

### Version Beta 0.1
![Version Beta 0.1](screenshoots/iniperpus-beta-0.1.png)

## 🔧 Troubleshooting

### Common Issues

**1. "Cannot connect to database"**
- Verify MariaDB is running
- Check database name, username, and password in `application.properties`
- Ensure database `iniperpus_2` exists

**2. "Port 8080 already in use"**
- Stop other applications using port 8080
- Or change port in `application.properties`: `server.port=8090`

**3. Python face_recognition installation fails**
- Install C++ build tools (see Prerequisites)
- On Windows, try: `pip install cmake` first
- Consider using Anaconda: `conda install -c conda-forge face_recognition`

**4. "Face not recognized" during check-in**
- Ensure student face is registered first
- Check lighting conditions (bright, even lighting recommended)
- Face should be clearly visible and frontal
- Python face service must be running

**5. Session lost after rebuild**
- DevTools session persistence only works in development mode
- Use `./gradlew bootRun` (not JAR execution)
- In production, sessions reset on restart (normal behavior)

## 📝 Development Notes

- **Dummy Data**: Auto-populated on first run only (admin user, sample books, students)
- **Database Schema**: Manually managed via `schema.sql` (`ddl-auto=none`)
- **Face Data Storage**: Stored in `python-face-service/data/` directory
  - `encodings.pkl`: Face encodings database
  - `images/`: Student face photos
- **Security**: CSRF protection enabled for all POST requests
- **Tests**: Run with `./gradlew test` or skip with `-x test`

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- Kelompok 1 - KKP S7A (Teknik Informatika)

## 🙏 Acknowledgments

- Spring Boot framework
- FastAPI framework
- face_recognition library by Adam Geitgey
- Thymeleaf template engine
- MariaDB database