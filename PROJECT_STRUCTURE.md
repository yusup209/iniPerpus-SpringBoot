# Project Structure

```
iniPerpus-SpringBoot/
├── build.gradle
├── gradlew
├── settings.gradle
├── python-face-service/
│   ├── main.py
│   ├── requirements.txt
│   └── data/
│       ├── encodings.pkl
│       └── images/
│           ├── S003.jpg
│           ├── S004.jpg
│           └── S005.jpg
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── kkp/
    │   │           └── iniperpus/
    │   │               ├── BaseController.java
    │   │               ├── IniperpusApplication.java
    │   │               ├── config/
    │   │               │   └── DataInitializer.java
    │   │               ├── controller/
    │   │               │   ├── ApiBookController.java
    │   │               │   ├── ApiBorrowerController.java
    │   │               │   ├── ApiLendingController.java
    │   │               │   ├── ApiPresenceController.java
    │   │               │   ├── ApiUserController.java
    │   │               │   ├── AuthController.java
    │   │               │   ├── DebugController.java
    │   │               │   ├── MultipartInputStreamFileResource.java
    │   │               │   └── PresenceController.java
    │   │               ├── exception/
    │   │               │   └── GlobalExceptionHandler.java
    │   │               ├── model/
    │   │               │   ├── Book.java
    │   │               │   ├── Borrower.java
    │   │               │   ├── Lending.java
    │   │               │   ├── PresenceRecord.java
    │   │               │   ├── Role.java
    │   │               │   └── User.java
    │   │               ├── repository/
    │   │               │   ├── BookRepository.java
    │   │               │   ├── BorrowerRepository.java
    │   │               │   ├── LendingRepository.java
    │   │               │   ├── PresenceRecordRepository.java
    │   │               │   ├── RoleRepository.java
    │   │               │   └── UserRepository.java
    │   │               ├── security/
    │   │               │   ├── SecurityConfig.java
    │   │               │   └── UserDetailsServiceImpl.java
    │   │               └── service/
    │   │                   ├── BookService.java
    │   │                   ├── BorrowerService.java
    │   │                   ├── LendingService.java
    │   │                   ├── PresenceService.java
    │   │                   └── UserService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── schema.sql
    │       ├── static/
    │       │   ├── script.js
    │       │   └── styles.css
    │       └── templates/
    │           ├── base.html
    │           ├── books.html
    │           ├── index.html
    │           ├── lending.html
    │           ├── login.html
    │           ├── presence.html
    │           ├── register.html
    │           └── users.html
    └── test/
        ├── java/
        │   └── com/
        │       └── kkp/
        │           └── iniperpus/
        │               └── IniperpusApplicationTests.java
        └── resources/
            └── application-test.properties
```

## Notes
This structure excludes:
- Files and folders beginning with dot (.)
- Files ending with .bat
- Markdown files (.md)
- gradle folder
- build folder
- screenshoots folder
