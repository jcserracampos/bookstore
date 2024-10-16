# Bookstore

Brief description of your project.

## Configuration

To configure the project, update the `application.properties` file with your PostgreSQL database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

Perhaps you will need to grant the necessary permissions to the user:

```sql
CREATE DATABASE yourdatabase;
CREATE USER yourusername WITH ENCRYPTED PASSWORD 'yourpassword';
\c yourdatabase
GRANT CREATE ON SCHEMA public TO yourusername;
GRANT USAGE ON SCHEMA public TO yourusername;
```

## Building the Project

Steps to build the project:

```bash
./mvnw clean install
```

## Running the Project

How to run the project:

```bash
./mvnw spring-boot:run
```

## Running Tests

How to run tests for the project:

```bash
./mvnw test
```