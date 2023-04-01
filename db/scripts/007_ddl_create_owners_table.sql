--Таблица содержит справочник владельцев автомобилей.
create table owners
(
    id serial primary key,
    name varchar,
    user_id int not null unique references auto_users(id)
);