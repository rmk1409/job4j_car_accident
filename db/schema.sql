DROP TABLE IF EXISTS accident_rule;
DROP TABLE IF EXISTS accident;
DROP TABLE IF EXISTS accident_type;
DROP TABLE IF EXISTS rule;

CREATE TABLE IF NOT EXISTS rule
(
    id   serial primary key,
    name varchar(2000)
);

CREATE TABLE IF NOT EXISTS accident_type
(
    id   serial primary key,
    name varchar(2000)
);

CREATE TABLE IF NOT EXISTS accident
(
    id      serial primary key,
    name    varchar(2000),
    text    varchar(2000),
    address varchar(2000),
    type_id int references accident_type (id)
);

CREATE TABLE IF NOT EXISTS accident_rule
(
    id          serial primary key,
    accident_id int references accident (id),
    rule_id     int references rule (id)
);

INSERT INTO accident_type (name)
values ('Две машины');
INSERT INTO accident_type (name)
values ('Машина и человек');
INSERT INTO accident_type (name)
values ('Машина и велосипед');

INSERT INTO rule (name)
values ('Статья. 1');
INSERT INTO rule (name)
values ('Статья. 2');
INSERT INTO rule (name)
values ('Статья. 3');

INSERT INTO accident (name, text, address, type_id)
VALUES ('name1', 'text1', 'address1', 1);
INSERT INTO accident (name, text, address, type_id)
VALUES ('name2', 'text2', 'address2', 1);
INSERT INTO accident (name, text, address, type_id)
VALUES ('name3', 'text3', 'address3', 2);
INSERT INTO accident (name, text, address, type_id)
VALUES ('name4', 'text4', 'address4', 3);

INSERT INTO accident_rule (accident_id, rule_id)
VALUES (1, 1);
INSERT INTO accident_rule (accident_id, rule_id)
VALUES (2, 1);
INSERT INTO accident_rule (accident_id, rule_id)
VALUES (2, 2);
INSERT INTO accident_rule (accident_id, rule_id)
VALUES (3, 3);
INSERT INTO accident_rule (accident_id, rule_id)
VALUES (4, 1);
INSERT INTO accident_rule (accident_id, rule_id)
VALUES (4, 3);
