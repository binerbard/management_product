# # Tahap build
FROM maven:3.6-openjdk-17-slim as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Tahap run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/product-0.0.1-SNAPSHOT.jar app.jar
# COPY target/product-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
