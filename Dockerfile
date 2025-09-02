# Stage 1: Build the JAR
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven \
    && mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/bfhl-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
