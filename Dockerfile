FROM eclipse-temurin:25-jre
WORKDIR /app
COPY target/*.jar chupapo-api.jar
ENTRYPOINT ["java", "-jar", "chupapo-api.jar"]