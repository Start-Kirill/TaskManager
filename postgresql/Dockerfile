FROM postgres:15.3-alpine3.18
MAINTAINER ITAcademy

ADD /user-service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /user-service/dml/*.sql /docker-entrypoint-initdb.d/
ADD /audit-service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /task-service/ddl/*.sql /docker-entrypoint-initdb.d/

