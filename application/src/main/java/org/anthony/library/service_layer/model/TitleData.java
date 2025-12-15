package org.anthony.library.service_layer.model;

import org.anthony.tablePrinter.TablePrinter.Column;

public class TitleData {
    Title title;
    Integer numCopies;
    String genres;
//    List<String> genres; //the query would not have GenreIds

    public TitleData(Integer numCopies, Title title, String genres) {
//        this.genres = genre;
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
}
