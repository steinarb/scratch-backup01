--liquibase formatted sql
--changeset sb:example_passwords
insert into password (user_id, username, password, salt) values (1, 'jd', 'secret', 'salt');
insert into password (user_id, username, password, salt) values (2, 'jad', 'secret', 'salt');
