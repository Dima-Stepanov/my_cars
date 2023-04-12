--Наполнение таблицы CARS данными
--Toyota
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Toyota ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, бензин'
  AND mo.name = 'Camry'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Toyota ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, бензин'
  AND mo.name = 'Corolla'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Toyota ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, дизель'
  AND mo.name = 'Land Cruiser Prado'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Toyota ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, бензин'
  AND mo.name = 'RAV4'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Toyota ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, дизель'
  AND mo.name = 'Land Cruiser'
limit 1;
--Лада
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Лада ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, бензин'
  AND mo.name = 'Гранта'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Лада ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, бензин'
  AND mo.name = 'Приора'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Лада ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, бензин'
  AND mo.name = '2114 Самара'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Лада ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, дизель'
  AND mo.name = '2107'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Лада ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, дизель'
  AND mo.name = '4х4 2121 Нива'
limit 1;
--Nissan
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Nissan ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, дизель'
  AND mo.name = 'X-Trail'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Nissan ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, бензин'
  AND mo.name = 'Note'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Nissan ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, дизель'
  AND mo.name = 'Qashqai'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Nissan ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, бензин'
  AND mo.name = 'Juke'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Nissan ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, бензин'
  AND mo.name = 'Teana'
limit 1;
--Honda
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Honda ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, бензин'
  AND mo.name = 'Fit'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Honda ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, бензин'
  AND mo.name = 'Freed'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Honda ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, бензин'
  AND mo.name = 'Accord'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Honda ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, дизель'
  AND mo.name = 'CR-V'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Honda ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, дизель'
  AND mo.name = 'Vezel'
limit 1;
--Hyundai
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Hyundai ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, бензин'
  AND mo.name = 'Solaris'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Hyundai ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, бензин'
  AND mo.name = 'Santa Fe'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Hyundai ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '2.0 л, бензин'
  AND mo.name = 'Tucson'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Hyundai ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.0 л, дизель'
  AND mo.name = 'Creta'
limit 1;
INSERT INTO cars(name, engine_id, model_id)
SELECT CONCAT('Hyundai ', mo.name, ', ', en.name), en.id, mo.id
FROM engines AS en,
     car_models AS mo
WHERE en.name = '1.6 л, дизель'
  AND mo.name = 'Sonata'
limit 1;

