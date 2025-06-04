# 1. Build Stage: compilar con JDK 21 y Gradle Wrapper
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# Copiamos sólo archivos necesarios para cache de dependencias primero
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY gradlew /app/

# Cargamos dependencias y wrapper (build inicial)
RUN chmod +x ./gradlew && ./gradlew --no-daemon dependencies

# Ahora copiamos el resto del código
COPY . /app

# Compilamos sin tests ni checks
RUN ./gradlew clean build -x test -x check --no-daemon

# 2. Runtime Stage: sólo JRE para ejecutar
FROM openjdk:21-jdk-slim AS runtime

WORKDIR /app

# Copiamos el JAR ya compilado
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto expuesto (si usas Spring Boot actuator o similar)
EXPOSE 8761

# Comando de inicio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
