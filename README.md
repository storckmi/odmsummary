# Requirements

The software is tested in the following environment:

* [Maven 3.5.2](https://archive.apache.org/dist/maven/maven-3/3.5.2/binaries/)
* [Apache Tomcat 8.0.36](http://archive.apache.org/dist/tomcat/tomcat-8/v8.0.36/)
* [MySQL 5.5](https://dev.mysql.com/downloads/mysql/)
* The CDISC ODM standards are excluded because of Terms & Conditions of the CDISC. You can download them on [ODM standards](http://www.cdisc.org/odm).

# Getting started

* Install Apache Tomcat 8.0.36
* Configure additional JVM Parameters: -Xms2048m
* Install MySQL 5.5
* Configure a database user and password in db/odmsummary.sql.
* Configure same user and password in src\main\webapp\WEB-INF\entityManagerDefinition.xml.
* Run db/odmsummary.sql for database setup.
* Add the downloaded ODM standard files to src\main\webapp\xsd\ in appropriate subfolders like odm1-3-1\
* Deploy the built .war file on the Apache Tomcat 8.0.36.

# Licensing

&copy; 2018 Institute of Medical Informatics, University of Münster.

ODMSummary is licensed under the Apache License, Version 2.0. See LICENSE for the full license text.