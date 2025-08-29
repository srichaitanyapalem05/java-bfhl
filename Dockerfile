# Optional: containerize
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/bfhl-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
