-- MySQL initialization script for schema "seminar_jdo"
-- run this after structure definition
-- best import using client command "source <path to this file>"
USE seminar_jdo;
SET CHARACTER SET utf8mb4;
BEGIN;

INSERT INTO Seminar VALUES (0, "2018-10-21", "Hamburg", "Java", "Eine kleine Einfuehrung in Java");
INSERT INTO Seminar VALUES (0, "2018-12-12", "Muenchen", "Java DB", "Vertiefung von Java und Datenbanken");
INSERT INTO Seminar VALUES (0, "2019-03-18", "Hamburg", "C++", "Einfuehrung in die Programmierung mit C++");
INSERT INTO Seminar VALUES (0, "2019-12-07", "Duesseldorf", "JavaScript", "Internetprogrammierung leicht gemacht");
INSERT INTO Seminar VALUES (0, "2020-06-03", "Berlin", "HTML", "Eigene Internetseiten Ruck-Zuck erstellen");

COMMIT;
SELECT * FROM Seminar;
