CREATE USER task_manager WITH PASSWORD 'q1w2e3r4';

SET ROLE task_manager;

CREATE SCHEMA app
    AUTHORIZATION task_manager;

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
        PRIMARY KEY (uuid)
    );

