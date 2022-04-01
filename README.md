# Requirements

The software is tested in the following environment:

* Current Debian Server with packaged Tomcat and MariaDB
* The CDISC ODM standards are excluded because of Terms & Conditions of the CDISC. You can download them on [ODM standards](http://www.cdisc.org/odm).

# Getting started

* Install packaged Apache Tomcat
* Configure additional JVM Parameters: -Xms2048m
* Install packaged MariaDB
* Configure a database user and password in MariaDB.
* Configure same user and password in db/odmsummary.sql.
* Configure same user and password in src\main\webapp\WEB-INF\entityManagerDefinition.xml.
* Import db/odmsummary.sql into MariaDB for database setup.
* Add the downloaded ODM standard files to src\main\webapp\xsd\ in appropriate subfolders like odm1-3-1\
* Deploy the built .war file on the installedApache Tomcat.

# Licensing

&copy; 2021 Institute of Medical Informatics, University of Mnster.

ODMSummary is licensed under the Apache License, Version 2.0. See LICENSE for the full license text.