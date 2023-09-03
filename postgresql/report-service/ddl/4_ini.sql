DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'report_service') THEN

      RAISE NOTICE 'Role "report_service" already exists. Skipping.';
   ELSE
      CREATE ROLE report_service LOGIN PASSWORD 'q1w2e3r4';
   END IF;
END
$do$;

CREATE DATABASE report_service
            WITH OWNER = report_service
            ENCODING = 'UTF8'
            CONNECTION LIMIT = -1
            IS_TEMPLATE = False;

\c report_service report_service;

DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM information_schema.schemata
      WHERE  schema_name = 'app') THEN

      RAISE NOTICE 'Schema "app" already exists. Skipping.';
   ELSE
      CREATE SCHEMA app AUTHORIZATION report_service;
   END IF;
END
$do$;

CREATE TABLE app.report
(
    uuid uuid,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    status text NOT NULL,
    type text NOT NULL,
    description text NOT NULL,
    location text,
    PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.report
    OWNER to report_service;

CREATE TABLE app.report_param_audit
(
    report_id uuid NOT NULL,
    "user" uuid,
    "from" timestamp without time zone,
    "to" timestamp without time zone,
    FOREIGN KEY (report_id)
        REFERENCES app.report (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS app.report_param_audit
    OWNER to report_service;