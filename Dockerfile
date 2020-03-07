# Start with a base image containing Java runtime
#FROM openjdk:8-jdk-alpine

# Add Maintainer Info
#LABEL maintainer="kinnsdhongadi@gmail.com"

# Add a volume pointing to /tmp
#VOLUME /tmp

# Make port 8000 available to the world outside this container
#EXPOSE 8000

# The application's jar file
#ARG JAR_FILE=target/htmlToPdf-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
#ADD ${JAR_FILE} html-to-pdf-sb.jar

# Run the jar file 
#ENTRYPOINT ["java","-jar","/html-to-pdf-sb.jar"]

#---------------------------------------------------

# Maven build container 

FROM maven:3.5.2-jdk-8-alpine AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

#pull base image

FROM openjdk:8-jdk-alpine

#maintainer 
MAINTAINER kinnsdhongadi@gmail.com
#expose port 8000
EXPOSE 8000

#default command
CMD java -jar /data/htmlToPdf-0.0.1-SNAPSHOT.jar

#copy html to pdf to docker image from builder image

COPY --from=maven_build /tmp/target/htmlToPdf-0.0.1-SNAPSHOT.jar /data/htmlToPdf-0.0.1-SNAPSHOT.jar

