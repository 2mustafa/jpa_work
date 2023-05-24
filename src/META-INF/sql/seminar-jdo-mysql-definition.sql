-- MySQL structure script for schema "seminar_jdo"
-- best import using client command "source <path to this file>"
DROP DATABASE IF EXISTS seminar_jdo;
CREATE DATABASE seminar_jdo;
USE seminar_jdo;

CREATE TABLE Seminar (
	seminar_id INTEGER NOT NULL AUTO_INCREMENT,
	termin DATE NOT NULL,
	ort VARCHAR(30) NOT NULL,
	thema VARCHAR(30) NOT NULL,
	beschreibung VARCHAR(250) NOT NULL,
	PRIMARY KEY (seminar_id)
);