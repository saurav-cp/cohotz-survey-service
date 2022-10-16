FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
MAINTAINER Saurav Samantray
COPY target/cohotz-survey-service-0.1.jar /app/app.jar
#COPY src/main/resources/db/data/init/ /app/db/data/init
ENTRYPOINT ["java","-jar","/app/app.jar"]