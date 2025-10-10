-- V2__seed_authorities.sql
-- Preload default authorities

INSERT INTO airbnb_clone.authority (name, created_at, modified_at)
VALUES
    ('ROLE_USER',   NOW(), NOW()),
    ('ROLE_TENANT', NOW(), NOW())
    ON CONFLICT (name) DO NOTHING;
