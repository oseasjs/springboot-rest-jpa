# Spring Boot Rest JPA

### Build Branches Status

| Branch |                                          Build                                           |                                                                                                                                                   Coverage |
|--------|:----------------------------------------------------------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------:|
| main   | ![CI](https://github.com/oseasjs/springboot-rest-jpa/workflows/CI/badge.svg?branch=main) | [![codecov](https://codecov.io/gh/oseasjs/springboot-rest-jpa/branch/main/graph/badge.svg)](https://codecov.io/gh/oseasjs/springboot-rest-jpa/branch/main) |

### Description

Blog application to add/get Posts and Comments;

* Blog data persisted on blog schema;
* Audit data persisted on audit schema;

The database scripts are versioned using liquibase, including ddl and dml scripts;
The application provide endpoints secured by Spring Security but swagger, h2 and authentication endpoints are open;
A token is required to call endpoints and could be generated using `/register` and `/authenticate` endpoints;
A sample user was seeded by application on `SeedDataConfig` class: `username: string`, `password: string`;
The application can generate `post` and `comments` automatically based on external api: `jsonplaceholdser.com` and the relaed endpoints are available on swagger;
There are 2 scheduled jobs running to moderate posts title/content and comments content and the trigger time is defined on properties file; 
The moderation only set a moderation date and moderation reason based on some invalid words seeded on db table and is being cached;  

### Requirements

To build the project, Java 17 and Maven 3 are required. 
You can install it using the SDKMan: 
https://sdkman.io/install

### Kafka and Docker Compose

The docker-compose.yml file is available on root page.

Services required by application and available on docker-compose file:
* kafka - port 29092;
* zookeeper - port 2181;
* kafdrop - port 19000;

Basic commands to execute docker-compose:
* docker-compose up -d 
* docker-compose start
* docker-compose stop
* docker-compose down --volume

Kafka console could be accessed using kafdrop on: http://localhost:19000

#### Build and Run

To build the project, you should execute: mvn clean package
To run the project you should execute: mvn springboot:run
You can also build and run the project using your prefered IDE, such as: Intellij, VSCode or Eclipse

### Database

H2 DB is being used for persistence in memory during the application execution.
When application is restarted, the data will be lost.
The connections configuration is available on `resources/application.yml`;
During the application execution, the H2-Console could be accessed on: 

* Url: http://localhost:8080/h2-console
* JDBC: jdbc:h2:mem:blog
* User Name: sa
* Password: sa

Liquibase is also used to execute database scripts to create table and the scripts are available on `resources/db` folder.

### Swagger

The endpoints are available on:
http://localhost:8080/swagger-ui/index.html

### Bootstrapped with:

- Java 17;
- Maven;
- Spring Boot 3;
- Spring Cloud 2022.0.3;
- Spring Web;
- Spring Data;
- Spring Cache;
- Spring Validation;
- Spring Actuator;
- Spring OpenFeign;
- Spring Security 6;
- SpringDoc OpenAPI;
- Hibernate Envers;
- Global Exception Handler;
- Lombok;
- Map Struct;
- Flyway;
- H2;
- JUnit 5;
- MockMVC;
- Sonar;
- Docker;
- Docker Compose;
- Github Action;
- CodeCov;
- Kafka;
- Scheduler;
- Cache;

### TODO

- Resilient4J
- Keycloak: https://github.com/eazybytes/springsecurity6
