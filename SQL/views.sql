CREATE VIEW title_data AS
(
    SELECT book_count.title, book_count.isbn, book_count.num_copies - borrowed_count.borrowed AS num_copies, agg_genres.genre
    FROM
    (
        SELECT title, titles.isbn, COUNT(books.book_id) AS num_copies
        FROM books INNER JOIN titles USING (isbn)
        GROUP BY titles.isbn
    ) AS book_count INNER JOIN
    (
        SELECT titles.isbn, STRING_AGG(genres.genre_name, ', ') AS genre
        FROM titles INNER JOIN categories USING (isbn)
        INNER JOIN genres USING (genre_id)
        GROUP BY titles.isbn
    ) AS agg_genres USING (isbn) INNER JOIN
    (
        SELECT isbn, COUNT(library_card_num) as borrowed FROM books
        LEFT JOIN borrows using (book_id)
        GROUP BY isbn
    ) AS borrowed_count USING (isbn)
    WHERE book_count.num_copies - borrowed_count.borrowed > 0
    ORDER BY title ASC
);

CREATE VIEW unborrowed AS
(
    SELECT * FROM books
    WHERE book_id NOT IN
    (
        SELECT book_id FROM borrows
    )
);