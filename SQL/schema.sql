CREATE TABLE IF NOT EXISTS members (
    library_card int PRIMARY KEY,
    member_password VARCHAR(50) NOT NULL, -- plaintext password for simplicity
    member_name VARCHAR(50) NOT NULL
);

CREATE TABLE if NOT EXISTS phone_numbers (
    phone_number_id serial NOT NULL PRIMARY KEY,
    library_card_num int REFERENCES members(library_card),
    phone_number int NOT NULL
);

CREATE TABLE IF NOT EXISTS email_addresses (
    email_address_id serial NOT NULL PRIMARY KEY,
    library_card_num int REFERENCES members(library_card),
    email_address VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS titles (
    isbn INT NOT NULL PRIMARY KEY,
    title VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    book_id SERIAL NOT NULL PRIMARY KEY,
    isbn int NOT NULL REFERENCES titles(isbn)
);

CREATE TABLE if NOT EXISTS borrows (
    book_id INT REFERENCES books(book_id) PRIMARY KEY,
    library_card_num INT NOT NULL REFERENCES members(library_card),
    checkout_date DATE NOT NULL,
    due_date DATE NOT NULL
    -- fine is calculated
);
CREATE TABLE if NOT EXISTS genres (
    genre_id SERIAL PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE if NOT EXISTS categories (
    isbn INT REFERENCES titles(isbn),
    genre_id INT NOT NULL REFERENCES genres(genre_id),
    PRIMARY KEY (isbn, genre_id)
);