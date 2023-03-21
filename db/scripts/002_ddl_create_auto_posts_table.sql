--Создание таблицы с объявлениями
create table auto_posts
(
    id           serial primary key,
    description  varchar   not null,
    created      timestamp not null default now(),
    auto_user_id int       not null references auto_users (id)
);