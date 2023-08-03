CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO app.users(
	uuid, dt_create, dt_update, mail, fio, role, status, password)
	VALUES (uuid_generate_v4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@admin.god', 'Admin', 'ADMIN', 'ACTIVATED', 'admin');