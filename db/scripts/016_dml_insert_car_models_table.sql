--Наполнение таблица справочной информацией моделей автомобиле
--Toyota
insert into car_models(name, brand_id)
select 'Camry', cb.id from car_brands as cb where cb.name = 'Toyota' limit 1;
insert into car_models(name, brand_id)
select 'Corolla', cb.id from car_brands as cb where cb.name = 'Toyota' limit 1;
insert into car_models(name, brand_id)
select 'Land Cruiser Prado', cb.id from car_brands as cb where cb.name = 'Toyota' limit 1;
insert into car_models(name, brand_id)
select 'RAV4', cb.id from car_brands as cb where cb.name = 'Toyota' limit 1;
insert into car_models(name, brand_id)
select 'Land Cruiser', cb.id from car_brands as cb where cb.name = 'Toyota' limit 1;
--Лада
insert into car_models(name, brand_id)
select 'Гранта', cb.id from car_brands as cb where cb.name = 'Лада' limit 1;
insert into car_models(name, brand_id)
select 'Приора', cb.id from car_brands as cb where cb.name = 'Лада' limit 1;
insert into car_models(name, brand_id)
select '2114 Самара', cb.id from car_brands as cb where cb.name = 'Лада' limit 1;
insert into car_models(name, brand_id)
select '2107', cb.id from car_brands as cb where cb.name = 'Лада' limit 1;
insert into car_models(name, brand_id)
select '4х4 2121 Нива', cb.id from car_brands as cb where cb.name = 'Лада' limit 1;
--Nissan
insert into car_models(name, brand_id)
select 'X-Trail', cb.id from car_brands as cb where cb.name = 'Nissan' limit 1;
insert into car_models(name, brand_id)
select 'Note', cb.id from car_brands as cb where cb.name = 'Nissan' limit 1;
insert into car_models(name, brand_id)
select 'Qashqai', cb.id from car_brands as cb where cb.name = 'Nissan' limit 1;
insert into car_models(name, brand_id)
select 'Juke', cb.id from car_brands as cb where cb.name = 'Nissan' limit 1;
insert into car_models(name, brand_id)
select 'Teana', cb.id from car_brands as cb where cb.name = 'Nissan' limit 1;
--Honda
insert into car_models(name, brand_id)
select 'Fit', cb.id from car_brands as cb where cb.name = 'Honda' limit 1;
insert into car_models(name, brand_id)
select 'Freed', cb.id from car_brands as cb where cb.name = 'Honda' limit 1;
insert into car_models(name, brand_id)
select 'Accord', cb.id from car_brands as cb where cb.name = 'Honda' limit 1;
insert into car_models(name, brand_id)
select 'CR-V', cb.id from car_brands as cb where cb.name = 'Honda' limit 1;
insert into car_models(name, brand_id)
select 'Vezel', cb.id from car_brands as cb where cb.name = 'Honda' limit 1;
--Hyundai
insert into car_models(name, brand_id)
select 'Solaris', cb.id from car_brands as cb where cb.name = 'Hyundai' limit 1;
insert into car_models(name, brand_id)
select 'Santa Fe', cb.id from car_brands as cb where cb.name = 'Hyundai' limit 1;
insert into car_models(name, brand_id)
select 'Tucson', cb.id from car_brands as cb where cb.name = 'Hyundai' limit 1;
insert into car_models(name, brand_id)
select 'Creta', cb.id from car_brands as cb where cb.name = 'Hyundai' limit 1;
insert into car_models(name, brand_id)
select 'Sonata', cb.id from car_brands as cb where cb.name = 'Hyundai' limit 1;