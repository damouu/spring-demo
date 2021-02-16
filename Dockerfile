FROM openjdk:15-jdk-alpine
ARG JAR_FILE=/var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]