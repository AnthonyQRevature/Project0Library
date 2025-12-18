
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.anthony.library.repository.dao.BookDao;
import org.anthony.library.repository.dao.TitleDao;
import org.anthony.library.repository.dao.TitleDataDao;
import org.anthony.library.repository.entity.BookEntity;
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.service_layer.model.Book;
import org.anthony.library.service_layer.model.Title;
import org.anthony.library.service_layer.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookDao bookDao;
    @Mock
    TitleDao titleDao;
    @Mock
    TitleDataDao titleDataDao;

    @InjectMocks
    BookService bookService;

    /*
    ArrayList<BookEntity> testList;
    ArrayList<Book> resultList;

    @BeforeEach
    void Setup()
    {
        testList = new ArrayList<BookEntity>();
        testList.add(new BookEntity(1, 123));
        testList.add(new BookEntity(2, 123));
        testList.add(new BookEntity(3, 123));
        testList.add(new BookEntity(4, 456));
        testList.add(new BookEntity(5, 456));
        testList.add(new BookEntity(6, 456));
        testList.add(new BookEntity(7, 134));

        resultList = new ArrayList<Book>();
        resultList.add(new Book(testList.get(0)));
        resultList.add(new Book(testList.get(1)));
        resultList.add(new Book(testList.get(2)));
        resultList.add(new Book(testList.get(3)));
        resultList.add(new Book(testList.get(4)));
        resultList.add(new Book(testList.get(5)));
        resultList.add(new Book(testList.get(6)));
    }

    @Test
    void FindAll_Empty() throws SQLException {
        // Arrange
        when(bookDao.findAll()).thenReturn(new ArrayList<>());

        // Act
        var books = bookService.RetrieveAllBookInstances();

        // Assert
        assertEquals(new ArrayList<Book>(), books);
    }

    @Test
    void FindAll_Success() throws SQLException {
        // Arrange
        when(bookDao.findAll()).thenReturn(testList);

        // Act
        var books = bookService.RetrieveAllBookInstances();

        // Assert
        assertEquals(resultList, books);
    }
    */

    ArrayList<TitleEntity> testTitles = new ArrayList<>();
    ArrayList<Title> resultTitles = new ArrayList<>();

    @BeforeEach
    void Setup()
    {
        testTitles.add(new TitleEntity(123, "To Kill A Mockingbird"));
        testTitles.add(new TitleEntity(456, "The Great Gatsby"));
        testTitles.add(new TitleEntity(134, "The Odyssey"));

        resultTitles.add(new Title(testTitles.get(0)));
        resultTitles.add(new Title(testTitles.get(1)));
        resultTitles.add(new Title(testTitles.get(2)));
    }

    //quantum unit test
    //issue with Mockito
    @AfterEach
    void Reset()
    {
        Mockito.reset(bookDao, titleDao, titleDataDao);
    }

    @Test
    void FindAllTitles_Success() throws SQLException
    {
        //Arrange
        when(titleDao.findAll()).thenReturn(testTitles);

        //Act
        var result = bookService.RetrieveAllBooks();

        //Assert
        assertEquals(resultTitles, result);
    }

    @Test
    void ObtainBookInstance_Success() throws SQLException
    {
        //Arrange
        when(bookDao.ObtainUnborrowedBookByIsbn(123)).thenReturn(Optional.of(new BookEntity(1, 123)));
        
        //Act
        var result = bookService.ObtainBookInstance(new Title(123, "To Kill a Mockingbird"));

        //Assert
        assertEquals(Optional.of(new Book(1, 123, "To Kill a Mockingbird")), result);
    }
}
