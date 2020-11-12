CREATE DATABASE bank;


-- For the sake of simplicity, and to be respectful to the problem description.
-- I'm using the auto-incremented `id` field as bank account. For obvious reasons,
-- this is not production grade code.
CREATE TABLE IF NOT EXISTS accounts (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(128) NOT NULL,
	balance DECIMAL(8, 2) DEFAULT 0,
	PRIMARY KEY (id)
) CHARSET=utf8;
