--Создание таблицы изменение цены объявления
create table price_history
(
    id      serial primary key,
    before  bigint not null,
    after   bigint not null,
    created timestamp with time zone default now(),
    post_id int    not null references auto_posts (id)
);