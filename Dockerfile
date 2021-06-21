#FROM openjdk:16-alpine3.13
#WORKDIR /time-tracker-app-backend
#
#COPY mvnw pom.xml ./
#COPY .mvn ./.mvn
#RUN chmod +x mvnw
#RUN ./mvnw dependency:go-offline

FROM openjdk:14-alpine
MAINTAINER Maxim Alekseenko
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
ARG JAR_FILE=/target/TimeTracker-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

