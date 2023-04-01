--Таблица содержит историю владения автомобилем конкретным владельцем.
create table history_owner
(
    id serial primary key,
    car_id int not null references cars(id),
    owner_id int not null references owners(id),
    startAt timestamp not null,
    endAt timestamp
);