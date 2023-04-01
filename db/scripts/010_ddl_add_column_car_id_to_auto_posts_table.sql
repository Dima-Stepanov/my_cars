--Добавление в таблицу auto_posts связи с таблицей cars
--через добавление поля car_id
ALTER TABLE auto_posts ADD COLUMN
car_id INT NOT NULL REFERENCES cars(id);