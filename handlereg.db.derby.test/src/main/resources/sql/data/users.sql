--liquibase formatted sql
--changeset sb:example_users
insert into users (username, email, firstname, lastname) values ('jd', 'johndoe21@gmail.com', 'John', 'Doe');
insert into users (username, email, firstname, lastname) values ('jad', 'janedoe21@gmail.com', 'Jane', 'Doe');
