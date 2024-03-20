CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    version INTEGER,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    city VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    phone_number VARCHAR UNIQUE NOT NULL,
    active BOOLEAN NOT NULL,
    create_time TIMESTAMP NOT NULL,
    modify_time TIMESTAMP
);

ALTER SEQUENCE users_id_seq INCREMENT BY 50;