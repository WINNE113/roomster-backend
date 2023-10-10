FROM openjdk:17
EXPOSE 8080
WORKDIR /app
COPY target/roomster-backend.jar roomster-backend.jar
ENTRYPOINT ["java", "-jar", "roomster-backend.jar"]