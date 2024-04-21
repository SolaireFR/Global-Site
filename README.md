Lancer projet :
```bash
mvn spring-boot:run
```

Mysql DB :
https://dev.mysql.com/downloads/mysql/
https://spring.io/guides/gs/accessing-data-mysql

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true
==>
Here, spring.jpa.hibernate.ddl-auto can be none, update, create, or create-drop. See the Hibernate documentation for details.
none: The default for MySQL. No change is made to the database structure.
update: Hibernate changes the database according to the given entity structures.
create: Creates the database every time but does not drop it on close.
create-drop: Creates the database and drops it when SessionFactory closes.

Spring security connect to "user" with password generated in the terminal maven Spring

Spring Security nouveauté :
- https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
