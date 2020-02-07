FROM java:8-jdk-alpine

COPY ./target/*.jar /usr/app/app.jar

WORKDIR /usr/app
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
