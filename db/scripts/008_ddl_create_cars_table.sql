--Таблица модели автомобиля cars
create table cars
(
  id serial primary key,
  name varchar,
  engine_id int references engines(id)
);