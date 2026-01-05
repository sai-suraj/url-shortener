# Build stage
FROM gradle:8.6.0-jdk21 AS build

# Set working directory
WORKDIR /app

# Copy build configuration files
COPY build.gradle settings.gradle* ./

# Download and cache dependencies
RUN gradle --no-daemon dependencies

# Copy source code
COPY src ./src

# Build the application
RUN gradle --no-daemon bootJar

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

