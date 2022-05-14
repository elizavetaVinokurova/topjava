DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

insert into meals(user_id, datetime, description, calories) values (100000, '2022-05-12 08:30', 'chicken meatballs', 700);
insert into meals(user_id, datetime, description, calories) values (100000, '2022-05-13 20:24', 'carbonara', 800);
insert into meals(user_id, datetime, description, calories) values (100001, '2022-05-14 06:30', 'pizza', 999);
insert into meals(user_id, datetime, description, calories) values (100001, '2022-05-14 16:00','hamburger', 450);


