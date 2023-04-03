--Таблица справочников марок автомобилей
create table car_brands
(
    id serial primary key,
    name varchar not null unique
);