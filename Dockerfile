FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn package

FROM openjdk:20
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
