package org.anthony.library.repository.entity;

import java.util.Objects;

import org.anthony.tablePrinter.TablePrinter.Column;

import javafx.util.Pair;

public class Categorization {
    Integer isbn;
    Integer genre_id;

    public Categorization() {
    }

    public Categorization(Integer genre_id, Integer isbn) {
        this.genre_id = genre_id;
        this.isbn = isbn;
    }

    public Pair<Integer, Integer> getKey()
    {
        return new Pair<>(isbn, genre_id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.isbn);
        hash = 37 * hash + Objects.hashCode(this.genre_id);
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
        final Categorization other = (Categorization) obj;
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        return Objects.equals(this.genre_id, other.genre_id);
    }

    @Column(name="isbn", weight=1, width=20)
    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    @Column(name="genre id", weight=2, width=10)
    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Categorization{");
        sb.append("isbn=").append(isbn);
        sb.append(", genre_id=").append(genre_id);
        sb.append('}');
        return sb.toString();
    }
}
