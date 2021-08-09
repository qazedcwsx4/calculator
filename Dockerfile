FROM openjdk:8-jdk-alpine
COPY target/*.jar appplication.jar
ENTRYPOINT ["java","-jar","/appplication.jar"]