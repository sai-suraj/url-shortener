# Build stage
FROM gradle:8.6.0-jdk21 AS build
WORKDIR /app
COPY build.gradle settings.gradle* ./
RUN gradle --no-daemon dependencies
COPY src ./src
RUN gradle --no-daemon bootJar

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Set timezone and JVM options
ENV TZ=UTC
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar