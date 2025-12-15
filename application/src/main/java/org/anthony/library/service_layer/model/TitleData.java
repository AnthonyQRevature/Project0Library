package org.anthony.library.service_layer.model;

import java.util.List;

import org.anthony.library.service_layer.model.Title;
import org.anthony.tablePrinter.TablePrinter.Column;
import org.anthony.library.service_layer.model.Genre;

public class TitleData {
    Title title;
    Integer numCopies;
//    List<String> genres; //the query would not have GenreIds

    public TitleData(Integer numCopies, Title title) {
//        this.genres = genre;
        this.numCopies = numCopies;
        this.title = title;
    }

    @Column(name="title", width=20, weight=1)
    public String getTitle() {return title.getTitle();}
    @Column(name="isbn", width=20, weight=2)
    public Integer getIsbn() {return title.getIsbn();}
    @Column(name="num_copies", width=5, weight=3)
    public Integer getNumCopies() {return numCopies;}
}
