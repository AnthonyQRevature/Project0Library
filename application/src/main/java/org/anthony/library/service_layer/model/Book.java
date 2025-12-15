package org.anthony.library.service_layer.model;

import org.anthony.library.repository.entity.BookEntity;
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.util.LibraryLogger;
import org.anthony.tablePrinter.TablePrinter.Column;

public class Book {

    Integer bookId;
    Title title;

    public Book(BookEntity book, Title title) {
        LibraryLogger.LogAssertEq(book.getIsbn(), title.getIsbn(), "unexpected data missmatch");
        this.title = title;
        this.bookId = book.getBook_id();
    }

    public Book(Integer bookId, Integer isbn, String title) {
        this.bookId = bookId;
        this.title = new Title(isbn, title);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        Book other = (Book) obj;
        if (bookId == null) {
            if (other.bookId != null)
                return false;
        } else if (!bookId.equals(other.bookId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Column(name="book_id", weight=1)
    public Integer getBookId() { return bookId; }
    @Column(name="isbn", weight=3, width=20)
    public Integer getIsbn() { return title.getIsbn(); }
    @Column(name="title", weight=2, width=20)
    public String getTitle() { return title.getTitle(); }

    
}
