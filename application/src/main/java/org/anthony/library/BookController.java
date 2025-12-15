package org.anthony.library;

import java.util.List;

import org.anthony.library.service_layer.model.TitleData;
import org.anthony.library.service_layer.service.BookService;

public class BookController {
    BookService service;

    List<TitleData> RetrieveAllTitles()
    {
        return service.RetrieveAllTitles();
    }

    public BookController(BookService service) {
        this.service = service;
    }
}
