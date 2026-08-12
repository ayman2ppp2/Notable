# Notable

A collaborative note-taking REST API built with **Spring Boot 4.1** (Java 26, Maven). Notes are private per-user and authenticated with JWT tokens; groups allow sharing notes among members.

## Tech stack

- Spring Boot 4.1 / Spring MVC, Data JPA (Hibernate), Security
- JWT auth via `jjwt` 0.13
- PostgreSQL (production) / H2 (tests)
- Bean Validation (`spring-boot-starter-validation`)

## Features

- Stateless JWT authentication — register, login, BCrypt-hashed passwords
- Full notes CRUD scoped to the authenticated user
- Groups: collections of users and notes (schema modeled, service/API planned)
- Externalized configuration via environment variables

## Getting started

Prereqs: JDK 17+, Maven 3.9+

```bash
export DB_URL=jdbc:postgresql://localhost:5432/notable
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=change-me-0123456789
mvn spring-boot:run
```

Run tests (uses in-memory H2):

```bash
mvn test
```

## Configuration

| Property | Env var | Default |
|---|---|---|
| `spring.datasource.url` | `DB_URL` | `jdbc:postgresql://localhost:5432/notable` |
| `spring.datasource.username` | `DB_USERNAME` | `postgres` |
| `spring.datasource.password` | `DB_PASSWORD` | `postgres` |
| `app.jwt.secret` | `JWT_SECRET` | dev placeholder |
| `app.jwt.expiration-ms` | `JWT_EXPIRATION_MS` | `86400000` (24h) |

## API

| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/register` | — | Create account, returns `201 {token}` |
| POST | `/api/auth/login` | — | Login, returns `{token}` |
| GET | `/api/notes` | Bearer | List my notes |
| POST | `/api/notes` | Bearer | Create note, `201` |
| GET | `/api/notes/{id}` | Bearer | Get note |
| PUT | `/api/notes/{id}` | Bearer | Update note |
| DELETE | `/api/notes/{id}` | Bearer | Delete note, `204` |
| POST | `/` | — | Health check |

Example:

```
POST /api/auth/register
{"username":"alice","email":"alice@example.com","password":"secret1"}
→ 201 {"token":"eyJhbGciOi..."}
```

Then send `Authorization: Bearer <token>` on all note endpoints.

## Architecture

```
com.example
├── MyApplication          # entry point (@SpringBootApplication)
├── controllers            # AuthController, NoteController
├── dto                    # request/response records + validation
├── models                 # Note, Group, User (JPA entities)
├── repositories           # NoteRepository, UserRepository
├── security               # JwtService, JwtAuthFilter, SecurityConfig
└── services               # NoteService, UserService, GroupService
```

## Security model

- `/api/auth/**` is public; all other endpoints require a valid bearer token
- `JwtAuthFilter` validates the token and loads the user from the DB into the security context
- Passwords hashed with BCrypt; sessions are stateless

## Data model

- **User** — owns notes, belongs to groups
- **Note** — title, content, owner, optional group, `createdAt`/`updatedAt` auto-managed
- **Group** — name, many-to-many members, one-to-many notes

## License

MIT (see LICENSE)
