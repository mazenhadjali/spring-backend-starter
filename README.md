# Spring Boot Backend Starter Project

A comprehensive Spring Boot starter project with JWT authentication, role-based access control (RBAC), and caching capabilities. This project provides a solid foundation for building secure enterprise-level backend applications.

## 📋 Table of Contents

- [Features](#features)
- [Architecture Overview](#architecture-overview)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Security Implementation](#security-implementation)
- [Database Schema](#database-schema)

## 🚀 Features

### Core Features
- **JWT Authentication & Authorization**: Secure token-based authentication system
- **Role-Based Access Control (RBAC)**: Granular permission management with features and roles
- **User Management System (UMS)**: Complete user lifecycle management
- **Redis Caching**: Enhanced performance with Redis caching layer
- **Data Auditing**: Automatic tracking of created/updated timestamps
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Docker Support**: Containerized development environment
- **Input Validation**: Comprehensive request validation with custom constraints

### Security Features
- **JWT Token Management**: Secure token generation and validation
- **Password Encryption**: BCrypt password hashing
- **Method-Level Security**: Fine-grained access control using `@PreAuthorize`
- **Session Management**: Stateless authentication
- **CORS Configuration**: Cross-origin resource sharing support

### Data Management
- **PostgreSQL Database**: Robust relational database integration
- **JPA/Hibernate**: Object-relational mapping with automatic DDL
- **Database Migrations**: Schema version control (ready for Flyway/Liquibase)
- **Connection Pooling**: Optimized database connections

## 🏗️ Architecture Overview

This project follows a **layered architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────┐
│           Presentation Layer        │
│         (REST Controllers)          │
├─────────────────────────────────────┤
│            Service Layer            │
│      (Business Logic & DTOs)        │
├─────────────────────────────────────┤
│         Data Access Layer           │
│     (Repositories & DAOs)           │
├─────────────────────────────────────┤
│          Persistence Layer          │
│    (JPA Entities & Database)        │
└─────────────────────────────────────┘
```

### Key Architectural Patterns
- **Repository Pattern**: Data access abstraction
- **DTO Pattern**: Data transfer between layers
- **Service Pattern**: Business logic encapsulation
- **Builder Pattern**: Object creation (Lombok)
- **Strategy Pattern**: Authentication mechanisms

## 💻 Technology Stack

### Backend Framework
- **Spring Boot 3.5.4** - Main application framework
- **Spring Security 6.x** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **Spring Cache** - Caching abstraction

### Database & Caching
- **PostgreSQL** - Primary database
- **Redis** - Caching and session storage
- **Hibernate** - ORM framework

### Development Tools
- **Java 21** - Programming language
- **Maven** - Build and dependency management
- **Lombok** - Code generation
- **ModelMapper** - Object mapping
- **Docker & Docker Compose** - Containerization

### Documentation & Testing
- **SpringDoc OpenAPI 3** - API documentation
- **JUnit 5** - Unit testing framework
- **Spring Security Test** - Security testing utilities

## 📁 Project Structure

```
backend-starter/
├── src/main/java/org/example/backendstarter/
│   ├── auth/                          # Authentication module
│   │   ├── controllers/               # Auth REST endpoints
│   │   ├── dto/                       # Authentication DTOs
│   │   └── services/                  # Auth business logic
│   ├── ums/                          # User Management System
│   │   ├── controllers/               # User/Role REST endpoints
│   │   ├── dto/                       # UMS DTOs
│   │   ├── entity/                    # JPA entities
│   │   ├── repository/                # Data repositories
│   │   ├── services/                  # Business services
│   │   ├── mappers/                   # DTO mappers
│   │   └── dao/                       # Data access objects
│   ├── common/                        # Shared components
│   │   ├── Auditable.java            # Base audit entity
│   │   └── Feature.java              # Permission enumeration
│   ├── configurations/                # Spring configurations
│   │   ├── SecurityConfig.java        # Security setup
│   │   ├── JwtAuthFilter.java         # JWT filter
│   │   ├── CacheConfig.java           # Cache configuration
│   │   ├── OpenApiConfig.java         # Swagger config
│   │   └── ModelMapperConfig.java     # Mapping configuration
│   └── utils/                         # Utility classes
│       └── DataInitializer.java       # Initial data setup
├── src/main/resources/
│   └── application.properties         # App configuration
├── docker-compose.yml                 # Development environment
├── pom.xml                           # Maven dependencies
└── README.md                         # Project documentation
```

## 🚀 Getting Started

### Prerequisites
- **Java 21+**
- **Maven 3.8+**
- **Docker & Docker Compose**
- **Git**

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/mazenhadjali/spring-backend-starter.git
   cd spring-backend-starter
   ```

2. **Start infrastructure services**
   ```bash
   docker-compose up -d postgres redis
   ```

3. **Build the application**
   ```bash
   mvn clean compile
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Quick Test
```bash
# Login to get JWT token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "superadmin", "password": "admin"}'

# Use the token to access protected endpoints
curl -X GET http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```


### Environment Variables & ⚙️ Configuration
For production deployment, override these via environment variables:
- `DB_HOST` - Database host
- `DB_PORT` - Database port
- `DB_NAME` - Database name
- `DB_USER` - Database username
- `DB_PASSWORD` - Database password
- `JWT_SECRET` - JWT signing secret
- `REDIS_HOST` - Redis host
- `REDIS_PORT` - Redis port

## 📚 API Documentation

### Interactive Documentation
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8080/v3/api-docs`

### Authentication Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | User authentication |

### User Management Endpoints
| Method | Endpoint | Description | Required Permission |
|--------|----------|-------------|-------------------|
| GET | `/api/v1/users` | List all users | `FEAT_LIST_USERS` |
| POST | `/api/v1/users` | Create new user | `FEAT_CREATE_USER` |
| GET | `/api/v1/users/{id}` | Get user by ID | `FEAT_LIST_USERS` |
| PUT | `/api/v1/users/{id}` | Update user | `FEAT_UPDATE_USER` |
| DELETE | `/api/v1/users/{id}` | Delete user | `FEAT_DELETE_USER` |
| POST | `/api/v1/users/{userId}/roles/{roleId}` | Assign role | `FEAT_ASSIGN_ROLE_TO_USER` |
| DELETE | `/api/v1/users/{userId}/roles/{roleId}` | Revoke role | `FEAT_REVOKE_ROLE_FROM_USER` |

### Role Management Endpoints
| Method | Endpoint | Description | Required Permission |
|--------|----------|-------------|-------------------|
| GET | `/api/v1/roles` | List all roles | `FEAT_LIST_ROLES` |
| POST | `/api/v1/roles` | Create new role | `FEAT_CREATE_ROLE` |
| PUT | `/api/v1/roles/{id}` | Update role | `FEAT_UPDATE_ROLE` |
| DELETE | `/api/v1/roles/{id}` | Delete role | `FEAT_DELETE_ROLE` |

## 🔒 Security Implementation

### Authentication Flow
1. **User Login**: Submit credentials to `/api/auth/login`
2. **Token Generation**: Server validates credentials and generates JWT
3. **Token Usage**: Include token in `Authorization: Bearer <token>` header
4. **Token Validation**: Server validates token on each protected request

### Authorization System
The system implements a feature-based authorization model:

#### Available Features
- **User Management**: `LIST_USERS`, `CREATE_USER`, `UPDATE_USER`, `DELETE_USER`
- **Role Management**: `LIST_ROLES`, `CREATE_ROLE`, `UPDATE_ROLE`, `DELETE_ROLE`
- **User-Role Assignment**: `ASSIGN_ROLE_TO_USER`, `REVOKE_ROLE_FROM_USER`
- **Role-Feature Assignment**: `ASSIGN_FEATURE_TO_ROLE`, `REVOKE_FEATURE_FROM_ROLE`
- **Password Management**: `RESET_USER_PASSWORD`

#### Permission Checking
```java
@PreAuthorize("hasAuthority('FEAT_CREATE_USER')")
public ResponseEntity<AUserDto> createUser(@RequestBody CreateUserRequest request) {
    // Method implementation
}
```

### Default Admin User
The system creates a default admin user on startup:
- **Username**: `superadmin`
- **Password**: `admin`
- **Role**: `ADMIN` (all features enabled)

## 🗄️ Database Schema

### Core Tables
- **users**: User information and credentials
- **roles**: System roles and permissions
- **role_features**: Role-feature associations
- **users_roles**: User-role associations

### Entity Relationships
```
AUser ←→ Role (Many-to-Many)
Role ←→ Feature (One-to-Many via ElementCollection)
```

### Auditing
All entities extend `Auditable` class providing:
- `createdDate`: Automatic creation timestamp
- `lastModifiedDate`: Automatic update timestamp

### Production Considerations
- Use environment-specific configuration
- Set up monitoring and logging
- Implement health checks

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BackendStarterApplicationTests

# Run with coverage
mvn clean test jacoco:report
```

## 🔧 Development

### Code Quality
- **Lombok**: Reduces boilerplate code

### Extension Points
- **Additional Features**: Extend Feature enum
- **Custom Validators**: Add validation annotations
- **Event Listeners**: Add application events

## 📋 TODO / Future Enhancements

### Planned Features
- [ ] Password reset functionality
- [ ] Email notifications
- [ ] File upload/download
- [ ] User profile management
- [ ] Audit logging
- [ ] Rate limiting
- [ ] Multi-tenancy support
- [ ] OAuth2 integration
- [ ] Kubernetes deployment configs
- [ ] CI/CD pipeline

### Performance Improvements
- [ ] Database query optimization
- [ ] Caching strategies
- [ ] Async processing
- [ ] Metrics and monitoring

### Coding Standards
- Follow Java naming conventions
- Use Lombok annotations appropriately
- Document public APIs
- Follow REST API best practices

## 📞 Support

For questions and support:
- **GitHub Issues**: Report bugs and request features
- **Documentation**: Check this README and inline documentation
- **Code Comments**: Review implementation details in source code

---