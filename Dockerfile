FROM openjdk:16-alpine3.13
WORKDIR /time-tracker-app-backend

COPY mvnw pom.xml ./ 
COPY .mvn ./.mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
