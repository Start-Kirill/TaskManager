DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'user_service') THEN

      RAISE NOTICE 'Role "user_service" already exists. Skipping.';
   ELSE
      CREATE ROLE user_service LOGIN PASSWORD 'q1w2e3r4';
   END IF;
END
$do$;

CREATE DATABASE user_service
            WITH OWNER = user_service;
            ENCODING = 'UTF8'
            CONNECTION LIMIT = -1
            IS_TEMPLATE = False;

\c user_service user_service;

DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM information_schema.schemata
      WHERE  schema_name = 'app') THEN

      RAISE NOTICE 'Schema "app" already exists. Skipping.';
   ELSE
      CREATE SCHEMA app AUTHORIZATION user_service;
   END IF;
END
$do$;


CREATE TABLE app.users
    (
        uuid uuid NOT NULL,
        dt_create timestamp without time zone NOT NULL,
        dt_update timestamp without time zone NOT NULL,
        mail text NOT NULL,
        fio text NOT NULL,
        role text NOT NULL,
        status text NOT NULL,
        password text NOT NULL,
        PRIMARY KEY (uuid),
        UNIQUE (mail)
    );

ALTER TABLE app.users OWNER TO user_service;

