FROM openjdk:8-jdk-alpine

EXPOSE 8778

VOLUME /busplannereta
COPY target/*.jar /busplannereta/busplannereta.jar

ENTRYPOINT ["java","-jar","/busplannereta/busplannereta.jar"]