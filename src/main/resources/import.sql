INSERT INTO users (creation, display_name, email, is_enabled, last_connection, locked, password) VALUES ('2024-05-18 00:52:11', 'jesappellegrootdu59', 'jesappellegrootdu59@gmail.com', 1, NULL, 0, '$2a$10$XAI6YZSJAIOpaQ6Cb4rgkubM5E8zoIvCUT4LRK3gw4fRKbXAIVhSy'), ('2024-06-12 20:47:39', 'admin', 'admin@test.com', 1, NULL, 0, '$2a$10$XAI6YZSJAIOpaQ6Cb4rgkubM5E8zoIvCUT4LRK3gw4fRKbXAIVhSy');
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1), (2, 2);
