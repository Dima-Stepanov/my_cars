--таблица содержит данные о файлах используемых приложением
create table files
(
    id serial primary key,
    name not null varchar,
    path not null varchar unique,
    post_id int not null references auto_posts(id)
);