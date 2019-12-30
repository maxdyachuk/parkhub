CREATE TABLE IF NOT EXISTS "park_hub".uuid_token_type (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    type VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 'true'
);

CREATE TABLE IF NOT EXISTS "park_hub".uuid_token (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT NOT NULL REFERENCES park_hub.user(id),
	token VARCHAR(36) NOT NULL,
	expiration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    token_type_id BIGINT NOT NULL REFERENCES park_hub.uuid_token_type(id)
);