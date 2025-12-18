SELECT * FROM members;
SELECT * FROM books;
SELECT * FROM borrows;

SELECT * FROM title_data;
SELECT * FROM unborrowed;
SELECT * FROM test;
DROP VIEW title_data;
DROP VIEW test;

SELECT isbn, COUNT(library_card_num) as borrowed FROM books
LEFT JOIN borrows using (book_id)
GROUP BY isbn