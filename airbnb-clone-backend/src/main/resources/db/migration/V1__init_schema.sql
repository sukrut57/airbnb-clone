-- V1__init_schema.sql
-- Initial schema for Airbnb Clone

CREATE SCHEMA IF NOT EXISTS airbnb_clone;

-- Users table
CREATE TABLE airbnb_clone.users (
                                    id                BIGSERIAL PRIMARY KEY,
                                    email             VARCHAR(255) NOT NULL UNIQUE,
                                    first_name        VARCHAR(255) NOT NULL,
                                    last_name         VARCHAR(255) NOT NULL,
                                    account_enabled   BOOLEAN NOT NULL DEFAULT FALSE,
                                    profile_picture_url VARCHAR(255),
                                    public_id         UUID NOT NULL UNIQUE,
                                    created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                    modified_at       TIMESTAMPTZ
);

-- Authority table (name is the primary key)
CREATE TABLE airbnb_clone.authority (
                                        name        VARCHAR(50) PRIMARY KEY,
                                        created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                        modified_at TIMESTAMPTZ
);

-- Join table (composite PK; cascade delete on user; restrict delete on authority)
CREATE TABLE airbnb_clone.user_authority (
                                             user_id        BIGINT NOT NULL
                                                 REFERENCES airbnb_clone.users(id) ON DELETE CASCADE,
                                             authority_name VARCHAR(50) NOT NULL
                                                 REFERENCES airbnb_clone.authority(name) ON DELETE RESTRICT ON UPDATE CASCADE,
                                             created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                             modified_at    TIMESTAMPTZ,
                                             PRIMARY KEY (user_id, authority_name)
);
