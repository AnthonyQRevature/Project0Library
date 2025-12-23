SELECT * FROM members;
SELECT * FROM books;
SELECT * FROM borrows;

SELECT * FROM title_data;
SELECT * FROM unborrowed;
SELECT * FROM test;
DROP VIEW title_data;
DROP VIEW unborrowed;

SELECT library_card_num, isbn FROM borrows
INNER JOIN books USING (book_id)
WHERE library_card_num = 42 AND isbn = 456;