
# Airbnb Clone – Full Stack Web Application

### <b>Description</b>:

- Built using Angular 18, Spring Boot 3, and Java 17, this project replicates the core functionality of Airbnb. It allows users to:

- Sign up and log in with secure authentication using JWT and OAuth2 (managed by keycloak).

- Browse available property listings with filtering and search

- Create, update, or delete their own listings

- Make reservations on available properties

#### This full-stack application demonstrates modern web development practices, RESTful API design, responsive UI, and robust backend architecture.
   
### Functional Requirements v1.0

#### 1. User Registration and Authentication

- Users can:
  - Register with a unique email and password.
  - Log in using their credentials.
  - Log in via **social accounts** (Google, Facebook).
  - Reset their password using an email-based token (or security questions – TBD).

- Role-Based Access Control (RBAC):
  - **Guests (Users)** can browse listings, make/cancel reservations, bookmark listings, and leave reviews.
  - **Hosts** can create, edit, update, or delete their own property listings.

---

#### 2. Property Listings Management

- Hosts can:
  - Create new listings with required details: **title, price, description, images, and availability**.
  - Update or delete their existing listings.

- Listings should include:
  - Property details: **description, price, location, available dates, host info**.
  - Media: **images and potentially videos**.
  - Reviews and ratings from guests.

---

#### 3. Booking and Reservation

- Users can:
  - Check availability for a listing before booking.
  - Make new reservations.
  - Modify or cancel existing reservations (based on policy/rules).

- The system should:
  - Block unavailable dates once a reservation is confirmed.
  - Ensure **data consistency** in high-concurrency scenarios.

---

#### 4. Email Notifications

- Email notifications must be sent for:
  - Booking confirmations (to the guest).
  - Listing updates or bookings (to the host).
  - Password reset requests.

---

#### 5. Reviews and Ratings

- Users can:
  - Submit reviews and ratings only after completing a stay.
  - View all reviews and ratings for a listing.

- Reviews should include:
  - **Rating score**
  - Optional **comment**
  - **Timestamp**

---

#### 6. Bookmarking Favorites

- Logged-in users can:
  - Bookmark listings to a personal list.
  - View and manage bookmarks from their dashboard/profile.

---

#### 7. Sharing Listings

- Users can:
  - Share listings via **email** with friends and family.
  - Optionally include a custom message.

## 📁 Project Structure (Hexagonal Architecture)

```plaintext
com.airbnb.clone
└── userEntity                             # 🟡 Feature/bounded context: "User"
    ├── domain                       # 🔵 Domain layer (core logic, entities)
    │   └── model
    │       └── User.java
    ├── application                  # 🟢 Application layer (use cases, ports, services)
    │   ├── port
    │   │   ├── input
    │   │   └── output
    │   ├── service
    │   └── mapper
    │       └── UserMapper.java
    ├── adapter                      # 🟣 Adapters (inbound/outbound)
    │   ├── in
    │   │   └── web
    │   │       ├── controller
    │   │       └── dto
    │   └── out
    │       └── persistence
    │           ├── UserJpaEntity.java
    │           ├── UserJpaRepository.java
    │           └── JpaUserRepositoryAdapter.java
    └── config                       # ⚙️ Optional: Config specific to this feature

