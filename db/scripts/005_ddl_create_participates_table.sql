--Таблицы хранения подписок на объявление разных пользователей
create table participates
(
    id      serial primary key,
    user_id int not null references auto_users (id),
    post_id int not null references auto_posts (id)
)