# Spring Boot Rest JPA

### Build Branches Status

| Branch |                                          Build                                           |                                                                                                                                                   Coverage |
|--------|:----------------------------------------------------------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------:|
| main   | ![CI](https://github.com/oseasjs/springboot-rest-jpa/workflows/CI/badge.svg?branch=main) | [![codecov](https://codecov.io/gh/oseasjs/springboot-rest-jpa/branch/main/graph/badge.svg)](https://codecov.io/gh/oseasjs/springboot-rest-jpa/branch/main) |

### Description

Blog application to add/get Posts and Comments;  
* Blog data persisted on blog schema;
* Audit data persisted on audit schema;

### Bootstrapped with:

- Java 11;
- Spring Boot;
- Spring Web;
- Spring Data;
- Spring Validation;
- Spring Actuator;
- Spring OpenFeign
- SpringDoc OpenAPI 3;
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
- Github Action (CI);
- CodeCov;

### TODO
- Spring Security JWT
- ActiveMQ

### Technics
-  Embedded Active MQ (to create a comment an a post from random user)
- https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example/

- OpenFeign (to get user from random user and send to the queue)
- https://www.baeldung.com/spring-cloud-openfeign
