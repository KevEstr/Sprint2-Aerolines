FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/payments.jar /app/modulo-pagos.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/modulo-pagos.jar"]
