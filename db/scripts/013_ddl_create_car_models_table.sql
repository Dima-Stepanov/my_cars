--Таблица содержит справочник моделей автомобиля привязанных к конкретной марке.
create table car_models
(
    id serial primary key,
    name varchar not null,
    brand_id int not null references car_brands(id)
);