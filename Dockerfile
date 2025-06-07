# Use Maven image to build the application
FROM maven:3.8.5-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JRE image for running the application
FROM adoptopenjdk/openjdk11:alpine-jre

# Install required libraries for font rendering
RUN apk add --no-cache \
    fontconfig \
    ttf-dejavu \
    freetype \
    freetype-dev

# Set the working directory for the runtime container
WORKDIR /opt/app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/parsecfdi-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for Render
EXPOSE 8080

# Use CMD to run the application
CMD ["java", "-jar", "app.jar"]