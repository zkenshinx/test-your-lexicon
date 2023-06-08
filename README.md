## About The Project

REST API project where users can participate in a quiz consisting of multiple steps.
Each step consists of selecting a correct translation for a word from some given number 
of suggested words.

## Tech Stack
* Spring Framework, Boot, Web, Actuator
* Lombok
* Checkstyle
* Flyway
* PostgreSQL
* Data JPA, Hibernate

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
Run checkstyle:
```
mvn checkstyle:checkstyle
```
