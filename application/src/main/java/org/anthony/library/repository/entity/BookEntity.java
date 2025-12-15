package org.anthony.library.repository.entity;

import org.anthony.tablePrinter.TablePrinter.Column;

public class BookEntity {
    private Integer book_id;
    private Integer isbn;

    @Override
    public String toString() {
        return "Book [book_id=" + book_id + ", isbn=" + isbn + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((book_id == null) ? 0 : book_id.hashCode());
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookEntity other = (BookEntity) obj;
        if (book_id == null) {
            if (other.book_id != null)
                return false;
        } else if (!book_id.equals(other.book_id))
            return false;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        return true;
    }

    public BookEntity() {
    }

    public BookEntity(Integer book_id, Integer isbn) {
        this.book_id = book_id;
        this.isbn = isbn;
    }

    @Column(name="book_id", weight=1)
    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    @Column(name="isbn", weight=2)
    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }
}
