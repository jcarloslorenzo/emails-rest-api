FROM openjdk:17-jdk-alpine
WORKDIR /opt/emails
MAINTAINER jclorenzo
COPY emails-app.jar .
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-DlogFilesPath=./logs","-jar","emails-app.jar"]