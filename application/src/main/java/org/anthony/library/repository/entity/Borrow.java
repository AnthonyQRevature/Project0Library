package org.anthony.library.repository.entity;

import java.sql.Date;
import java.util.Objects;

public class Borrow {
    Integer book_id;
    Integer library_card_number;
    Date checkout_date;
    Date due_date;

    public Borrow(Integer book_id, Integer library_card_number, Date checkout_date, Date due_date) {
        this.book_id = book_id;
        this.library_card_number = library_card_number;
        this.checkout_date = checkout_date;
        this.due_date = due_date;
    }

    public Borrow() {
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getLibrary_card_number() {
        return library_card_number;
    }

    public void setLibrary_card_number(Integer library_card_number) {
        this.library_card_number = library_card_number;
    }

    public Date getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Date checkout_date) {
        this.checkout_date = checkout_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Borrow{");
        sb.append("book_id=").append(book_id);
        sb.append(", library_card_number=").append(library_card_number);
        sb.append(", borrow_date=").append(checkout_date);
        sb.append(", due_date=").append(due_date);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.book_id);
        hash = 67 * hash + Objects.hashCode(this.library_card_number);
        hash = 67 * hash + Objects.hashCode(this.checkout_date);
        hash = 67 * hash + Objects.hashCode(this.due_date);
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
        if (!Objects.equals(this.book_id, other.book_id)) {
            return false;
        }
        if (!Objects.equals(this.library_card_number, other.library_card_number)) {
            return false;
        }
        if (!Objects.equals(this.checkout_date, other.checkout_date)) {
            return false;
        }
        return Objects.equals(this.due_date, other.due_date);
    }
}
