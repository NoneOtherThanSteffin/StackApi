FROM openjdk:16-slim
VOLUME /tmp
ADD /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]