# maven package and image build

FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/backend-assignment-sandbox-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "backend-assignment-sandbox-0.0.1-SNAPSHOT.jar"]
