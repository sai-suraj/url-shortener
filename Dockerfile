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

# Copy the built JAR file
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set JVM options
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar

