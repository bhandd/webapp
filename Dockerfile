# ===== STAGE 1: Bygga appen =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Kopiera pom och src
COPY pom.xml .
COPY src ./src

# Bygg jar-filen
RUN mvn clean package -DskipTests

# ===== STAGE 2: Köra appen =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Kopiera över jar från build-staget
COPY --from=build /app/target/*.jar app.jar

# Exponera port (Spring Boot default = 8080)
EXPOSE 8080

# Starta appen
ENTRYPOINT ["java", "-jar", "app.jar"]
