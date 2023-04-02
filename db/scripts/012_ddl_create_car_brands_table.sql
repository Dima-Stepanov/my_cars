--Таблица справочников марок автомобилей
create table car_brands
(
    id serial primary key,
    name not null varchar unique
);