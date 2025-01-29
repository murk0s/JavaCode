# FROM maven:3.8.4-openjdk-17 as builder
# WORKDIR /app
# COPY . /app/.
# # RUN mvn -f /app/pom.xml clean package -D maven.test.skip=true
# RUN mvn clean install
FROM openjdk:17
WORKDIR /app
# COPY /target/. /app/target/.
COPY . /app/.
# VOLUME /tmp
# ADD target/JavaCode-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/target/JavaCode-0.0.1.jar"]