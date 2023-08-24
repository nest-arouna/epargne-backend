# Use a Java base image
FROM openjdk:17-alpine
LABEL mentainer="arouna.sanou@nest.sn"
# Set the working directory to /app
WORKDIR /app
#expose app port
EXPOSE 7070
# create variable
ARG JAR_FILE=target/*.jar
# Copy the Spring Boot application JAR file into the Docker image
COPY  ${JAR_FILE} /app/nest-epargne.jar
# Run the Spring Boot application when the container starts
ENTRYPOINT  ["java", "-jar", "nest-epargne.jar"]


