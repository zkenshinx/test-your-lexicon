FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

COPY ./src ./src

RUN mvn install -DskipTests

FROM openjdk:17.0.2-jdk

WORKDIR /app

COPY --from=build ./app/target/test-your-lexicon-0.0.1-SNAPSHOT.jar myapp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/myapp.jar"]