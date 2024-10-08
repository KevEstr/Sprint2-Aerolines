# Usa una imagen de Maven con OpenJDK (Eclipse Temurin) para construir el proyecto
FROM maven:3.8.8-eclipse-temurin-17-alpine AS build

# Establece el directorio de trabajo para la fase de construcción
WORKDIR /app

# Copia el archivo POM y el código fuente al contenedor
COPY pom.xml ./
COPY src ./src

# Ejecuta la compilación del proyecto utilizando Maven
RUN mvn clean package -DskipTests

# Fase de producción con OpenJDK
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR generado en la fase anterior
COPY --from=build /app/target/payments.jar /app/modulo-pagos.jar

# Expone el puerto de la aplicación
EXPOSE 8081

# Comando de inicio para la aplicación
ENTRYPOINT ["java", "-jar", "/app/modulo-pagos.jar"]
