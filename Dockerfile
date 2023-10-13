FROM ubuntu:latest as BUILDER

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

COPY --from=BUILDER /target/todolist-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
