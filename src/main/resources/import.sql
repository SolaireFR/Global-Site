INSERT INTO main_users(role, creation, display_name, email, is_enabled, last_connection, locked, password, token, token_expiry_date) VALUES ('ROLE_ADMIN', '2024-06-12 20:47:39', 'admin', 'admin@test.com', 1, NULL, 0, '$2a$10$XAI6YZSJAIOpaQ6Cb4rgkubM5E8zoIvCUT4LRK3gw4fRKbXAIVhSy', NULL, NULL);
INSERT INTO main_users_data(email) VALUES ('admin@test.com');
INSERT INTO money_manager_users(user_data_id) VALUES (1);
INSERT INTO money_manager_bank_accounts(user_id, name) VALUES (1, 'Main'), (1, 'Livret A');
INSERT INTO money_manager_accumulators(user_id, name, amount, percent_per_month) VALUES (1, 'Sous-marin', 0, 10), (1, 'SSD', 0, 5), (1, 'Retraite', 0, 85);
INSERT INTO money_manager_labels(user_id, name) VALUES (1, 'Alimentation'), (1, 'Loisirs'), (1, 'Santé'), (1, 'Transport'), (1, 'Logement'), (1, 'Impôts'), (1, 'Salaire');