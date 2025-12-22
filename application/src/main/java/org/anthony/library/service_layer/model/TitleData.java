package org.anthony.library.service_layer.model;

import java.util.Objects;

import org.anthony.tablePrinter.TablePrinter.Column;

public class TitleData {
    Title title;
    Integer numCopies;
    String genres;

    public TitleData(Integer numCopies, Title title, String genres) {
        this.numCopies = numCopies;
        this.title = title;
        this.genres = genres;
    }

    @Column(name="title", width=20, weight=1)
    public String getTitle() {return title.getTitle();}
    @Column(name="isbn", width=20, weight=2)
    public Integer getIsbn() {return title.getIsbn();}
    @Column(name="num_copies", width=5, weight=3)
    public Integer getNumCopies() {return numCopies;}
    @Column(name="genres", width=20, weight=4)
    public String getGenres() {return genres;}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + Objects.hashCode(this.numCopies);
        hash = 17 * hash + Objects.hashCode(this.genres);
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
        final TitleData other = (TitleData) obj;
        if (!Objects.equals(this.genres, other.genres)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return Objects.equals(this.numCopies, other.numCopies);
    }

    
}
