FROM openjdk:17-jdk

WORKDIR /app

COPY target/spring-boot-microservices-1.0-SNAPSHOT.jar /app/microserviceapp.jar

EXPOSE 8080

CMD ["java", "-jar", "microserviceapp.jar"]