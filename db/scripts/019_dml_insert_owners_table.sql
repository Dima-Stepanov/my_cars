--Наполнение таблицы владельцами автомобилей
INSERT INTO owners(name, user_id)
SELECT us.login, us.id
FROM auto_users AS us
WHERE us.login = 'Ivanov'
limit 1;
INSERT INTO owners(name, user_id)
SELECT us.login, us.id
FROM auto_users AS us
WHERE us.login = 'Petrov'
limit 1;
INSERT INTO owners(name, user_id)
SELECT us.login, us.id
FROM auto_users AS us
WHERE us.login = 'Sidorov'
limit 1;