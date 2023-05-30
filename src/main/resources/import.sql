create sequence hibernate_sequence start with 1 increment by 1;

INSERT INTO person(id, name, birth, eyes) VALUES (nextval('hibernate_sequence'), 'Farid Ulyanov', '1974-08-15', 'BLUE');
INSERT INTO person(id, name, birth, eyes) VALUES (nextval('hibernate_sequence'), 'Salvador L. Witcher', '1984-05-24', 'BROWN');
INSERT INTO person(id, name, birth, eyes) VALUES (nextval('hibernate_sequence'), 'Kim Hu', '1999-04-25', 'HAZEL');