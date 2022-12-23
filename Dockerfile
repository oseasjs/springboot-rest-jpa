# Image docker for MAC OS - M1
# FROM arm64v8/openjdk

FROM openjdk:17-alpine
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

# Quick Docker Commands
# docker build -t springboot-rest-jpa:latest .
# docker run --name springboot-rest-jpa -p 8080:8080 springboot-rest-jpa:latest
# docker run --name springboot-rest-jpa -t -p 8080:8080 springboot-rest-jpa:latest
# docker image ls
# docker image rmi springboot-rest-jpa -f
