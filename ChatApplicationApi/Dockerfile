FROM eclipse-temurin:17-jdk-alpine as builder

COPY target/*.jar app.jar

ENTRYPOINT [ "java","-jar", "/app.jar"]