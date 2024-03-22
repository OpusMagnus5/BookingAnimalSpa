CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    version INTEGER,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    phone_number VARCHAR UNIQUE NOT NULL,
    active BOOLEAN NOT NULL,
    create_time TIMESTAMP NOT NULL,
    modify_time TIMESTAMP
);

ALTER SEQUENCE users_id_seq INCREMENT BY 50;
CREATE UNIQUE INDEX users_id_idx ON users (id);
CREATE UNIQUE INDEX users_username_idx ON users (username);
CREATE UNIQUE INDEX users_email_idx ON users (email);
CREATE UNIQUE INDEX users_phone_number_idx ON users (phone_number);