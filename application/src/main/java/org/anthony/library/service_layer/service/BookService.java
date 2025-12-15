package org.anthony.library.service_layer.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.dao.BookDao;
import org.anthony.library.repository.dao.DaoInterface;
import org.anthony.library.repository.dao.TitleDataDao;
import org.anthony.library.repository.entity.BookEntity;
import org.anthony.library.repository.entity.TitleDataEntity;
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.service_layer.model.Book;
import org.anthony.library.service_layer.model.Title;
import org.anthony.library.service_layer.model.TitleData;
import org.anthony.library.util.LibraryLogger;

public class BookService {
    BookDao bookDao;
    DaoInterface<TitleEntity, Integer> titleDao;
    TitleDataDao titleDataDao;

    Book convert(BookEntity e, TitleEntity t)
    {
        return new Book(e, convert_title(t));
    }
    Optional<Book> convert(Optional<BookEntity> e, TitleEntity t)
    {
        if (e.isEmpty()) return Optional.empty();
        else return Optional.of(convert(e.get(), t));
    }
    /*
    List<Book> convert(List<BookEntity> e)
    {
        ArrayList<Book> ret = new ArrayList<>();
        for (var o : e) {
            ret.add(convert(o));
        }
        return ret;
    }
    */
    Title convert_title(TitleEntity e)
    {
        return new Title(e);
    }
    List<Title> convert_title(List<TitleEntity> e)
    {
        ArrayList<Title> ret = new ArrayList<>();
        for (var o : e) {
            ret.add(convert_title(o));
        }
        return ret;
    }
    TitleData ConvertTitleData(TitleDataEntity e)
    {
        return new TitleData(e.getNum_copies(), new Title(e.getIsbn(), e.getTitle()), e.getGenres());
    }
    List<TitleData> ConvertTitleData(List<TitleDataEntity> e)
    {
        ArrayList<TitleData> ret = new ArrayList<>();
        for (var o : e) {
            ret.add(ConvertTitleData(o));
        }
        return ret;
    }

    //unused
    /*
    public List<Book> RetrieveAllBookInstances()
    {
        try
        {
            var entities = bookDao.findAll();
            return convert(entities);
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
            return new ArrayList<>();
        }
    }
    */

    //obtain the first Book with the isbn
    public Optional<Book> ObtainBookInstance(Title title)
    {
        try
        {
            return convert(bookDao.ObtainBookByIsbn(title.getIsbn()), title);
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
            return Optional.empty();
        }
    }

    public List<Title> RetrieveAllBooks()
    {
        try
        {
            return convert_title(titleDao.findAll());
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
            return new ArrayList<>();
        }
    }

    //connect Title table with Genre and aggregate BookInstance
    //most reliable way to achieve this is with a view since doing this in java
    //would require querrying from 3 different tables
    public List<TitleData> RetrieveAllTitles()
    {
        try 
        {
            var titleDataEntity = titleDataDao.RetrieveAllTitleData();
            return ConvertTitleData(titleDataEntity);
        } catch (SQLException e) 
        {
            LibraryLogger.LogException(e);
            return new ArrayList<>();
        }
    }

    //Dependency Injection
    public BookService(
        BookDao bookDao,
        DaoInterface<TitleEntity, Integer> titleDao,
        TitleDataDao titleDataDao
    ) {
        this.bookDao = bookDao;
        this.titleDao = titleDao;
        this.titleDataDao = titleDataDao;
    }
}
