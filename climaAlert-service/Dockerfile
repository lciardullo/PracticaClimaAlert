FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

ARG SERVICE_NAME=climaAlert-service

COPY . .

RUN mvn clean package spring-boot:repackage -pl ${SERVICE_NAME} -am -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre
WORKDIR /app

ARG SERVICE_NAME=climaAlert-service

COPY --from=builder /app/${SERVICE_NAME}/target/${SERVICE_NAME}-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
