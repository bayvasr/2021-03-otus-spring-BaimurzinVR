insert into authors (name) values ('Айзек Азимов');
insert into authors (name) values ('Тургенев И.С.');
insert into genres (name) values ('Фантастика');
insert into genres (name) values ('Роман');
insert into books (title, author_id, genre_id) values ('Я, робот', 1, 1);
insert into books (title, author_id, genre_id) values ('Пикник на обочине', 0, 1);
insert into books (title, author_id, genre_id) values ('Отцы и дети', 0, 0);
insert into comments(book_id, text) values (1, 'New comment');
insert into comments(book_id, text) values (1, 'Old comment');
