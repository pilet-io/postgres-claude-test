# person-service

Spring Boot 3 + Spring Data JPA REST API for the `person` table (PostgreSQL).

## Schema

```sql
CREATE TABLE person (
    id   TEXT PRIMARY KEY,
    name TEXT,
    age  INTEGER
);
```

## Run

Requires Java 17+, Maven and a PostgreSQL database.

```bash
DB_URL=jdbc:postgresql://localhost:5432/postgres DB_USERNAME=postgres DB_PASSWORD=postgres \
  mvn spring-boot:run
```

The table is created from `src/main/resources/schema.sql` on startup.

## API

| Method | Path                | Description                              |
|--------|---------------------|------------------------------------------|
| GET    | `/api/persons`      | List all persons                         |
| GET    | `/api/persons/{id}` | Get one person (404 if missing)          |
| POST   | `/api/persons`      | Create (400 if no id, 409 if id exists)  |
| PUT    | `/api/persons/{id}` | Update name/age (404 if missing)         |
| DELETE | `/api/persons/{id}` | Delete (204, or 404 if missing)          |

```bash
curl -X POST localhost:8080/api/persons -H "Content-Type: application/json" \
  -d '{"id":"p1","name":"Alice","age":30}'
```

## Test

`mvn test` runs an end-to-end CRUD test against an in-memory H2 database (PostgreSQL mode).
