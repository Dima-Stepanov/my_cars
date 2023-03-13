--Создание таблицы с объявлениями
create table auto_user
(
    id           serial primary key,
    description  varchar   not null,
    created      timestamp not null,
    auto_user_id int       not null references auto_user (id)
);