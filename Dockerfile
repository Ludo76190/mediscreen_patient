FROM openjdk:8-jdk-alpine
COPY target/*.jar mspatient-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/mspatient-0.0.1-SNAPSHOT.jar"]