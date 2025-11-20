# Java Shop Lab

A desktop e-commerce management application built in **Java 23**, **Swing**, and **Maven**, using a clean **layered architecture** and **CSV-based persistence**.  
The system supports both **CLIENT** and **ADMIN** roles, including product browsing, orders, payments, and user management.

---

## 🚀 Features

### 👤 User Roles
#### CLIENT:
- Register and log in
- Browse product catalog (digital & physical products)
- Add products to cart
- Place orders and view order history
- Request account balance top-ups
- View payment request statuses (approved / rejected)

#### ADMIN:
- Approve or reject payment requests
- Manage product catalog (add/edit/remove)
- View and manage all orders
- Change order statuses (NEW → PAID → SHIPPED → COMPLETED)

---

## 🏗 Architecture

The project uses a **fully modular and layered structure**:

```
src/main/java/pl/shop/lab
│
├── app                     # ApplicationContext (services + repositories)
├── controller              # AppController (UI <-> services bridge)
├── io                      # CsvUtils, IdGenerator
├── model                   # Business entities (User, Product, Order, Payment...)
├── repository              # CSV-based repositories for all entities
├── service                 # Business logic (UserService, OrderService...)
└── view
    ├── frames              # MainFrame (application window)
    └── panels              # Swing panels (LoginPanel, RegisterPanel, etc.)
```

---

## 💾 Data Persistence

All persistent data is stored in **CSV files** inside the `data/` directory:

```
data/
├── ids.csv               # Auto-incrementing ID sequences
├── products.csv          # Product catalog
├── users.csv             # User accounts
├── payment_requests.csv  # Pending/approved/rejected top-ups
└── orders.csv            # Saved orders (with item lists)
```

Each repository:
- loads CSV on startup,
- keeps in-memory list,
- writes entire list back on modifications.

Products with variable attributes (digital / physical) are stored using `getType()` and parsed via factory logic.

---

## 🪟 User Interface (Swing)

The UI is built using:
- **CardLayout** for switching screens
- Separate `JPanel` classes for each screen
- Central navigation via `AppController`

### Implemented Screens:
- LoginPanel
- RegisterPanel (full address + password + login)
- MainFrame (router for all screens)

### Upcoming:
- ClientHomePanel
- AdminPanel
- ProductListPanel
- CartPanel
- PaymentRequestPanel
- OrderListPanel

---

## ⚙️ How to Run

### 1. Clone the repository
git clone https://github.com/<your-user>/java-shop-lab.git
cd java-shop-lab

### 2. Build with Maven
mvn clean package

### 3. Run the app
java -jar target/java-shop-lab.jar

OR via IntelliJ:
Run → App.java

---

## 🧩 Core Components

### ApplicationContext
- Creates all repositories
- Creates all services
- Holds `loggedUser`
- Provides singleton access to system components

### Repositories
- Load/save data to CSV
- Parse entity structures
- Implement basic CRUD

### Services
- Contain business logic
- No Swing, no CSV — pure logic layer

### UI (Swing)
- Panels interact with services through AppController
- No business logic inside GUI

---

## 🔐 Authentication Flow

1. User enters login & password  
2. `UserService.login()` returns Optional<User>  
3. `AppController` sets `loggedUser` in ApplicationContext  
4. Navigation jumps to:
   - **CLIENT** → ClientHomePanel
   - **ADMIN** → AdminPanel

---

## 📸 Screenshots (To Be Added)


---

---

## 👤 Author

Kacper Junory  
