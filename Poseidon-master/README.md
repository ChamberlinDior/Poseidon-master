![img.png](src/main/resources/META-INF/resources/img/poseidon.png)

Poseidon is a web-deployed enterprise software that aims to 
generate more trades for institutional investors buying and selling fixed income securities.

This is project number 7 in the OpenClassrooms Java course whose goal is to obtain the skills 
to implement an API using good development practices.

<h2>Built with</h2>

- java 11 (object oriented programming language ) <br/> https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
- Apache Maven 3.6.3 (Java project management ) <br/> http://maven.apache.org/
- Spring Boot <br/> https://spring.io/projects/spring-boot
- Spring Web (build web,uses Apache Tomcat as the default embedded container) <br/> https://mvnrepository.com/artifact/org.springframework/spring-web
- Spring Data Jpa (implement JPA based repositories) <br/> https://spring.io/projects/spring-data-jpa
- Spring Security (access-control framework) <br/> https://spring.io/projects/spring-security
- MySQL (object-relational database system) <br/> https://www.mysql.com/fr/
- Openpojo (Testing POJOs easier) <br/> https://mvnrepository.com/artifact/com.openpojo/openpojo/0.8.3
- JUnit Jupiter (execution of tests) <br/> https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
- Thymeleaf (server-side Java template) <br/> https://www.thymeleaf.org/
- OAuth2 (Protocol authorization flows for web applications) <br/> https://oauth.net/2/
- Passay (Password policy enforcement for Java) <br/> http://www.passay.org/

<h2>Installation and Getting Started</h2>
<h3>Requirements</h3>

- Java 11
- Maven 3.6.3
- MySQL

<h3>Installation</h3>

1. Clone this repo
   ```shell
   git clone https://github.com/G-jonathan/Poseidon.git

2. Package the application
   ```shell
   mvn package

3. Configure the database on your system <br/>
   (database access logs can be modified in the application.properties file)
   ```shell
   execute the src/main/resources/SQL/databaseScript.sql

4. Execute the Jar
   ```shell
   java -jar ./target/poseidon-0.0.1-SNAPSHOT.jar

5. You can now access the application at localhost:8080/login