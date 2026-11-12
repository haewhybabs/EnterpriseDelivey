INSERT INTO roles (name, created_at, updated_at) VALUES
    ('Sender', now(), now()),
    ('Driver', now(), now()),
    ('Admin', now(), now());

INSERT INTO delivery_statuses (name, created_at, updated_at) VALUES
    ('Awaiting delivery', now(), now()),
    ('Processing', now(), now()),
    ('In transit', now(), now()),
    ('Delivered', now(), now());