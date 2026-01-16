# iniPerpus - Library Management System

**A Comprehensive Library Management System with Face Recognition-Based User Attendance**

iniPerpus is a modern, feature-rich library management system built with Spring Boot and Python FastAPI. It combines traditional library operations with cutting-edge face recognition technology for seamless user attendance tracking.

---

## 📋 Table of Contents

- [Overview](#overview)
- [🚀 Beginner's Quick Start](#beginners-quick-start)
- [Key Features](#key-features)
- [Feature Guides](#feature-guides)
- [Complete Workflow Example](#complete-workflow-example)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)

---

## 🎯 Overview

iniPerpus simplifies library management for educational institutions by providing:

- **Efficient User Management**: Maintain comprehensive user records with face recognition integration
- **Book Inventory Control**: Track all books, copies, and availability status
- **Lending Management**: Handle book borrowing and returns with automatic due date tracking
- **Smart Attendance**: Automatic check-in using facial recognition technology
- **Secure Access**: Role-based authentication and authorization
- **Beautiful UI**: Modern glassmorphism design with responsive layouts

### Technology Stack

| Component | Technology |
|-----------|------------|
| Backend | Spring Boot 4.0.0, Java 25 |
| Database | MariaDB 10.x+ |
| Frontend | Thymeleaf, JavaScript ES6+, CSS3 |
| Face Recognition | Python FastAPI, face_recognition library |
| Build Tool | Gradle |

---

## 🚀 Beginner's Quick Start

### First Time Setup (Do This Once)

#### Step 1: Install Required Software

**1.1. Install Java Development Kit (JDK)**
- Download JDK 25 from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
- Run the installer and follow the on-screen instructions
- Verify installation by opening Terminal/Command Prompt and typing:
  ```bash
  java -version
  ```
- You should see Java version 25 displayed

**1.2. Install MariaDB Database**
- Download MariaDB from [mariadb.org](https://mariadb.org/download/)
- During installation, remember the **root password** you set
- Start MariaDB service after installation

**1.3. Install Python**
- Download Python 3.8+ from [python.org](https://www.python.org/downloads/)
- **Important**: Check "Add Python to PATH" during installation
- Verify by typing in terminal:
  ```bash
  python --version
  ```

**1.4. Install Git**
- Download from [git-scm.com](https://git-scm.com/downloads)
- Install with default settings

#### Step 2: Download the Project

```bash
# Open Terminal/Command Prompt and run:
git clone <repository-url>
cd iniPerpus-SpringBoot
```

#### Step 3: Setup Database

**3.1. Open MariaDB Command Line**
- Windows: Search "MySQL Client" or "MariaDB" in Start Menu
- Mac/Linux: Open Terminal and type `mysql -u root -p`
- Enter your root password

**3.2. Create Database**
```sql
CREATE DATABASE iniperpus_2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

**3.3. Configure Database Connection**
- Open file: `src/main/resources/application.properties`
- Find and update these lines:
  ```properties
  spring.datasource.username=root
  spring.datasource.password=YOUR_PASSWORD_HERE
  ```
- Replace `YOUR_PASSWORD_HERE` with your MariaDB password
- Save the file

#### Step 4: Setup Face Recognition Service

**4.1. Navigate to Python Service Folder**
```bash
cd python-face-service
```

**4.2. Create Virtual Environment**
```bash
# On Windows:
python -m venv venv
venv\Scripts\activate

# On Mac/Linux:
python3 -m venv venv
source venv/bin/activate
```

**4.3. Install Dependencies**
```bash
pip install -r requirements.txt
```
*Note: This may take 5-10 minutes*

**4.4. Start Face Recognition Service**
```bash
python main.py
```
- You should see: `Uvicorn running on http://127.0.0.1:5000`
- **Keep this terminal window open**

#### Step 5: Start the Main Application

**5.1. Open a NEW Terminal/Command Prompt**
- Navigate to project folder:
  ```bash
  cd iniPerpus-SpringBoot
  ```

**5.2. Run the Application**
```bash
# On Windows:
gradlew.bat bootRun

# On Mac/Linux:
./gradlew bootRun
```

**5.3. Wait for Startup**
- First time may take 2-3 minutes
- Look for message: `Started IniperpusApplication`
- Application is ready when you see: `Tomcat started on port 8080`

#### Step 6: Access the Application

**6.1. Open Your Web Browser**
- Go to: `http://localhost:8080`

**6.2. Login**
- **Username**: `admin`
- **Password**: `admin`
- Click "Login"

🎉 **Congratulations! You're now in the system!**

---

### Your First Tasks (Follow in Order)

#### Task 1: Add Your First User (3 minutes)

1. **Click "Users"** in the top menu
2. **Click "Add User"** button (top right)
3. **Fill in the form**:
   - Name: `John Doe`
   - NIS: `12345`
   - Class: `10A`
4. **Click "Save"**
5. ✅ You should see John Doe in the list!

#### Task 2: Register User's Face (2 minutes)

1. **Click "Presence"** in the top menu
2. **Click "Register Face"** button
3. **Click the Camera Icon** 📷
4. **Allow camera access** when browser asks
5. **Select "John Doe"** from dropdown
6. **Position your face** in front of camera
   - Make sure face is well-lit
   - Look directly at camera
   - Remove glasses if possible
7. **Click "Capture & Register"**
8. ✅ Wait for success message!

#### Task 3: Add Your First Book (2 minutes)

1. **Click "Books"** in the top menu
2. **Click "Add Book"** button
3. **Fill in the form**:
   - Title: `Harry Potter and the Sorcerer's Stone`
   - Author: `J.K. Rowling`
   - ISBN: `978-0439708180`
   - Total Copies: `5`
4. **Click "Save"**
5. ✅ Book added! Available copies: 5/5

#### Task 4: Lend a Book (2 minutes)

1. **Click "Lending"** in the top menu
2. **Click "Add Lending"** button
3. **Fill in the form**:
   - Select Student: `John Doe`
   - Select Book: `Harry Potter...`
   - Lending Date: Today's date (auto-filled)
4. **Click "Save"**
5. ✅ Book lent! Check Books page - Available copies now: 4/5

#### Task 5: Test Face Recognition (1 minute)

1. **Click "Presence"** in the top menu
2. **Click "Start Camera"** button
3. **Show your face** to the camera
4. ✅ System should recognize you automatically!
5. **Check "Recent Check-ins"** below to see your attendance record

---

### Daily Usage (Quick Reference)

**Starting the Application:**
1. Start MariaDB service (if not auto-start)
2. Open Terminal → `cd python-face-service` → `python main.py`
3. Open NEW Terminal → `cd iniPerpus-SpringBoot` → `./gradlew bootRun`
4. Open browser → `http://localhost:8080`
5. Login with admin/admin

**Common Tasks:**
- 📝 **Add Member**: Users → Add User → Fill form → Save
- 📸 **Register Face**: Presence → Register Face → Select user → Capture
- 📚 **Add Book**: Books → Add Book → Fill details → Save
- 📤 **Lend Book**: Lending → Add Lending → Select user & book → Save
- 📥 **Return Book**: Lending → Find record → Click Return button
- ✅ **Check Attendance**: Presence → Start Camera → Face recognized

---

### Troubleshooting for Beginners

**Problem: "Port 8080 already in use"**
- Solution: Another app is using port 8080
- Close other programs or restart your computer

**Problem: "Cannot connect to database"**
- Check MariaDB is running
- Verify password in `application.properties`
- Make sure database `iniperpus_2` exists

**Problem: "Camera not working"**
- Check browser permissions (click lock icon in address bar)
- Make sure Python service is running
- Try different browser (Chrome recommended)

**Problem: "Face not recognized"**
- Ensure good lighting (face bright, no shadows)
- Look directly at camera
- Re-register face in better conditions

**Problem: "Python dependencies failed to install"**
- Windows: Install Visual Studio Build Tools
- Mac: Install Xcode Command Line Tools: `xcode-select --install`
- Linux: Install build-essential: `sudo apt-get install build-essential`

---

## ✨ Key Features

### 👥 User Management

Manage user records efficiently with intuitive CRUD operations.

![User Management Interface](./images/user-management.png)

**Features:**
- Add new users with NIS (User ID) and class information
- Edit existing user information through modal dialogs
- Delete user records with cascade cleanup of related data
- Real-time search and filtering across all users
- Pagination with customizable page sizes (5, 10, 25 records)
- Direct integration with face recognition system
- Quick action icons for common operations

**Use Case:**
When a new user enrolls, navigate to the Users section, click "Add User", enter their details (Name, NIS, Class), and save. The system automatically prepares the user for face registration.

---

### 📚 Book Management

Maintain your library's book collection with complete inventory control.

![Book Management Interface](./images/book-management.png)

**Features:**
- Complete CRUD operations for book records
- Track essential information: Title, Author, ISBN, Copy Count
- Smart inventory management with automatic availability updates
- Real-time monitoring of available vs. total copies
- Edit books inline without leaving the page
- Delete protection with confirmation dialogs
- Efficient pagination for large collections
- Total book count display for quick statistics

**Use Case:**
To add a new book to the library, go to Books section, click "Add Book", fill in the details (Title, Author, ISBN, Total Copies), and save. When books are lent or returned, the available copy count updates automatically.

---

### 🔄 Lending Management

Track all borrowing transactions and manage due dates effortlessly.

![Lending Management Interface](./images/lending-management.png)

**Features:**
- Lend books to users with automatic due date calculation
- Process returns and automatically restore inventory
- View all active loans in one centralized location
- Automatic 14-day loan period
- Visual due date indicators
- Prevention of lending when no copies available
- Real-time status updates across the system
- Complete lending history with pagination
- Total active loans display

**Use Case:**
When a user wants to borrow a book, navigate to Lending section, click "Add Lending", select the user and book, enter the lending date. The system automatically calculates the due date (14 days later). When the user returns the book, mark it as returned and the book's availability count increases.

---

### 📸 User Presence (Face Recognition)

Automate attendance tracking with intelligent face recognition technology.

![Face Recognition Interface](./images/face-recognition.png)

**Features:**
- Live webcam integration for real-time face capture
- Register user faces linked to their profiles
- Automatic face recognition for instant check-ins
- Timestamp recording for attendance tracking
- Re-register or update existing user faces
- 0.6 confidence threshold for accurate matching
- View recent check-ins with user details
- Responsive video feed for all screen sizes
- Clear visual feedback with color-coded alerts
- Automatic cleanup of face data when users are deleted

**Use Case:**
**Registration:**
1. Go to Presence section
2. Click "Register Face"
3. Click camera icon to start
4. Select user from dropdown
5. Allow camera access when prompted
6. Position face in frame
7. Successfully registered

**Check-in:**
1. Go to Presence section
2. Click "Start Camera"
3. System automatically recognizes registered faces
4. Attendance recorded instantly with timestamp
5. View recent check-ins below

---

### 🔐 Authentication & Authorization

Secure access to the system with role-based permissions.

![Login Interface](./images/login-page.png)

**Features:**
- Secure form-based authentication
- Role-based access control (Admin, User)
- BCrypt password encryption
- Session management and persistence
- CSRF protection
- Safe logout with confirmation dialog
- Error messages with helpful guidance

**Use Case:**
1. At login page, enter username and password
2. System validates credentials securely
3. After login, role determines available features
4. Admin can manage users and system settings
5. To logout, click logout button and confirm in dialog

---

### 🎨 User Interface

Experience a modern, responsive interface designed for productivity.

![Dashboard Interface](./images/dashboard.png)

**Features:**
- **Glassmorphism Design**: Frosted glass effect with modern aesthetics
- **Animated Background**: Smooth gradient color transitions
- **Responsive Layout**: Seamless experience on desktop, tablet, and mobile
- **Modal Dialogs**: Non-intrusive confirmations for critical actions
- **Client-side Pagination**: Fast navigation without page reloads
- **Real-time Updates**: Instant reflection of changes across the interface
- **Intuitive Icons**: Clear visual indicators for actions
- **Color-coded Alerts**: 
  - 🟢 Green for success messages
  - 🔴 Red for errors
  - 🔵 Blue for information
- **Consistent Styling**: Unified design language throughout
- **Accessible Navigation**: Clear menu structure and breadcrumbs

---

## 🎓 Feature Guides

### How to Add a User

1. Click on **"Users"** in the navigation menu
2. Click the **"Add User"** button
3. Fill in the required fields:
   - **Name**: User's full name
   - **NIS**: User identification number
   - **Class**: User's class/grade
4. Click **"Save"**
5. User appears in the list and is ready for face registration

### How to Register a User's Face

1. Navigate to **"Presence"** section
2. Click **"Register Face"** button
3. Click the **Camera Icon** to activate webcam
4. Select user from the dropdown menu
5. Ensure good lighting and position face clearly in the frame
6. Click **"Capture & Register"** when ready
7. System will process and save the face encoding
8. Confirmation message will appear

### How to Lend a Book

1. Go to **"Lending"** section
2. Click **"Add Lending"** button
3. Select **User** from the dropdown
4. Select **Book** from the available books list
5. Set the **Lending Date** (usually today)
6. System automatically calculates due date (14 days)
7. Click **"Save"**
8. Book count decreases automatically in inventory

### How to Process a Book Return

1. Navigate to **"Lending"** section
2. Find the book being returned in the active loans list
3. Click the **Return** button next to the lending record
4. Confirm the return in the dialog
5. Book availability count increases automatically
6. Lending record moves to history

### How to Search and Filter

**For Users:**
1. In Users section, locate the search box
2. Type user name, NIS, or class
3. Results update in real-time
4. Clear search to see all users

**For Books:**
1. In Books section, use the search functionality
2. Enter book title, author, or ISBN
3. Matching books display instantly
4. Adjust pagination to view more results

### How to Check Attendance Records

1. Go to **"Presence"** section
2. View **"Recent Check-ins"** at the bottom
3. Each record shows:
   - User name and NIS
   - Check-in timestamp
   - Confidence level of face match
4. Use pagination to browse through history

---

## 📖 Complete Workflow Example

### Scenario: A Typical Library Day

Let's follow **Mrs. Sarah (Librarian)** through a complete day using iniPerpus.

#### 🌅 Morning - Setup (8:00 AM)

**Step 1: Start the System**
1. Turn on computer
2. Start MariaDB service
3. Open Terminal 1:
   ```bash
   cd iniPerpus-SpringBoot/python-face-service
   source venv/bin/activate  # or venv\Scripts\activate on Windows
   python main.py
   ```
4. Open Terminal 2:
   ```bash
   cd iniPerpus-SpringBoot
   ./gradlew bootRun
   ```
5. Open browser → `http://localhost:8080`
6. Login: admin / admin

✅ **System Ready!**

---

#### 👤 New Member Registration (8:30 AM)

**Emma Wilson joins the library today**

**Step 2: Add Member Record**
1. Click **"Users"** menu
2. Click **"Add User"** button
3. Enter:
   - Name: `Emma Wilson`
   - NIS: `2026001`
   - Class: `11B`
4. Click **"Save"**

**Step 3: Register Emma's Face**
1. Click **"Presence"** menu
2. Click **"Register Face"** button
3. Click camera icon 📷
4. Select **"Emma Wilson"** from dropdown
5. Ask Emma to look at camera
6. Click **"Capture & Register"**
7. Wait for green success message

✅ **Emma is now in the system!**

---

#### 📚 New Books Arrival (9:00 AM)

**Library receives 10 new books**

**Step 4: Add Books to System**
1. Click **"Books"** menu
2. For each book, click **"Add Book"**:

   **Book 1:**
   - Title: `To Kill a Mockingbird`
   - Author: `Harper Lee`
   - ISBN: `978-0061120084`
   - Total Copies: `3`
   - Click **"Save"**

   **Book 2:**
   - Title: `1984`
   - Author: `George Orwell`
   - ISBN: `978-0451524935`
   - Total Copies: `2`
   - Click **"Save"**

   *Repeat for remaining books...*

✅ **All books added! Total inventory updated.**

---

#### 📖 First Break - Book Lending (10:00 AM)

**Several members want to borrow books**

**Step 5: Lend Book to Emma**
1. Click **"Lending"** menu
2. Click **"Add Lending"** button
3. Select:
   - User: `Emma Wilson`
   - Book: `To Kill a Mockingbird`
   - Lending Date: `2026-01-15` (today)
4. System shows: Due Date: `2026-01-29` (automatically calculated)
5. Click **"Save"**

**Step 6: Lend Book to Another Member**
1. Click **"Add Lending"** again
2. Select:
   - User: `John Doe`
   - Book: `1984`
   - Lending Date: Today
3. Click **"Save"**

✅ **Books lent! Check Books page:**
- To Kill a Mockingbird: Available 2/3
- 1984: Available 1/2

---

#### ✅ Lunchtime - Attendance Tracking (12:00 PM)

**Members come to library for reading**

**Step 7: Automatic Face Recognition Check-in**
1. Click **"Presence"** menu
2. Click **"Start Camera"** button
3. Emma walks in front of camera
   - 🎯 System recognizes: "Emma Wilson"
   - ✅ Check-in recorded: 12:05 PM
4. John walks in front of camera
   - 🎯 System recognizes: "John Doe"
   - ✅ Check-in recorded: 12:06 PM

**Step 8: View Attendance**
- Scroll down to **"Recent Check-ins"**
- See list:
  - Emma Wilson (11B) - 12:05 PM - Confidence: 95%
  - John Doe (10A) - 12:06 PM - Confidence: 92%

✅ **Attendance automatically tracked!**

---

#### 📥 After School - Book Returns (3:00 PM)

**John returns his book**

**Step 9: Process Book Return**
1. Click **"Lending"** menu
2. Find **"John Doe - 1984"** in the list
3. Click **"Return"** button (↩️ icon)
4. Confirm in popup: **"Yes, Return"**

✅ **Book returned! Check Books page:**
- 1984: Available 2/2 (back to full stock)

---

#### 📊 End of Day - Reports (4:00 PM)

**Step 10: Check Statistics**

1. **Users Page**:
   - Total Members: 25
   - New Today: 1 (Emma)

2. **Books Page**:
   - Total Books: 150
   - Total Copies: 450
   - Available: 425
   - On Loan: 25

3. **Lending Page**:
   - Active Loans: 24
   - Books Due Today: 0
   - Overdue Books: 2 (need follow-up)

4. **Presence Page**:
   - Check-ins Today: 45 members
   - Peak Time: 12:00-13:00 PM

✅ **Day complete! All data saved automatically.**

---

#### 🔒 Closing (4:30 PM)

**Step 11: Shutdown System**
1. Click **"Logout"** button
2. Confirm logout
3. Close browser
4. Stop Spring Boot (Ctrl+C in Terminal 2)
5. Stop Python service (Ctrl+C in Terminal 1)

✅ **System safely closed. All data preserved for tomorrow!**

---

### 💡 Key Takeaways from This Workflow

1. **One-time setup** is required for each member (add user + register face)
2. **Books are automatically tracked** - system updates availability instantly
3. **Due dates are calculated automatically** (14 days from lending date)
4. **Face recognition works hands-free** - members just walk up to camera
5. **All data is persistent** - close and reopen anytime, data remains
6. **Changes reflect everywhere** - lend a book, availability updates on Books page

---

### 🎯 Next Steps for New Users

**Week 1: Basic Operations**
- ✅ Add 10 members
- ✅ Register all faces
- ✅ Add 20 books
- ✅ Process 5 lendings
- ✅ Test face recognition daily

**Week 2: Advanced Features**
- 📝 Edit member information
- 🔍 Use search/filter features
- 📊 Track attendance patterns
- 🔄 Process returns regularly
- 📅 Monitor due dates

**Week 3: Maintenance**
- 🗑️ Remove inactive members
- 📚 Update book inventory
- 🔄 Re-register faces if needed
- 📈 Review lending statistics
- 🎨 Customize as needed

---

## 🏗️ System Architecture

### Data Flow

```
┌─────────────────────────────────────────────────────────┐
│                    iniPerpus System                      │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────────┐          ┌────────────────────┐       │
│  │   Frontend   │          │  Spring Boot App   │       │
│  │ (Thymeleaf,  │◄────────►│  (REST API,        │       │
│  │ JavaScript)  │ AJAX/JSON│   Business Logic)  │       │
│  └──────────────┘          └────────────────────┘       │
│                                    │                     │
│                                    ▼                     │
│                            ┌─────────────────┐           │
│                            │    MariaDB      │           │
│                            │   Database      │           │
│                            └─────────────────┘           │
│                                                           │
│  ┌──────────────────────┐   ┌────────────────────┐      │
│  │   Camera/Webcam      │──►│  Python FastAPI    │      │
│  │  (Face Capture)      │   │  Face Recognition  │      │
│  └──────────────────────┘   └────────────────────┘      │
│                                    │                     │
│                                    ▼                     │
│                            ┌─────────────────┐           │
│                            │  Face Encodings │           │
│                            │  (Face Data)    │           │
│                            └─────────────────┘           │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

### Component Overview

| Component | Purpose |
|-----------|---------|
| **Frontend** | User interface built with Thymeleaf templates and vanilla JavaScript |
| **Controllers** | Handle HTTP requests and route them to appropriate services |
| **Services** | Implement business logic and orchestrate operations |
| **Repositories** | Access database through Spring Data JPA |
| **Models** | Represent database entities (User, Book, Lending, etc.) |
| **Python Service** | Process face recognition requests in isolation |
| **Database** | Store all persistent data (users, books, loans, attendance) |

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 25 or compatible
- **MariaDB**: Version 10.x or higher
- **Python**: Version 3.8 or higher
- **Git**: For cloning the repository
- **Visual Studio Code** or **IntelliJ IDEA** (recommended IDEs)

### Quick Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd iniPerpus-SpringBoot
   ```

2. **Configure Database**
   ```sql
   CREATE DATABASE iniperpus_2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
   Update `application.properties` with your database credentials

3. **Setup Python Face Service**
   ```bash
   cd python-face-service
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   pip install -r requirements.txt
   python main.py
   ```

4. **Run Spring Boot Application**
   ```bash
   ./gradlew bootRun
   ```
   Application will be available at `http://localhost:8080`

5. **Login**
   - Default Admin: `admin` / `admin`
   - Create additional users as needed

### Troubleshooting

**Issue**: Camera not working in Presence section
- **Solution**: Ensure browser has camera permissions enabled and Python service is running

**Issue**: Face recognition not matching
- **Solution**: Register face in good lighting, ensure face is clearly visible, re-register if needed

**Issue**: Book lend fails
- **Solution**: Ensure user and book are selected, and available copies > 0

---

## 📊 Real-World Scenarios

### Scenario 1: School Opening Day

1. Admin logs in and adds all users from enrollment list
2. Registers each user's face in the Presence section
3. Teachers can now use face recognition for attendance
4. Library books are added to the system with inventory counts
5. Users can borrow books by asking librarian

### Scenario 2: Weekly Book Circulation

- Monday: Users borrow books (lending records created)
- Wednesday: Face recognition checks attendance
- Friday: Some books are returned (inventory updated)
- System tracks overdue books automatically via due dates

### Scenario 3: User Transfer

1. User's original data is deleted from system
2. Face encodings automatically removed
3. All lending records cascade deleted
4. Attendance history preserved in database
5. User's data can be re-added if needed

---

## 💡 Best Practices

### User Management
- Keep user information up-to-date
- Register faces immediately after user enrollment
- Re-register face annually or if recognition fails frequently

### Book Management
- Maintain accurate copy counts
- Set realistic total copy counts for inventory planning
- Regularly review ISBN entries for accuracy

### Lending Operations
- Process returns promptly to update availability
- Monitor due dates to prevent overdue books
- Follow up on unreturned books after due date

### Face Recognition
- Register faces in well-lit areas
- Ensure natural lighting (avoid backlighting)
- Register only one user face at a time
- Update registrations if user appearance changes significantly

---

## 📞 Support & Feedback

For issues, suggestions, or feature requests:
- Check the main README.md for detailed technical documentation
- Review application logs for error messages
- Ensure all services (Spring Boot + Python) are running

---

## 📄 License

This project is provided as-is for educational purposes.

---

**Last Updated**: January 2026
**Version**: 1.0
