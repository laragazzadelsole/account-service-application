# Stage 1: Build the JAR
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src ./src

# Build the JAR file
RUN gradle build -x test

# Stage 2: Run the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/authentication-service-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]