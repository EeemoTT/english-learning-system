# 📚 English Learning System

> A full-featured AI-powered English learning platform built with **Spring Boot + Vue2**. It integrates Baidu ERNIE Bot for AI conversation practice, a complete vocabulary memorization system, online exams, a community forum, and a data analytics dashboard.

🌐 **Live Demo**: [http://54.66.160.70](http://54.66.160.70) *(may be offline when not in use)*

---

## ✨ Features

### 🤖 AI Conversation Practice
- Real-time AI dialogue powered by **Baidu ERNIE Bot** (文心一言) via SSE streaming
- Interactive English tutoring with natural, flowing responses
- Dedicated `InteractiveStudy` page for immersive practice sessions

### 📖 Vocabulary Learning
- Select from multiple word books (`BookChoosing`)
- Track memorization progress with `MemoryRecord`
- Daily word statistics and review history (`StatisticWord`)

### 📝 Online Exam System
- Take structured papers and tests (`Paper`, `TestPaper`)
- Question bank management with multiple question types (`Question`, `PaperQuestion`)
- Auto-grading and score recording (`UserPaper`)
- Personal score history and analysis (`PersonalScore`, `ScoreAnalysis`)

### 🎓 Courses
- Browse and view structured English courses (`Course`, `CourseDetail`)
- Rich content powered by WangEditor

### 💬 Community Forum
- Post and browse community discussions (`Community`)
- Comment and interact with other learners (`Comment`)
- Edit and manage your own posts (`CommunityChange`)

### 📊 Learning Analytics
- Visual learning statistics with **ECharts** (`ScoreAnalysis`)
- Daily learning records and streak tracking (`DateRecord`, `SignIn`)
- Personal learning goal setting (`Target`)

### ❤️ Personal Center
- Manage profile, favorites, and learning history (`PersonCenter`, `PersonalFavor`)
- Change password, update personal info
- View all exam history and scores

### 🛠️ Admin Dashboard
- Full user management (CRUD, role control)
- Course, paper, and question bank management
- System announcements (`Notice`)
- Platform-wide statistics overview
- Excel data import/export (Apache POI)

---

## 🖼️ Screenshots

- https://www.youtube.com/watch?v=I86yY6L0d1Q

---

## 🗂️ Additional Resources

### 📄 Database Schema
The `sql/schema.sql` file contains all table definitions. Import it before running the project:
```bash
mysql -u root -p studyenglishdb < sql/schema.sql
```

### 🔧 Word Book Converter (`tools/`)
A utility tool that converts word book data from **JSON format to Excel (.xlsx)**, making it easy to bulk-import vocabulary into the system.

---

## 🏗️ Tech Stack

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Java | 8 | Language |
| Spring Boot | 2.5.10 | Core framework |
| MyBatis-Plus | 3.5.1 | ORM & code generation |
| MySQL | 8.x | Primary database |
| Redis | — | Caching & session storage |
| JWT (java-jwt) | 3.10.3 | Authentication & authorization |
| OkHttp SSE | 3.14.9 | Baidu ERNIE Bot streaming |
| Swagger 3 | 3.0.0 | API documentation |
| Apache POI | 4.1.2 | Excel import/export |
| PageHelper | 1.3.0 | Pagination |
| Hutool | 5.7.20 | Utility library |

### Frontend
| Technology | Version | Purpose |
|---|---|---|
| Vue.js | 2.6.14 | UI framework |
| Element UI | 2.15.14 | Component library |
| Vuex | 3.6.2 | State management |
| Vue Router | 3.5.1 | Client-side routing |
| ECharts | 5.5.0 | Learning data visualization |
| Axios | 1.6.5 | HTTP client |
| WangEditor | 4.7.15 | Rich text editor |
| Mavon Editor | 2.10.4 | Markdown editor |

---

## 🐳 Docker Deployment

The easiest way to run this project is with Docker Compose.

### Prerequisites
- Docker
- Docker Compose v2.17+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/EeemoTT/english-learning-system.git
   cd english-learning-system
   ```

2. **Add your `application.yml`**

   Create `backend/src/main/resources/application.yml` based on the example:
   ```yaml
   server:
     port: 9090
   spring:
     datasource:
       url: jdbc:mysql://mysql:3306/studyenglishdb?serverTimezone=GMT%2b8
       username: your_db_username
       password: your_db_password
     redis:
       host: redis
       port: 6379
   mybatis:
     mapper-locations:
       classpath:mapper/*.xml
   ```

3. **Build the frontend locally** (required — EC2 free tier has limited memory)
   ```bash
   cd frontend
   npm install
   npm run build
   cd ..
   ```

4. **Start all services**
   ```bash
   docker-compose up -d
   ```

5. **Access the app**
   - Frontend: `http://localhost`
   - Backend API: `http://localhost:9090`
   - Swagger docs: `http://localhost:9090/swagger-ui/index.html`

> The `sql/data.sql` file will be automatically imported into MySQL on first startup.

---

## 🚀 Getting Started (Local Development)

### Prerequisites

- Java 8+
- Node.js 14+
- MySQL 8.x
- Redis
- A [Baidu AI Cloud](https://cloud.baidu.com/) account with ERNIE Bot API access

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/english-learning-system.git
   cd english-learning-system/backend
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE studyenglishdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Import the data**
   ```bash
   mysql -u root -p studyenglishdb < sql/data.sql
   ```

4. **Configure `application.yml`**
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/english_learning?useUnicode=true&characterEncoding=utf8
       username: your_db_username
       password: your_db_password
     redis:
       host: localhost
       port: 6379

   # Baidu ERNIE Bot credentials
   baidu:
     api-key: your_baidu_api_key
     secret-key: your_baidu_secret_key
   ```

5. **Run the backend**
   ```bash
   mvn spring-boot:run
   ```
   - API server: `http://localhost:8080`
   - Swagger docs: `http://localhost:8080/swagger-ui/index.html`

### Frontend Setup

1. **Navigate to the frontend directory**
   ```bash
   cd ../frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure the API base URL**

   Update `.env.development`:
   ```
   VUE_APP_BASE_URL=http://localhost:8080
   ```

4. **Run the development server**
   ```bash
   npm run serve
   ```
   App runs at `http://localhost:8081`

5. **Build for production**
   ```bash
   npm run build
   ```

---

## 📁 Project Structure

```
english-learning-system/
│
├── backend/                                   # Spring Boot application
│   └── src/main/java/com/studyEnglish/
│       ├── controller/                        # REST API endpoints
│       │   ├── WXYYController.java            # Baidu ERNIE Bot (AI chat)
│       │   ├── WordController.java            # Vocabulary
│       │   ├── PaperController.java           # Exam papers
│       │   ├── CommunityController.java       # Forum posts
│       │   ├── CourseController.java          # Courses
│       │   ├── SignInController.java          # Daily check-in
│       │   ├── StatisticsController.java      # Analytics
│       │   └── ...
│       ├── entity/
│       │   └── wenxinyiyan/                   # Baidu ERNIE Bot integration
│       ├── config/                            # CORS, JWT, Swagger, MyBatis
│       ├── common/                            # Unified response, constants
│       └── ...
│
└── frontend/                                  # Vue2 application
    └── src/
        ├── views/
        │   ├── front/
        │   │   ├── InteractiveStudy.vue       # AI conversation
        │   │   ├── Learning.vue               # Vocabulary learning
        │   │   ├── BookChoosing.vue           # Word book selection
        │   │   ├── Paper.vue                  # Online exam
        │   │   ├── ScoreAnalysis.vue          # Score charts
        │   │   ├── Community.vue              # Forum
        │   │   ├── Course.vue                 # Courses
        │   │   └── PersonCenter.vue           # Profile
        │   ├── Login.vue
        │   ├── Register.vue
        │   └── Manage.vue                     # Admin dashboard
        ├── router/
        ├── store/
        └── utils/request.js                   # Axios configuration
```

---

## ⚠️ Important: Before Pushing to GitHub

Make sure sensitive information is NOT committed. Your `.gitignore` should exclude:

```gitignore
# Backend
application.yml
target/
*.iml
.DS_Store

# Frontend
node_modules/
dist/
.env.development
.env.production
npminstall-debug.log
```

Use `application.yml.example` and `.env.example` files (with placeholders) for others to reference.

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 📬 Contact

If you have any questions or suggestions, feel free to open an issue.

## Update Logs - Feb 2026
- **Deployment**: Migrated to a full-stack Dockerized architecture.
- **Networking**: Configured Nginx as a reverse proxy to handle `/api` requests and resolve CORS.
- **DevOps**: Integrated `docker-compose` for streamlined environment setup.
- **CI/CD**: Implemented GitHub Actions workflow for automated build, test, and deployment.
