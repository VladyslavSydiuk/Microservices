FROM openjdk:17
WORKDIR /app
COPY target/authorization-service-0.0.1-SNAPSHOT.jar authorization-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "authorization-service.jar"]
