--таблица содержит данные о файлах используемых приложением
create table files
(
    id serial primary key,
    name varchar not null ,
    path varchar not null unique,
    post_id int not null references auto_posts(id)
);