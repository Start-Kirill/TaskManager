\c user_service user_service;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO app.users(
	uuid, dt_create, dt_update, mail, fio, role, status, password)
	VALUES (uuid_generate_v4(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@admin.com', 'Admin', 'ADMIN', 'ACTIVATED', '$2a$10$mU1rCt.YXVpzhpnirXDMPOcA6yHrZgxwVa3qWNUtw8m92YWLOinJO');