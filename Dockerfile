# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="kinnsdhongadi@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8000 available to the world outside this container
EXPOSE 8000

# The application's jar file
ARG JAR_FILE=target/htmlToPdf-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} html-to-pdf-sb.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/html-to-pdf-sb.jar"]

