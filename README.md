# 🏆 Bidding System

A full-featured online bidding/auction platform built with Spring Boot. Users can browse shops by category, place bids in real-time, and compete to win auctions.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [Project Structure](#-project-structure)
- [User Roles](#-user-roles)
- [API Endpoints](#-api-endpoints)
- [Contributing](#-contributing)

---

## ✨ Features

### For Users
- 🔐 User registration and secure login
- 🏪 Browse shops by categories (Food, Education, Sports, Toys, etc.)
- 💰 Place bids on active auctions
- ⚡ Real-time bid updates via WebSocket
- 📊 View bid history and track your bids
- 🏆 Winner announcements and notifications
- 👤 User profile management

### For Admins
- 📁 Category management (Add, Edit, Delete)
- 🏬 Shop management with multiple images
- 📅 Set bidding start and end dates
- 💵 Set minimum bid amounts
- 📈 View all bid history
- 🎯 Manage and announce winners

### For Master Admin
- 👨‍💼 Create and manage admin accounts
- 🔒 Full system control

---

## 🛠 Tech Stack

| Technology | Description |
|------------|-------------|
| **Spring Boot 4.0.0** | Backend framework |
| **Spring Security** | Authentication & Authorization |
| **Spring Data JPA** | Database ORM |
| **Spring WebSocket** | Real-time bidding updates |
| **Spring Mail** | Email notifications |
| **Thymeleaf** | Server-side template engine |
| **MySQL** | Relational database |
| **Lombok** | Reduce boilerplate code |
| **Maven** | Dependency management |

---

## 📌 Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java 21** or higher - [Download](https://www.oracle.com/java/technologies/downloads/#java21)
- 🗄️ **MySQL 8.0** or higher - [Download](https://dev.mysql.com/downloads/mysql/)
- 📦 **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- 💻 **IDE** (Recommended: IntelliJ IDEA, Eclipse, or Spring Tool Suite)

---

## 🚀 Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/Badding_System.git
cd Badding_System
```

### Step 2: Create MySQL Database

Open MySQL and run:

```sql
CREATE DATABASE badding_system_db;
```

### Step 3: Configure Application Properties

Navigate to `src/main/resources/application.properties` and update the following:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/badding_system_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# Email Configuration (for notifications)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.from=your-email@gmail.com
```

> ⚠️ **Note for Gmail Users:** You need to generate an [App Password](https://support.google.com/accounts/answer/185833) instead of using your regular password.

### Step 4: Install Dependencies

```bash
mvn clean install
```

---

## ⚙️ Configuration

### Database Configuration

| Property | Description | Example |
|----------|-------------|---------|
| `spring.datasource.url` | MySQL connection URL | `jdbc:mysql://localhost:3306/badding_system_db` |
| `spring.datasource.username` | Database username | `root` |
| `spring.datasource.password` | Database password | `yourpassword` |

### Email Configuration (Optional)

| Property | Description | Example |
|----------|-------------|---------|
| `spring.mail.host` | SMTP server host | `smtp.gmail.com` |
| `spring.mail.port` | SMTP server port | `587` |
| `spring.mail.username` | Email address | `your-email@gmail.com` |
| `spring.mail.password` | App password | `xxxx-xxxx-xxxx-xxxx` |

### Server Configuration

| Property | Description | Default |
|----------|-------------|---------|
| `server.port` | Application port | `9097` |

---

## ▶️ Running the Application

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Using JAR file

```bash
mvn clean package
java -jar target/Badding_System-0.0.1-SNAPSHOT.jar
```

### Option 3: Using IDE

1. Open the project in your IDE
2. Navigate to `BaddingSystemApplication.java`
3. Right-click and select **Run**

Once started, open your browser and go to:

```
http://localhost:9097/user/home
```

---

## 📁 Project Structure

```
Badding_System/
├── src/
│   ├── main/
│   │   ├── java/com/baddingSystem/
│   │   │   ├── BaddingSystemApplication.java    # Main Application
│   │   │   ├── config/                          # Security & WebSocket Config
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── WebSocketConfig.java
│   │   │   ├── controller/                      # REST Controllers
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── BidController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── MasterAdminController.java
│   │   │   │   ├── ShopController.java
│   │   │   │   └── UserController.java
│   │   │   ├── entities/                        # JPA Entities
│   │   │   │   ├── Admin.java
│   │   │   │   ├── Bid.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── Shop.java
│   │   │   │   ├── User.java
│   │   │   │   └── Winner.java
│   │   │   ├── repository/                      # Data Repositories
│   │   │   ├── services/                        # Business Logic
│   │   │   └── util/                            # Utility Classes
│   │   └── resources/
│   │       ├── application.properties           # App Configuration
│   │       ├── static/                          # Static Assets (Images)
│   │       │   ├── categoryImg/
│   │       │   ├── hero-section/
│   │       │   └── shopImg/
│   │       └── templates/                       # Thymeleaf Templates
│   │           ├── admin/                       # Admin Pages
│   │           └── user/                        # User Pages
│   └── test/                                    # Unit Tests
├── pom.xml                                      # Maven Dependencies
└── README.md
```

---

## 👥 User Roles

### 1. User (Regular)
- Register and login
- Browse categories and shops
- Place bids on auctions
- View bid history
- Check winner status

### 2. Admin
- Manage categories (CRUD)
- Manage shops (CRUD)
- Set auction dates and minimum bids
- View all bids
- Announce winners

### 3. Master Admin
- All Admin privileges
- Create/manage admin accounts
- System administration

---

## 🔗 API Endpoints

### Public Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/home` | Home page with categories |
| GET | `/user/shop` | View all shops |
| GET | `/user/shop/{id}` | View shop details |
| GET | `/user/category/{id}` | View shops by category |

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/login` | User login page |
| POST | `/user/register` | User registration |
| GET | `/user/profile` | User profile |
| GET | `/user/my-bids` | User's bid history |
| POST | `/bid/place` | Place a bid |

### Admin Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/dashboard` | Admin dashboard |
| GET | `/admin/category` | Manage categories |
| POST | `/admin/category/add` | Add category |
| GET | `/admin/shop` | Manage shops |
| POST | `/admin/shop/add` | Add shop |
| GET | `/admin/bid-history` | View all bids |

---

---

## 🔧 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Verify database credentials in `application.properties`
   - Check if database `badding_system_db` exists

2. **Port Already in Use**
   - Change `server.port` in `application.properties`
   - Or kill the process using port 9097

3. **Email Not Sending**
   - Verify SMTP credentials
   - For Gmail, use App Password instead of regular password
   - Enable "Less secure app access" or use OAuth2

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---


---

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/satyampatel9302)

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Thymeleaf Documentation
- MySQL Documentation

---

⭐ **If you found this project helpful, please give it a star!** ⭐

