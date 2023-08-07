DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'tasks_service') THEN

      RAISE NOTICE 'Role "tasks_service" already exists. Skipping.';
   ELSE
      CREATE ROLE user_service LOGIN PASSWORD 'q1w2e3r4';
   END IF;
END
$do$;

CREATE DATABASE task_service
            WITH OWNER = task_service
            ENCODING = 'UTF8'
            CONNECTION LIMIT = -1
            IS_TEMPLATE = False;

\c task_service task_service;

DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM information_schema.schemata
      WHERE  schema_name = 'app') THEN

      RAISE NOTICE 'Schema "app" already exists. Skipping.';
   ELSE
      CREATE SCHEMA app AUTHORIZATION task_service;
   END IF;
END
$do$;


--CREATE TABLE app.users
--    (
--        uuid uuid NOT NULL,
--        dt_create timestamp without time zone NOT NULL,
--        dt_update timestamp without time zone NOT NULL,
--        mail text NOT NULL,
--        fio text NOT NULL,
--        role text NOT NULL,
--        status text NOT NULL,
--        password text NOT NULL,
--        PRIMARY KEY (uuid),
--        UNIQUE (mail)
--    );
--
--ALTER TABLE app.users OWNER TO user_service;
--
--CREATE TABLE app.verification_code
--(
--    uuid uuid NOT NULL,
--    user_uuid uuid NOT NULL,
--    code text NOT NULL,
--    dt_create timestamp without time zone NOT NULL,
--    dt_update timestamp without time zone NOT NULL,
--    PRIMARY KEY (uuid),
--    FOREIGN KEY (user_uuid)
--        REFERENCES app.users (uuid) MATCH SIMPLE
--        ON UPDATE NO ACTION
--        ON DELETE NO ACTION
--        NOT VALID
--);
--
--ALTER TABLE IF EXISTS app.verification_code
--    OWNER to user_service;

