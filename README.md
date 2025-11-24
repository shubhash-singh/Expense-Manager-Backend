# Expense Tracker Backend

Simple Spring Boot + MongoDB backend for tracking expenses. Built with Java 17 and Spring Boot 3 using a straightforward controller → service → repository flow.

## Endpoints

- `POST /auth/signup` – create a user with plain email/password (for prototype only)
- `POST /auth/login` – return a simple UID if credentials match
- `POST /expenses` – create an expense
- `GET /expenses` – list expenses (optionally filter by `category`, `startDate`, `endDate`)
- `PUT /expenses/{id}` – update an expense
- `DELETE /expenses/{id}` – remove an expense

## Example Requests

```json
// POST /auth/signup
{
  "email": "demo@example.com",
  "password": "password123"
}

// POST /auth/login
{
  "email": "demo@example.com",
  "password": "password123"
}

// POST /expenses
{
  "amount": 42.50,
  "description": "Lunch",
  "date": "2025-01-12",
  "category": "Food",
  "userId": "user-123"
}
```

## Example Responses

```json
// POST /auth/login (success)
{
  "message": "Login successful",
  "uid": "34dea3a2-5a9f-4bfb-9ceb-ccf1dbf2e123"
}

// POST /expenses
{
  "id": "65a1f9a5b1f6f2216c123456",
  "amount": 42.50,
  "description": "Lunch",
  "date": "2025-01-12",
  "category": "Food",
  "userId": "user-123"
}
```

## Run Locally

```bash
mvn spring-boot:run
```

