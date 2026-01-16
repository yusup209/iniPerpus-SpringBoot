# iniPerpus - Library Management System

Sistem manajemen perpustakaan komprehensif dengan fitur presensi siswa berbasis pengenalan wajah. Dibangun dengan Spring Boot (Java) dan FastAPI (Python).

---

*A comprehensive library management system with face recognition-based student attendance. Built with Spring Boot (Java) and FastAPI (Python).*

## 📋 Daftar Isi / Table of Contents

- [Fitur / Features](#fitur--features)
- [Stack Teknologi / Technical Stack](#-stack-teknologi--technical-stack)
- [Kebutuhan Sistem / System Requirements](#-kebutuhan-sistem--system-requirements)
- [Struktur Proyek / Project Structure](#-struktur-proyek--project-structure)
- [Instalasi & Setup / Installation & Setup](#-instalasi--setup--installation--setup)
- [Menjalankan Aplikasi / Running the Application](#-menjalankan-aplikasi--running-the-application)
- [Panduan Penggunaan / Usage Guide](#-panduan-penggunaan--usage-guide)
- [Konfigurasi / Configuration](#-konfigurasi--configuration)
- [Screenshots](#-screenshots)

## ✨ Fitur / Features

### 👥 Manajemen Siswa / Student Management
- **Tambah Siswa**: Menambahkan data siswa baru dengan informasi nama, NIS, dan kelas
- **Edit Siswa**: Memperbarui informasi siswa yang sudah ada melalui dialog modal
- **Hapus Siswa**: Menghapus siswa dengan dialog konfirmasi dan penghapusan cascade otomatis
- **Cari & Filter**: Fungsi pencarian real-time untuk semua data siswa
- **Paginasi**: Navigasi melalui daftar siswa dengan ukuran halaman yang dapat disesuaikan (5, 10, 25)
- **Tampilan Total**: Melihat jumlah total siswa yang terdaftar secara langsung
- **Integrasi Data Wajah**: Link langsung ke registrasi wajah dari data siswa
- **Aksi Berbasis Ikon**: Ikon intuitif untuk edit (✎), hapus (🗑️), dan kamera (📸) untuk aksi cepat

### 📚 Manajemen Buku / Book Management
- **Operasi CRUD Lengkap**: Tambah, lihat, edit, dan hapus data buku
- **Detail Buku**: Melacak judul, pengarang, ISBN, dan ketersediaan eksemplar
- **Pelacakan Inventori**: Memantau eksemplar tersedia vs total eksemplar untuk setiap buku
- **Ketersediaan Cerdas**: Update otomatis eksemplar tersedia saat buku dipinjam atau dikembalikan
- **Perlindungan Hapus**: Modal konfirmasi mencegah penghapusan buku secara tidak sengaja
- **Modal Edit**: Pengeditan dalam halaman tanpa gangguan navigasi
- **Sistem Paginasi**: Jelajahi koleksi buku besar secara efisien
- **Hitungan Real-time**: Menampilkan jumlah total buku di perpustakaan
- **Umpan Balik Visual**: Indikasi jelas status ketersediaan buku

### 🔄 Manajemen Peminjaman / Lending Management
- **Pinjam Buku**: Meminjamkan buku ke siswa dengan perhitungan tanggal jatuh tempo otomatis
- **Proses Pengembalian**: Tandai buku sebagai dikembalikan dan pulihkan inventori
- **Pelacakan Pinjaman Aktif**: Lihat semua buku yang sedang dipinjam dalam satu tempat
- **Manajemen Tanggal Jatuh Tempo**: Periode pinjaman 14 hari otomatis dengan tampilan tanggal jatuh tempo visual
- **Hubungan Siswa-Buku**: Menghubungkan peminjam dengan item yang dipinjam secara mulus
- **Validasi**: Mencegah peminjaman saat tidak ada eksemplar tersedia
- **Update Status**: Refleksi real-time status peminjaman di seluruh sistem
- **Dukungan Paginasi**: Menangani riwayat peminjaman besar secara efisien
- **Tampilan Total Pinjaman**: Ringkasan cepat transaksi peminjaman aktif

### 📸 Presensi Siswa (Pengenalan Wajah) / Student Presence (Face Recognition)
- **Integrasi Kamera**: Akses webcam langsung untuk menangkap wajah
- **Pendaftaran Wajah**: Mendaftarkan wajah siswa yang terhubung dengan data siswa mereka
- **Pencocokan Wajah**: Pengenalan wajah real-time untuk check-in otomatis
- **Pencatatan Check-in**: Pencatatan kehadiran otomatis dengan timestamp
- **Pendaftaran Ulang**: Memperbarui data wajah untuk siswa yang sudah ada
- **Kepercayaan Pencocokan**: Threshold 0.6 untuk pencocokan wajah yang akurat
- **Check-in Terbaru**: Lihat catatan kehadiran terbaru dengan detail siswa
- **Paginasi**: Navigasi melalui riwayat kehadiran
- **Umpan Balik Visual**: Pesan sukses/error yang jelas dengan alert berkode warna
- **Toggle Kamera**: Mulai/hentikan kamera dengan satu klik tombol
- **Video Responsif**: Ukuran feed kamera adaptif untuk berbagai ukuran layar
- **Pembersihan Data Wajah**: Penghapusan otomatis encoding wajah saat siswa dihapus
- **Integrasi Layanan Python**: Microservice terpisah untuk pemrosesan wajah

### 🔐 Keamanan & Autentikasi / Security & Authentication
- **Login Pengguna**: Autentikasi berbasis form yang aman dengan Spring Security
- **Konfirmasi Logout**: Dialog modal mencegah logout tidak sengaja
- **Manajemen Sesi**: Sesi persisten selama development
- **Perlindungan CSRF**: Perlindungan Cross-Site Request Forgery bawaan
- **Enkripsi Password**: Penyimpanan password aman dengan BCrypt
- **Akses Berbasis Role**: Diferensiasi role admin dan user
- **Logout Otomatis**: Pemutusan sesi yang bersih dengan konfirmasi
- **Alert Gaya Bootstrap**: Pesan error dan sukses yang user-friendly dengan ikon

### 🎨 Antarmuka Pengguna / User Interface
- **Desain Glassmorphism**: Estetika kaca buram modern dengan backdrop blur
- **Background Animasi**: Transisi warna gradien yang mulus
- **Layout Responsif**: Menyesuaikan dengan desktop, tablet, dan layar mobile
- **Dialog Modal**: Konfirmasi edit dan hapus yang tidak mengganggu
- **Paginasi Client-side**: Navigasi cepat tanpa reload halaman
- **Update Real-time**: Refleksi perubahan instan di seluruh antarmuka
- **Ikon Intuitif**: Indikator aksi visual untuk UX yang lebih baik
- **Alert Berkode Warna**: Styling pesan sukses (hijau), error (merah), info (biru)
- **Loading States**: Umpan balik pengguna selama operasi asinkron
- **Styling Konsisten**: Bahasa desain terpadu di seluruh halaman

### 🛠 Manajemen Data / Data Management
- **Penghapusan Cascade**: Pembersihan otomatis record terkait (peminjaman, presensi, data wajah)
- **Constraint Foreign Key**: Integritas database dengan ON DELETE CASCADE
- **Seeding Data Awal**: Pembuatan otomatis user admin dan data sampel saat pertama kali dijalankan
- **Inisialisasi Idempotent**: Data dummy hanya diisi sekali
- **RESTful API**: Endpoint API berbasis JSON untuk semua operasi CRUD
- **Validasi**: Validasi input server-side dan client-side
- **Penanganan Error**: Pesan error yang elegan dan recovery
- **Manajemen Skema Database**: Skema berbasis SQL dengan kontrol manual

### 🚀 Fitur Development / Development Features
- **Hot Reload**: Integrasi DevTools untuk restart aplikasi otomatis
- **Live Reload**: Auto-refresh browser saat ada perubahan kode
- **Persistensi Sesi**: Mempertahankan state login selama restart development
- **Debug Logging**: Logging komprehensif untuk security dan query JPA
- **Konfigurasi Test**: Database H2 in-memory terpisah untuk testing
- **Gradle Wrapper**: Environment build yang konsisten di semua mesin
- **Python Virtual Environment**: Dependensi terisolasi untuk layanan wajah
- **Dokumentasi API**: Dokumen Swagger/OpenAPI untuk layanan Python

### 📚 Book Management
- **Complete CRUD Operations**: Add, view, edit, and delete book records
- **Book Details**: Track title, author, ISBN, and copy availability
- **Inventory Tracking**: Monitor available copies vs. total copies for each book
- **Smart Availability**: Automatic update of available copies when books are lent or returned
- **Delete Protection**: Confirmation modal prevents accidental book deletion
- **Edit Modal**: In-page editing without navigation disruption
- **Pagination System**: Browse through large book collections efficiently
- **Real-time Count**: Display total number of books in the library
- **Visual Feedback**: Clear indication of book availability status

### 🔄 Lending Management
- **Lend Books**: Issue books to students with automatic due date calculation
- **Return Processing**: Mark books as returned and restore inventory
- **Active Loans Tracking**: View all currently borrowed books in one place
- **Due Date Management**: Automatic 14-day loan period with visual due date display
- **Student-Book Linking**: Connect borrowers with borrowed items seamlessly
- **Validation**: Prevent lending when no copies are available
- **Status Updates**: Real-time reflection of lending status across the system
- **Pagination Support**: Handle large lending histories efficiently
- **Total Loans Display**: Quick overview of active lending transactions

### 📸 Student Presence (Face Recognition)
- **Camera Integration**: Live webcam access for face capture
- **Face Enrollment**: Register student faces linked to their student records
- **Face Matching**: Real-time face recognition for automatic check-ins
- **Check-in Recording**: Automatic attendance logging with timestamps
- **Re-registration**: Update face data for existing students
- **Match Confidence**: 0.6 threshold for accurate face matching
- **Recent Check-ins**: View latest attendance records with student details
- **Pagination**: Navigate through attendance history
- **Visual Feedback**: Clear success/error messages with color-coded alerts
- **Camera Toggle**: Start/stop camera with single button click
- **Responsive Video**: Adaptive camera feed sizing for different screen sizes
- **Face Data Cleanup**: Automatic removal of face encodings when students are deleted
- **Python Service Integration**: Separate microservice for face processing

### 🔐 Security & Authentication
- **User Login**: Secure form-based authentication with Spring Security
- **Logout Confirmation**: Modal dialog prevents accidental logout
- **Session Management**: Persistent sessions during development
- **CSRF Protection**: Built-in Cross-Site Request Forgery protection
- **Password Encryption**: Secure password storage with BCrypt
- **Role-based Access**: Admin and user role differentiation
- **Automatic Logout**: Clean session termination with confirmation
- **Bootstrap-style Alerts**: User-friendly error and success messages with icons

### 🎨 User Interface
- **Glassmorphism Design**: Modern frosted glass aesthetic with backdrop blur
- **Animated Background**: Smooth gradient color transitions
- **Responsive Layout**: Adapts to desktop, tablet, and mobile screens
- **Modal Dialogs**: Non-intrusive edit and delete confirmations
- **Client-side Pagination**: Fast navigation without page reloads
- **Real-time Updates**: Instant reflection of changes across the interface
- **Intuitive Icons**: Visual action indicators for better UX
- **Color-coded Alerts**: Success (green), error (red), info (blue) message styling
- **Loading States**: User feedback during asynchronous operations
- **Consistent Styling**: Unified design language across all pages

### 🛠 Data Management
- **Cascade Deletion**: Automatic cleanup of related records (lending, presence, face data)
- **Foreign Key Constraints**: Database integrity with ON DELETE CASCADE
- **Initial Data Seeding**: Automatic creation of admin user and sample data on first run
- **Idempotent Initialization**: Dummy data populated only once
- **RESTful API**: JSON-based API endpoints for all CRUD operations
- **Validation**: Server-side and client-side input validation
- **Error Handling**: Graceful error messages and recovery
- **Database Schema Management**: SQL-based schema with manual control

### 🚀 Development Features
- **Hot Reload**: DevTools integration for automatic application restart
- **Live Reload**: Browser auto-refresh on code changes
- **Session Persistence**: Maintain login state during development restarts
- **Debug Logging**: Comprehensive security and JPA query logging
- **Test Configuration**: Separate H2 in-memory database for testing
- **Gradle Wrapper**: Consistent build environment across machines
- **Python Virtual Environment**: Isolated dependencies for face service
- **API Documentation**: Swagger/OpenAPI docs for Python service

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

1. **Add Student**: Fill form with Name, Borrower (User) ID, and Class
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

## ☁️ Deployment Cloud & Keamanan / Cloud Deployment & Security

### Opsi Deployment Cloud / Cloud Deployment Options

#### 1. **AWS (Amazon Web Services)**

**Spring Boot Application:**
- **AWS Elastic Beanstalk**: Deploy JAR file with auto-scaling
- **AWS EC2**: Full control with Ubuntu/Amazon Linux instance
- **AWS ECS/Fargate**: Docker containerization
- **Amazon RDS for MariaDB**: Managed database service

**Python Face Service:**
- **AWS Lambda + API Gateway**: Serverless (requires optimization)
- **AWS EC2**: Traditional VM deployment
- **AWS ECS**: Docker container deployment

**Storage:**
- **Amazon S3**: Store face encodings and images

**Estimasi Biaya / Estimated Cost:** $20-50/bulan untuk small-medium traffic

---

#### 2. **Google Cloud Platform (GCP)**

**Spring Boot Application:**
- **Google App Engine**: Managed platform with auto-scaling
- **Google Compute Engine**: VM instances
- **Google Kubernetes Engine (GKE)**: Container orchestration

**Python Face Service:**
- **Cloud Run**: Serverless container platform (recommended)
- **Compute Engine**: Traditional VM
- **Cloud Functions**: Serverless (with limitations)

**Database:**
- **Cloud SQL for MySQL/MariaDB**: Fully managed

**Storage:**
- **Cloud Storage**: Object storage for face data

**Estimasi Biaya / Estimated Cost:** $25-60/bulan

---

#### 3. **Microsoft Azure**

**Spring Boot Application:**
- **Azure App Service**: PaaS with Java support
- **Azure Virtual Machines**: Full control
- **Azure Container Instances**: Containerized deployment

**Python Face Service:**
- **Azure Functions**: Serverless
- **Azure Container Instances**: Simple containers
- **Azure App Service**: Web apps for containers

**Database:**
- **Azure Database for MySQL**: Managed service

**Storage:**
- **Azure Blob Storage**: File storage

**Estimasi Biaya / Estimated Cost:** $30-70/bulan

---

#### 4. **DigitalOcean** (Recommended untuk Budget)

**Deployment:**
- **Droplets**: Simple VPS ($6-12/month)
- **App Platform**: PaaS untuk Spring Boot dan Python
- **Managed Databases**: MariaDB/MySQL

**Kelebihan:**
- Harga terjangkau
- Setup sederhana
- Dokumentasi lengkap

**Estimasi Biaya / Estimated Cost:** $12-30/bulan

---

#### 5. **Heroku**

**Spring Boot:** Heroku Java buildpack
**Python Service:** Heroku Python buildpack
**Database:** Heroku Postgres atau JawsDB MySQL

**Catatan:** Free tier dihapus, mulai dari $7/dyno/bulan

---

### 🔒 Konfigurasi Keamanan Produksi / Production Security Configuration

#### 1. Application Properties untuk Production

Buat `application-prod.properties`:

```properties
# Nonaktifkan debug mode / Disable debug mode
spring.security.debug=false
logging.level.org.springframework.security=WARN

# Nonaktifkan DevTools / Disable DevTools
spring.devtools.restart.enabled=false
spring.devtools.livereload.enabled=false

# Jangan tampilkan SQL query / Don't show SQL queries
spring.jpa.show-sql=false

# SSL/HTTPS (jika menggunakan sertifikat sendiri)
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12

# Session timeout (30 menit)
server.servlet.session.timeout=30m

# Cookie security
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.same-site=strict

# Compression
server.compression.enabled=true

# Database connection pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.connection-timeout=30000
```

#### 2. Keamanan Web / Web Security Configuration

**Update `SecurityConfig.java` atau buat `ProductionSecurityConfig.java`:**

```java
@Configuration
@Profile("prod")
public class ProductionSecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // HTTPS Only
            .requiresChannel(channel -> channel
                .anyRequest().requiresSecure()
            )
            // Security Headers
            .headers(headers -> headers
                .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline';")
                .xssProtection()
                .frameOptions().deny()
                .httpStrictTransportSecurity(hsts -> hsts
                    .maxAgeInSeconds(31536000)
                    .includeSubDomains(true)
                )
            )
            // CSRF Protection (sudah enabled)
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            // Rate Limiting (gunakan library tambahan)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            );
        
        return http.build();
    }
}
```

#### 3. Firewall & Network Security

**Konfigurasi Server:**
```bash
# UFW Firewall (Ubuntu)
sudo ufw enable
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 80/tcp    # HTTP
sudo ufw allow 443/tcp   # HTTPS
sudo ufw deny 8080/tcp   # Block direct access to Spring Boot
sudo ufw deny 8001/tcp   # Block direct Python service access

# Gunakan Nginx sebagai reverse proxy
```

**Nginx Configuration:**
```nginx
server {
    listen 80;
    server_name your-domain.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name your-domain.com;

    # SSL Certificate (Let's Encrypt)
    ssl_certificate /etc/letsencrypt/live/your-domain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/your-domain.com/privkey.pem;
    
    # Security headers
    add_header X-Frame-Options "DENY";
    add_header X-Content-Type-Options "nosniff";
    add_header X-XSS-Protection "1; mode=block";
    add_header Strict-Transport-Security "max-age=31536000; includeSubDomains";
    
    # Rate limiting
    limit_req_zone $binary_remote_addr zone=login_limit:10m rate=5r/m;
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    location /login {
        limit_req zone=login_limit burst=3 nodelay;
        proxy_pass http://localhost:8080/login;
    }
    
    # Python face service (internal only)
    location /api/presence/ {
        proxy_pass http://localhost:8001/;
        proxy_set_header Host $host;
    }
}
```

#### 4. Database Security

```properties
# Gunakan user database terbatas / Use limited database user
spring.datasource.username=iniperpus_app
spring.datasource.password=${DB_PASSWORD}  # Dari environment variable

# SSL Connection
spring.datasource.url=jdbc:mariadb://your-db-host:3306/iniperpus_2?useSSL=true&requireSSL=true&serverTimezone=UTC
```

**Create Limited Database User:**
```sql
CREATE USER 'iniperpus_app'@'%' IDENTIFIED BY 'strong_password_here';
GRANT SELECT, INSERT, UPDATE, DELETE ON iniperpus_2.* TO 'iniperpus_app'@'%';
FLUSH PRIVILEGES;
```

#### 5. Environment Variables (Jangan hardcode secrets!)

```bash
# .env atau systemd service file
export DB_PASSWORD="your_secure_password"
export FACE_SERVICE_URL="http://localhost:8001"
export SSL_KEYSTORE_PASSWORD="keystore_password"
export SPRING_PROFILES_ACTIVE="prod"
```

#### 6. Dependency Security

**Scan dependencies untuk vulnerability:**
```bash
# Gradle
./gradlew dependencyCheckAnalyze

# Python
pip install safety
safety check -r requirements.txt
```

#### 7. Monitoring & Logging

**Spring Boot Actuator (production-safe):**
```properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=when-authorized
management.endpoints.web.base-path=/actuator
```

**Logging Configuration:**
```properties
# Log ke file / Log to file
logging.file.name=/var/log/iniperpus/application.log
logging.file.max-size=10MB
logging.file.max-history=30

# Log security events
logging.level.org.springframework.security=INFO
```

#### 8. Backup Strategy

```bash
# Automated daily backup script
#!/bin/bash
BACKUP_DIR="/backup/iniperpus"
DATE=$(date +%Y%m%d_%H%M%S)

# Database backup
mysqldump -u root -p iniperpus_2 > "$BACKUP_DIR/db_$DATE.sql"

# Face data backup
tar -czf "$BACKUP_DIR/faces_$DATE.tar.gz" /path/to/python-face-service/data/

# Keep only last 30 days
find $BACKUP_DIR -type f -mtime +30 -delete
```

#### 9. Docker Deployment (Recommended)

**docker-compose.yml:**
```yaml
version: '3.8'

services:
  mariadb:
    image: mariadb:10.11
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
      MYSQL_DATABASE: iniperpus_2
      MYSQL_USER: iniperpus_app
      MYSQL_PASSWORD: ${DB_PASSWORD}
    volumes:
      - db_data:/var/lib/mysql
    networks:
      - iniperpus-network
    restart: unless-stopped

  spring-app:
    build: .
    environment:
      SPRING_PROFILES_ACTIVE: prod
      DB_PASSWORD: ${DB_PASSWORD}
      FACE_SERVICE_URL: http://face-service:8001
    depends_on:
      - mariadb
      - face-service
    networks:
      - iniperpus-network
    restart: unless-stopped

  face-service:
    build: ./python-face-service
    volumes:
      - face_data:/app/data
    networks:
      - iniperpus-network
    restart: unless-stopped

  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
      - /etc/letsencrypt:/etc/letsencrypt
    depends_on:
      - spring-app
    networks:
      - iniperpus-network
    restart: unless-stopped

volumes:
  db_data:
  face_data:

networks:
  iniperpus-network:
    driver: bridge
```

#### 10. Checklist Keamanan Sebelum Production

- [ ] Nonaktifkan debug mode dan security debug
- [ ] Gunakan HTTPS dengan sertifikat SSL valid (Let's Encrypt)
- [ ] Set semua password via environment variables
- [ ] Aktifkan firewall dan tutup port yang tidak perlu
- [ ] Implementasi rate limiting untuk login
- [ ] Set session timeout yang wajar (30 menit)
- [ ] Enable security headers (CSP, HSTS, X-Frame-Options)
- [ ] Gunakan reverse proxy (Nginx/Apache)
- [ ] Buat database user dengan privilege terbatas
- [ ] Setup automated backup (database + face data)
- [ ] Implementasi monitoring dan alerting
- [ ] Update dependencies secara berkala
- [ ] Set log rotation untuk mencegah disk penuh
- [ ] Dokumentasikan incident response plan

### Rekomendasi Deployment untuk Pemula / Beginner Recommendation

**Option 1: DigitalOcean Droplet + Docker Compose**
- Paling mudah dan terjangkau
- Kontrol penuh atas environment
- Biaya: ~$12-24/bulan

**Option 2: Google Cloud Run + Cloud SQL**
- Auto-scaling otomatis
- Pay-per-use (murah untuk traffic rendah)
- Setup lebih kompleks

**Option 3: AWS Elastic Beanstalk + RDS**
- Managed platform
- Easy deployment via CLI
- Free tier tersedia (1 tahun)

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