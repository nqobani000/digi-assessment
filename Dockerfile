FROM azul/zulu-openjdk-alpine:17
WORKDIR /app
COPY ./target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
