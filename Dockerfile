# Multi-stage build for PersonApp
FROM maven:3.9.11-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom files
COPY pom.xml .
COPY common/pom.xml common/
COPY domain/pom.xml domain/
COPY application/pom.xml application/
COPY maria-output-adapter/pom.xml maria-output-adapter/
COPY mongo-output-adapter/pom.xml mongo-output-adapter/
COPY cli-input-adapter/pom.xml cli-input-adapter/
COPY rest-input-adapter/pom.xml rest-input-adapter/

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY common/src common/src
COPY domain/src domain/src
COPY application/src application/src
COPY maria-output-adapter/src maria-output-adapter/src
COPY mongo-output-adapter/src mongo-output-adapter/src
COPY cli-input-adapter/src cli-input-adapter/src
COPY rest-input-adapter/src rest-input-adapter/src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy built JARs
COPY --from=build /app/cli-input-adapter/target/cli-input-adapter-0.0.1-SNAPSHOT.jar /app/cli/app.jar
COPY --from=build /app/rest-input-adapter/target/rest-input-adapter-0.0.1-SNAPSHOT.jar /app/rest/app.jar

# Copy entrypoint script
COPY docker-entrypoint.sh /app/
RUN chmod +x /app/docker-entrypoint.sh

# Set entrypoint
ENTRYPOINT ["/app/docker-entrypoint.sh"]

