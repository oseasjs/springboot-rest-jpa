# Spring Boot Rest JPA

### Build Branches Status

| Branch |                                          Build                                           |                                                                                                                                                   Coverage |
|--------|:----------------------------------------------------------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------:|
| main   | ![CI](https://github.com/oseasjs/springboot-rest-jpa/workflows/CI/badge.svg?branch=main) | [![codecov](https://codecov.io/gh/oseasjs/springboot-rest-jpa/branch/main/graph/badge.svg)](https://codecov.io/gh/oseasjs/springboot-rest-jpa/branch/main) |

### Description

Blog application to add/get Posts and Comments;

* Blog data persisted on blog schema;
* Audit data persisted on audit schema;

### Requirements

To build the project, Java 17 and Maven 3 are required. 
You can install it using the SDKMan: 
https://sdkman.io/install

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
- Spring Cloud 2022;
- Spring Web;
- Spring Data;
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
- Github Action;
- CodeCov;

### TODO

- Resilient4J
- ActiveMQ
- Micrometer
- Keycloak: https://github.com/eazybytes/springsecurity6
