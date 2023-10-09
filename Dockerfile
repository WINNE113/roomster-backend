FROM openjdk:17
ADD ./roomster-backend.jar roomster-backend.jar
ENTRYPOINT ["java", "-jar", "roomster-backend.jar"]