--Добавление в таблицу cars связи ManyToOne с таблицей car_models
ALTER TABLE cars ADD COLUMN model_id NOT NULL REFERENCES car_models(id);