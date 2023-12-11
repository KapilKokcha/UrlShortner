# Use the official OpenJDK runtime image
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the JAR file into the image
COPY target/UrlShortner-0.0.1-SNAPSHOT.jar /app/UrlShortner-0.0.1-SNAPSHOT.jar

# Expose the port that the application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "UrlShortner-0.0.1-SNAPSHOT.jar"]
