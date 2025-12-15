package org.anthony.library.repository.entity;

import java.util.Objects;

import org.anthony.tablePrinter.TablePrinter.Column;

public class TitleEntity 
{
    
    private Integer isbn;
    private String title;

    public TitleEntity() {
    }

    public TitleEntity(Integer isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    @Column(name="isbn", weight=1, width=20)
    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }
    
    @Column(name="Title",weight=2,width=20)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.isbn);
        hash = 59 * hash + Objects.hashCode(this.title);
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
        final TitleEntity other = (TitleEntity) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return Objects.equals(this.isbn, other.isbn);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title{");
        sb.append("isbn=").append(isbn);
        sb.append(", title=").append(title);
        sb.append('}');
        return sb.toString();
    }

}
