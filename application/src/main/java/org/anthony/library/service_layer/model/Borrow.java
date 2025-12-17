package org.anthony.library.service_layer.model;

import java.sql.Date;

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
}
