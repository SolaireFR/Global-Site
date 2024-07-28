CREATE DATABASE IF NOT EXISTS global_site_db;

CREATE USER IF NOT EXISTS 'springuser'@'%' IDENTIFIED BY 'springpassword';

GRANT ALL PRIVILEGES ON global_site_db.* TO 'springuser'@'%';

GRANT PROXY ON 'root'@'%' TO 'springuser'@'%' WITH GRANT OPTION;