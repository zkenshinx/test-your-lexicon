## About The Project

REST API project where users can participate in a quiz consisting of multiple steps.
Each step consists of selecting a correct translation for a word from some given number 
of suggested words.

## Tech Stack
* Spring Framework, Boot, Web, Security, Actuator
* Lombok
* Checkstyle
* Flyway
* PostgreSQL
* Redis
* Data JPA, Hibernate, Jedis

## Prerequisities

* Java JDK
* Maven
* Docker

## Usage

Build the app:
```
mvn clean install
```
Run app:
```
mvn spring-boot:run
```
Run migration:
```
mvn clean flyway:migrate
```
Run checkstyle:
```
mvn checkstyle:checkstyle
```
