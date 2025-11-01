# Inventory Management System

A full-stack inventory management application built with Spring Boot and React, featuring JWT authentication, role-based access control, and comprehensive inventory tracking capabilities.

## 🚀 Features

- **User Authentication & Authorization**: JWT-based authentication with role-based access control (ADMIN, MANAGER, USER)
- **Product Management**: Create, read, update, and delete products with category organization
- **Inventory Tracking**: Real-time stock monitoring with low-stock alerts
- **Purchase Orders**: Manage purchase orders with status tracking
- **Stock Transactions**: Track stock-in, stock-out, and adjustment operations
- **Supplier Management**: Maintain supplier information and relationships
- **Search & Filter**: Advanced product search and filtering capabilities
- **Pagination**: Efficient data loading with paginated responses

## 🏗️ Project Structure

```
inventory_management/
├── backend/                    # Spring Boot REST API
│   ├── src/
│   │   └── main/
│   │       └── java/com/adv_java/inventory_management/
│   │           ├── config/             # Application configuration
│   │           ├── controllers/        # REST controllers
│   │           ├── domain/             # Entities and DTOs
│   │           ├── exception/          # Exception handling
│   │           ├── mapper/             # MapStruct mappers
│   │           ├── repository/         # JPA repositories
│   │           ├── security/           # Security configuration
│   │           └── services/           # Business logic
│   ├── pom.xml                # Maven dependencies
│   └── docker-compose.yml     # PostgreSQL container setup
│
└── frontend/                   # React + TypeScript UI
    ├── src/
    │   ├── components/        # Reusable UI components
    │   ├── contexts/          # React contexts
    │   ├── hooks/             # Custom React hooks
    │   ├── lib/               # Utility functions
    │   └── pages/             # Application pages
    ├── package.json           # npm dependencies
    └── vite.config.ts         # Vite configuration
```

## 🛠️ Technology Stack

### Backend

- **Framework**: Spring Boot 3.5.6
- **Language**: Java 17
- **Database**: PostgreSQL (production), H2 (development)
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA
- **Validation**: Jakarta Validation
- **Mapping**: MapStruct 1.6.3
- **Build Tool**: Maven

### Frontend

- **Framework**: React 18.3.1
- **Language**: TypeScript 5.8.3
- **Build Tool**: Vite 5.4.19
- **UI Library**: shadcn/ui with Radix UI
- **Styling**: TailwindCSS
- **State Management**: TanStack Query (React Query)
- **Routing**: React Router v6
- **Form Handling**: React Hook Form with Zod validation
- **Icons**: Lucide React

## 📡 API Endpoints

### Authentication

| Method | Endpoint             | Description       | Access |
| ------ | -------------------- | ----------------- | ------ |
| POST   | `/api/auth/register` | Register new user | Public |
| POST   | `/api/auth/login`    | User login        | Public |

### Products

| Method | Endpoint                                 | Description                  | Access         |
| ------ | ---------------------------------------- | ---------------------------- | -------------- |
| GET    | `/api/products`                          | Get all products (paginated) | Authenticated  |
| GET    | `/api/products/{id}`                     | Get product by ID            | Authenticated  |
| GET    | `/api/products/low-stock`                | Get low stock products       | Authenticated  |
| GET    | `/api/products/search?keyword={keyword}` | Search products              | Authenticated  |
| POST   | `/api/products`                          | Create new product           | ADMIN, MANAGER |
| PUT    | `/api/products/{id}`                     | Update product               | ADMIN, MANAGER |
| DELETE | `/api/products/{id}`                     | Delete product               | ADMIN          |

### Categories

| Method | Endpoint               | Description         | Access         |
| ------ | ---------------------- | ------------------- | -------------- |
| GET    | `/api/categories`      | Get all categories  | Authenticated  |
| GET    | `/api/categories/{id}` | Get category by ID  | Authenticated  |
| POST   | `/api/categories`      | Create new category | ADMIN, MANAGER |
| PUT    | `/api/categories/{id}` | Update category     | ADMIN, MANAGER |
| DELETE | `/api/categories/{id}` | Delete category     | ADMIN          |

### Suppliers

| Method | Endpoint              | Description         | Access         |
| ------ | --------------------- | ------------------- | -------------- |
| GET    | `/api/suppliers`      | Get all suppliers   | Authenticated  |
| GET    | `/api/suppliers/{id}` | Get supplier by ID  | Authenticated  |
| POST   | `/api/suppliers`      | Create new supplier | ADMIN, MANAGER |
| PUT    | `/api/suppliers/{id}` | Update supplier     | ADMIN, MANAGER |
| DELETE | `/api/suppliers/{id}` | Delete supplier     | ADMIN          |

### Purchase Orders

| Method | Endpoint                           | Description              | Access         |
| ------ | ---------------------------------- | ------------------------ | -------------- |
| GET    | `/api/purchase-orders`             | Get all purchase orders  | Authenticated  |
| GET    | `/api/purchase-orders/{id}`        | Get purchase order by ID | Authenticated  |
| POST   | `/api/purchase-orders`             | Create purchase order    | ADMIN, MANAGER |
| PUT    | `/api/purchase-orders/{id}`        | Update purchase order    | ADMIN, MANAGER |
| PATCH  | `/api/purchase-orders/{id}/status` | Update order status      | ADMIN, MANAGER |
| DELETE | `/api/purchase-orders/{id}`        | Delete purchase order    | ADMIN          |

### Stock Transactions

| Method | Endpoint                                      | Description                     | Access               |
| ------ | --------------------------------------------- | ------------------------------- | -------------------- |
| GET    | `/api/stock-transactions`                     | Get all transactions            | Authenticated        |
| GET    | `/api/stock-transactions/{id}`                | Get transaction by ID           | Authenticated        |
| GET    | `/api/stock-transactions/product/{productId}` | Get product transaction history | Authenticated        |
| POST   | `/api/stock-transactions/stock-in`            | Record stock in                 | ADMIN, MANAGER, USER |
| POST   | `/api/stock-transactions/stock-out`           | Record stock out                | ADMIN, MANAGER, USER |
| POST   | `/api/stock-transactions/adjust`              | Adjust stock                    | ADMIN, MANAGER       |

### Users

| Method | Endpoint          | Description    | Access |
| ------ | ----------------- | -------------- | ------ |
| GET    | `/api/users`      | Get all users  | ADMIN  |
| GET    | `/api/users/{id}` | Get user by ID | ADMIN  |
| DELETE | `/api/users/{id}` | Delete user    | ADMIN  |

## 🚦 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and pnpm
- PostgreSQL (or use Docker)

### Backend Setup

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd inventory_management/backend
   ```

2. **Start PostgreSQL (using Docker)**

   ```bash
   docker-compose up -d
   ```

3. **Configure application properties**

   - Update `src/main/resources/application.properties` with your database credentials

4. **Build and run**

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**

   ```bash
   cd frontend
   ```

2. **Install dependencies**

   ```bash
   pnpm install
   ```

3. **Start development server**

   ```bash
   pnpm dev
   ```

   The frontend will start on `http://localhost:5173`

## 🔐 User Roles

- **ADMIN**: Full system access including user management
- **MANAGER**: Can manage products, categories, suppliers, and purchase orders
- **USER**: Can view data and perform stock transactions

## 📝 Additional Documentation

- [Error Handling Guide](./ERROR_HANDLING_GUIDE.md) - Comprehensive error handling documentation

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is created for Advanced Java coursework.

## 🙏 Acknowledgments

- Spring Boot documentation
- React and TypeScript communities
- shadcn/ui for the beautiful component library
