# Usando a imagem do JDK 21 para compilar o projeto
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package

# Criando a imagem final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

# Criar um usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

ENTRYPOINT ["java", "-jar", "app.jar"]
