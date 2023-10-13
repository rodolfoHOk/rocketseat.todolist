FROM ubuntu:latest as BUILDER

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8080

COPY --from=BUILDER /target/todolist-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
