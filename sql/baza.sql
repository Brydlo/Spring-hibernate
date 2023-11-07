/* Dodatkowe tabele i widoki na potrzeby konfiguracji jdbcAuthentication,
 * czyli przechowywanie użytkowników i haseł w bazie danych.
 */

DROP VIEW IF EXISTS spring_account_roles;
DROP VIEW IF EXISTS spring_accounts;

DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	user_id INTEGER NOT NULL,
	username VARCHAR(30) NOT NULL,
	password VARCHAR(100) NOT NULL,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	-- enabled BOOLEAN NOT NULL,
    
	PRIMARY KEY(user_id),
	UNIQUE(username)
);

CREATE TABLE user_roles (
	user_id INTEGER NOT NULL,
	role VARCHAR(20) NOT NULL,
	PRIMARY KEY (user_id, role),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE VIEW spring_accounts AS
SELECT username, '{noop}' || password AS password, 1 AS enabled
FROM users;

CREATE VIEW spring_account_roles AS
SELECT username, 'ROLE_' || role AS role
FROM user_roles JOIN users USING(user_id);



INSERT INTO users(user_id, username, password, first_name, last_name) VALUES (1, 'adam', 'abc123', 'Adam', 'Abacki');
INSERT INTO users(user_id, username, password, first_name, last_name) VALUES (2, 'bartek', 'abc123', 'Bartosz', 'Borecki');
INSERT INTO users(user_id, username, password, first_name, last_name) VALUES (3, 'damian', 'abc123', 'Damian', 'Domyślny');

INSERT INTO user_roles(user_id, role) VALUES (1, 'manager');
INSERT INTO user_roles(user_id, role) VALUES (1, 'inna_rola');
INSERT INTO user_roles(user_id, role) VALUES (2, 'pomocnik');


-- SELECT * FROM users;
-- SELECT * FROM users LEFT JOIN user_roles USING(user_id);
--
-- SELECT username, password, enabled FROM spring_accounts;
-- SELECT username, role FROM spring_account_roles;


