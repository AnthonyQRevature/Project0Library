CREATE VIEW title_data AS
(
    SELECT title, titles.isbn, COUNT(books.book_id) AS num_copies
    FROM books NATURAL JOIN titles
    GROUP BY titles.isbn
    ORDER BY title ASC
);

CREATE VIEW title_data AS
(
    SELECT lq.title, lq.isbn, lq.num_copies, rq.genre
    FROM
    (
        SELECT title, titles.isbn, COUNT(books.book_id) AS num_copies
        FROM books INNER JOIN titles USING (isbn)
        GROUP BY titles.isbn
    ) AS lq JOIN
    (
        SELECT titles.isbn, STRING_AGG(genres.genre_name, ', ') AS genre
        FROM titles INNER JOIN categories USING (isbn)
        INNER JOIN genres USING (genre_id)
        GROUP BY titles.isbn
    ) AS rq USING (isbn)
    ORDER BY title ASC
);