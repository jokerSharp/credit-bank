FROM alpine/java:21-jdk

WORKDIR /app

COPY target/statement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]