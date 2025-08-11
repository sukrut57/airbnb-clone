CREATE SCHEMA IF NOT EXISTS airbnb_clone;

CREATE TABLE airbnb_clone.users(
    id SERIAL PRIMARY KEY ,
    email VARCHAR(255) NOT NULL ,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) NOT NULL ,
    account_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    profile_picture_url VARCHAR(255),
    public_id UUID NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITH TIME ZONE
);


CREATE TABLE airbnb_clone.authorityEntity(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITH TIME ZONE
);




CREATE TABLE airbnb_clone.user_authority(
                                            id SERIAL PRIMARY KEY ,
                                            user_id INTEGER NOT NULL REFERENCES airbnb_clone.users(id) ON DELETE CASCADE,
                                            authority_id INTEGER NOT NULL REFERENCES airbnb_clone.authorityEntity(id) ON DELETE CASCADE,
                                            created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            modified_at TIMESTAMP WITH TIME ZONE,
                                            UNIQUE (user_id, authority_id)


);