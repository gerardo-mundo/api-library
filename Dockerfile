FROM eclipse-temurin:17-jre-alpine

LABEL authors="gerardo-mundo"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
