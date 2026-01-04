FROM gradle:8.7-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY src ./src

COPY src/main/resources/db/migration ./src/main/resources/db/migration

RUN gradle bootjar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

