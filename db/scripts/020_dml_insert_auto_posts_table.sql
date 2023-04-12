--Наполнение таблицы auto_posts данными
INSERT INTO auto_posts(description, created, done, user_id, car_id)
VALUES ('срочная продажа', '2023-01-25 18:19', null, 1, 1);
INSERT INTO auto_posts(description, created, done, user_id, car_id)
VALUES ('продаю своё', '2023-03-15 09:19', null, 2, 2);
INSERT INTO auto_posts(description, created, done, user_id, car_id)
VALUES ('Сам бы ездил, но срочно нужны деньги', '2022-11-10 23:54', now(), 3, 3);
INSERT INTO auto_posts(description, created, done, user_id, car_id)
VALUES ('Без пробега по РФ', now(), null, 2, 4);