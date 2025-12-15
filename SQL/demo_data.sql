INSERT INTO members(library_card, member_password, member_name) VALUES
(42, 'password1', 'Bill'),
(49, 'p@ssw0rd', 'Jackie'),
(96, 'myDog''sName', 'James');

INSERT INTO titles(isbn, title) VALUES
(123, 'To Kill A Mockingbird'),
(456, 'The Great Gatsby'),
(134, 'The Odyssey');

Insert INTO books(book_id, isbn) VALUES
(1, 123),
(2, 123),
(3, 123),
(4, 456),
(5, 456),
(6, 456),
(7, 134);

Insert INTO borrows(book_id, library_card_num, checkout_date, due_date) VALUES
(1, 42, '2025-11-7', '2025-12-20'),
(4, 42, '2025-12-3', '2025-12-11'),
(2, 49, '2025-12-1', '2025-12-12'),
(3, 96, '2025-11-20', '2025-12-3'),
(5, 96, '2025-11-25', '2025-12-18');

INSERT INTO genres(genre_id, genre_name) VALUES
(1, 'Adventure'),
(2, 'American'),
(3, 'Classic');

INSERT INTO categories(isbn, genre_id) VALUES
(123, 2),
(123, 3),
(456, 2),
(456, 3),
(134, 1),
(134, 3);
