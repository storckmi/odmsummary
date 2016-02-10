# Requirements

* The software is tested in the following environment:
* Maven 3.3.9 (http://archive.apache.org/dist/maven/maven-3/3.3.9/)
* Apache Tomcat 7.0.50 (http://archive.apache.org/dist/tomcat/tomcat-7/v7.0.50/)
* Spring Framework Agent 2.5.6 (http://mvnrepository.com/artifact/org.springframework/spring-agent/2.5.6)
* MySQL 5.5 (https://dev.mysql.com/downloads/windows/installer/5.5.html)
* The CDISC ODM standards are excluded because of Terms & Conditions of the CDISC. You can download them on http://www.cdisc.org/odm.

# Getting started

* Install Apache Tomcat 7.0.50
* Configure additional JVM Parameters: -javaagent:[Path to the local Spring Framework Agent 2.5.6 jar] -Xms1024m -XX:MaxPermSize=256m
* Install MySQL 5.5
* Configure a database user and password in db/odmsummary.sql.
* Configure same user an dpassword in src\main\resources\META-INF\persistence.xml.
* Run db/odmsummary.sql for database setup.
* Add the downloaded ODM standard files to src\main\webapp\xsd\ in appropriate subfolders like odm1-3-1\
* Deploy the built .war file on the Apache Tomcat 7.0.50.

# Licensing

ODMSummary is licensed under the Apache License, Version 2.0. See LICENSE for the full license text.