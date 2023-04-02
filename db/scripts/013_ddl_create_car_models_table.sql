--Таблица содержит справочник моделей автомобиля привязанных к конкретной марке.
create table car_models
(
    id serial primary key,
    name not null varchar,
    brand_id not null references car_brands(id)
);