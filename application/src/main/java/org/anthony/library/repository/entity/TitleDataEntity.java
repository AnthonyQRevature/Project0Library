package org.anthony.library.repository.entity;

import java.util.Objects;

import org.anthony.tablePrinter.TablePrinter.Column;

public class TitleDataEntity {

    Integer isbn;
    String title;
    Integer num_copies;
    String genres;

    public TitleDataEntity(Integer isbn, String title, Integer num_copies, String genres) {
        this.isbn = isbn;
        this.num_copies = num_copies;
        this.title = title;
        this.genres = genres;
    }

    @Column(name="isbn",weight=2,width=20)
    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    @Column(name="title", weight=1, width=20)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="num_copies", weight=3)
    public Integer getNum_copies() {
        return num_copies;
    }

    public void setNum_copies(Integer num_copies) {
        this.num_copies = num_copies;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.isbn);
        hash = 41 * hash + Objects.hashCode(this.title);
        hash = 41 * hash + Objects.hashCode(this.num_copies);
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
        final TitleDataEntity other = (TitleDataEntity) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        return Objects.equals(this.num_copies, other.num_copies);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TitleDataEntity{");
        sb.append("isbn=").append(isbn);
        sb.append(", title=").append(title);
        sb.append(", num_copies=").append(num_copies);
        sb.append('}');
        return sb.toString();
    }

    @Column(name="genres",width=20,weight=4)
    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
