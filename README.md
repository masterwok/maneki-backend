# Maneki Backend

A Ktor backend using clean architecture. SQLDelight is used to interface with MySQL. JWT is used for authentication.

**Environment variables required by the backend:**

- `JWT_SECRET`, the secret used to sign JWT
- `JDBC_URL`, the URL of the MySQL database
- `MYSQL_USER`, the MySQL username
- `MYSQL_PASSWORD`, the MySQL password
