# Release tracker demo

### Introduction & Setup
This project was created as part of the coding assignment. The goal of the project is to create 
a simple Spring Boot application that manages software releases. The application needs to expose several 
API endpoints that will allow the user to manage the releases. 

The application uses [PostgreSQL](https://www.postgresql.org/) as the database. Before you start, you will need to create the database 
and update the connection string and other database properties in the `application.settings` file. I won't 
cover database setup here, but I will provide the SQL needed to create the database once you have installed 
PostgreSQL on your machine. Here is the SQL script:

```
CREATE DATABASE codeforge
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS public.release
(
    name character varying COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default",
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    "createdAt" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "lastUpdateAt" timestamp without time zone,
    status character varying COLLATE pg_catalog."default",
    "releaseDate" date,
    CONSTRAINT id_pk PRIMARY KEY (id)
        INCLUDE(id)
)

TABLESPACE pg_default;

ALTER TABLE public.release
    OWNER to postgres;
```
Once you have the database created, you need to update your database properties in `application.settings` file:
```
spring.datasource.url = jdbc:postgresql://localhost:5432/codeforge
spring.datasource.username = postgres
spring.datasource.password = YOUR_DB_PWD
spring.datasource.driver-class-name =org.postgresql.Driver
```
Of course, `spring.datasource.url` will point out to the database on your machine (or you can target a remote 
machine, or even a Docker container). Password will be the same as the one that you have chosen during the 
Postgres setup.

### Swagger documentation

### Jaeger-tracing support

### Raygun support

### Potential improvements
