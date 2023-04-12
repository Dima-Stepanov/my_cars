--Заполнение таблицы цены товаров
INSERT INTO price_history(before, after, created, post_id)
SELECT 1200500, 1100500, po.created, po.id
FROM auto_posts AS po
WHERE po.description = 'срочная продажа' LIMIT 1;
--
INSERT INTO price_history(before, after, post_id)
SELECT 1100500, 1000500, now(), po.id
FROM auto_posts AS po
WHERE po.description = 'срочная продажа' LIMIT 1;
--
INSERT INTO price_history(before, after, post_id)
SELECT 944300, 0, po.created, po.id
FROM auto_posts AS po
WHERE po.description = 'продаю своё' LIMIT 1;
--
INSERT INTO price_history(before, after, post_id)
SELECT 700700, 700500, po.created, po.id
FROM auto_posts AS po
WHERE po.description = 'Сам бы ездил, но срочно нужны деньги' LIMIT 1;
--
INSERT INTO price_history(before, after, post_id)
SELECT 3003003, 0, po.created, po.id
FROM auto_posts AS po
WHERE po.description = 'Без пробега по РФ';