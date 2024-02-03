DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'audit_service') THEN

      RAISE NOTICE 'Role "audit_service" already exists. Skipping.';
   ELSE
      CREATE ROLE audit_service LOGIN PASSWORD 'q1w2e3r4';
   END IF;
END
$do$;

CREATE DATABASE audit_service
        WITH OWNER = audit_service
        ENCODING = 'UTF8'
        CONNECTION LIMIT = -1
        IS_TEMPLATE = False;

\c audit_service audit_service;

DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM information_schema.schemata
      WHERE  schema_name = 'app') THEN

      RAISE NOTICE 'Schema "app" already exists. Skipping.';
   ELSE
      CREATE SCHEMA app AUTHORIZATION audit_service;
   END IF;
END
$do$;

CREATE TABLE app.audit
(
    uuid uuid,
    dt_create timestamp without time zone NOT NULL,
    user_uuid uuid,
    user_mail text,
    user_fio text,
    user_role text NOT NULL,
    text text NOT NULL,
    type text NOT NULL,
    id text NOT NULL,
    PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.audit
    OWNER to audit_service;

CREATE TABLE app.limits
(
    uuid uuid,
    expense_category text NOT NULL,
    sum numeric(2) NOT NULL,
    currency_uuid uuid NOT NULL,
    datetime timestamp without time zone NOT NULL,
    PRIMARY KEY (uuid),
    FOREIGN KEY (currency_uuid)
        REFERENCES app.currencies (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS app.limits
    OWNER to expense_manager

