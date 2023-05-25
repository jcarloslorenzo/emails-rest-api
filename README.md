# Emails REST API
Basic example of a REST API with Spring Boot.

#### Requirements
* Maven
* Java 17
* Docker-compose
* Postman

#### Technologies
* [Spring Boot](https://spring.io/projects/spring-boot)
* [MapStruct](https://mapstruct.org/)
* [Lombok](https://projectlombok.org/)
* [Swagger with Springdoc-OpenAPI](https://springdoc.org/)
* [Liquibase](https://www.liquibase.org/)
* [PostgreSQL](https://www.postgresql.org/)
* [RabbitMQ](https://www.rabbitmq.com/)

#### Usage
After downloading the repository it will be necessary to compile the project using maven:

	~/emails-rest-api$ mvn clean install
Once the build is complete, launch the application using docker-compose:

	~/emails-rest-api/project-resources/docker$ docker-compose up -d
Docker compose will start a PostgreSQL database, a PgAdmin instance, a RabbitMQ server and, finally, start the application with the REST API.
After a few seconds, all the applications will be available in the following links.
* [REST API - Swagger](http://localhost:8080/api/swagger)
* [PGAdmin](http://localhost:5050/)
* [RabbitQM](http://localhost:15672/)

Being a simple example, the same username and password have always been used for all applications related to this project.
* Username: emailsUser / emailsUser@nomail.com (for pgAdmin only)
* Password: emailsUserPass

Inside the directory **project-resources/postman** there is a JSON named **EmailsAPI-PostmanCollection.json** that contains a Postman collection with sample API calls.

