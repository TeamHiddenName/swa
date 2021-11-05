FROM maven:3.5.4-jdk-8-alpine AS build
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

FROM openjdk:8-jre-alpine
COPY --from=build ./target/SearchMs-0.0.1-SNAPSHOT.jar /usr/local/lib/SearchMs.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/SearchMs.jar"]
