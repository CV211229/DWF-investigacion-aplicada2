# Etapa de build con Maven 3.9.11 y Azul Zulu 17.0.14
FROM azul/zulu-openjdk:17.0.14 AS jdk
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Imagen final con Azul Zulu JDK 17.0.14
FROM azul/zulu-openjdk:17.0.14
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]