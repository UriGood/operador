# Etapa 1: construir el JAR con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagen final ligera
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Operador expone el puerto 8082
EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]