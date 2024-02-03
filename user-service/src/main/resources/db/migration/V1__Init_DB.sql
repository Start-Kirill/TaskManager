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

CREATE TABLE app.verification
(
    uuid uuid,
    user_uuid uuid NOT NULL,
    url text NOT NULL,
    code text NOT NULL,
    status text NOT NULL,
    attempt bigint NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    PRIMARY KEY (uuid),
    UNIQUE (user_uuid),
    FOREIGN KEY (user_uuid)
        REFERENCES app.users (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS app.verification
    OWNER to user_service;

