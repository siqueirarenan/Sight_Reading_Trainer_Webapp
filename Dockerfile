FROM adoptopenjdk:11-hotspot-bionic
RUN apt-get update
RUN apt-get -y install xorg gtk2-engines libasound2 libgtk2.0-0
FROM maven:3.6.3-jdk-11 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn jpro:run
