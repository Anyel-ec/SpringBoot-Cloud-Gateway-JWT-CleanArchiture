FROM openjdk:11-jre-slim

COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar

# Expone el puerto 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "gateway.jar"]
