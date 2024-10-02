# Use the official maven image to compile and package the application
FROM maven:3.8.5-openjdk-11 AS build

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and compile the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use the official openjdk image to run the application
FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/movie-booking-1.0.0.jar ./movie-booking-1.0.0.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "movie-booking-1.0.0.jar"]