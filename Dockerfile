#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8
WORKDIR /Users/odina/dockerVolume
RUN cd target/
COPY swift-1.0.jar swift
#CMD java -jar prduct-info
EXPOSE 3000