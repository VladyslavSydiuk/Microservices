FROM openjdk:17
WORKDIR /app
COPY target/client-service-0.0.1-SNAPSHOT.jar client-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "client-service.jar"]
