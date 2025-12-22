package org.anthony.library.service_layer.model;

import java.sql.Date;
import java.util.Objects;

import org.anthony.tablePrinter.TablePrinter.Column;

public class Borrow {

    Integer card_id;
    Book book;
    Date checkoutDate;
    Date dueDate;

    public int get_isbn() {return book.getIsbn();}

    @Column(name="title", weight=1, width=20)
    public String get_title() {return book.getTitle();}
    @Column(name="due date", weight=3, width=20)
    public Date get_due_date() {return dueDate;}

    public Borrow()
    {

    }

    public Borrow(Book book, Date checkoutDate, Date dueDate, int card_id) {
        this.book = book;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.card_id = card_id;
    }

    public int get_book_id() 
    {
        return book.getBookId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.card_id);
        hash = 79 * hash + Objects.hashCode(this.book);
        hash = 79 * hash + Objects.hashCode(this.checkoutDate);
        hash = 79 * hash + Objects.hashCode(this.dueDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Borrow other = (Borrow) obj;
        if (!Objects.equals(this.card_id, other.card_id)) {
            return false;
        }
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        if (!Objects.equals(this.checkoutDate, other.checkoutDate)) {
            return false;
        }
        return Objects.equals(this.dueDate, other.dueDate);
    }
}
