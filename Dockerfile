FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app

# Copy Gradle wrapper files and source code
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src ./src

# Ensure the Gradle wrapper script has executable permissions
RUN chmod +x gradlew

# Build the project
RUN ./gradlew build -x test

# Use a lightweight JDK image for running the application
FROM openjdk:21-slim AS runtime
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
