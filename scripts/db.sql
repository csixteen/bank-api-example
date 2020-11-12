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


-- Withdraw_money

DROP PROCEDURE IF EXISTS Withdraw_money;

DELIMITER //
CREATE PROCEDURE Withdraw_money(IN account_id INT, IN amount DECIMAL)
BEGIN
	DECLARE i INT;

	START TRANSACTION;
	SELECT COUNT(*) INTO i FROM accounts WHERE id = account_id AND balance >= amount;
	IF i > 0 THEN
		UPDATE accounts SET balance = balance - amount WHERE id = account_id;
	END IF;
	COMMIT WORK;
END//
DELIMITER ;


-- Transfer money

DROP PROCEDURE IF EXISTS Transfer_money;

DELIMITER //
CREATE PROCEDURE Transfer_money(IN src_account INT, IN dst_ACCOUNT INT, IN amount DECIMAL)
BEGIN
	DECLARE i INT;
	DECLARE j INT;

	START TRANSACTION;
	SELECT COUNT(*) INTO i FROM accounts WHERE id = src_account AND balance >= amount;
	SELECT COUNT(*) INTO j FROM accounts WHERE id = dst_account;
	if i > 0 AND j > 0 THEN
		UPDATE accounts SET balance = balance - amount WHERE id = src_account;
		UPDATE accounts SET balance = balance + amount WHERE id = dst_account;
	END IF;
	COMMIT WORK;
END//
DELIMITER ;
