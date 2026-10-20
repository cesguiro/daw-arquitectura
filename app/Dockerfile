# daw-arquitectura: app = API REST con Spring Boot (Tomcat embebido, puerto 8080)
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /build

# Capa cacheada: dependencias primero (solo cambian si tocas el pom.xml)
COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn -q package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
