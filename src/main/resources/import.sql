INSERT INTO user (email, password, enabled) VALUES ('user@example.com', 'password', true);
INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);