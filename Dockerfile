FROM openjdk:11-jdk
MAINTAINER Jung, Jiwoo <jiwoourty@gmail.com>

COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]