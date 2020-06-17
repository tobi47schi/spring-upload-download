FROM java:8-jdk-alpine

COPY ./target/*.jar /usr/app/app.jar

WORKDIR /usr/app
EXPOSE 6666

ENTRYPOINT ["java","-jar","app.jar"]
