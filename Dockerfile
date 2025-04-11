FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:18-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Khaopiyoji-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/Khaopiyoji-0.0.1-SNAPSHOT.jar"]