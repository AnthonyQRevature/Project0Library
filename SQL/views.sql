CREATE VIEW title_data AS
(
    SELECT title, titles.isbn, COUNT(books.book_id) AS num_copies
    FROM books NATURAL JOIN titles
    GROUP BY titles.isbn
    ORDER BY title ASC
);