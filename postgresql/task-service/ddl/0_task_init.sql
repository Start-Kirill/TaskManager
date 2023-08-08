DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'task_service') THEN

      RAISE NOTICE 'Role "task_service" already exists. Skipping.';
   ELSE
      CREATE ROLE task_service LOGIN PASSWORD 'q1w2e3r4';
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


CREATE TABLE app.project
(
    uuid uuid,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    name text,
    description text,
    manager uuid,
    staff uuid[],
    status text,
    PRIMARY KEY (uuid),
    UNIQUE (name)
);

ALTER TABLE IF EXISTS app.project
    OWNER to task_service;

CREATE TABLE app.task
(
    uuid uuid,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    project_uuid uuid NOT NULL,
    title text NOT NULL,
    description text,
    status text,
    implementer uuid,
    PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.task
    OWNER to task_service;
