DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'task_manager') THEN

      RAISE NOTICE 'Role "task_manager" already exists. Skipping.';
   ELSE
      CREATE ROLE task_manager LOGIN PASSWORD 'q1w2e3r4';
   END IF;
   IF EXISTS (
      SELECT FROM information_schema.schemata
      WHERE  schema_name = 'app') THEN

      RAISE NOTICE 'Schema "app" already exists. Skipping.';
   ELSE
      CREATE SCHEMA app AUTHORIZATION task_manager;
   END IF;
END
$do$;

CREATE TABLE app.users
    (
        uuid uuid NOT NULL,
        dt_create timestamp without time zone NOT NULL,
        dt_update timestamp without time zone NOT NULL,
        mail text,
        fio text,
        role text,
        status text,
        password text,
        PRIMARY KEY (uuid),
        UNIQUE (mail),
        UNIQUE (uuid)

    );

ALTER TABLE app.users OWNER TO task_manager;

