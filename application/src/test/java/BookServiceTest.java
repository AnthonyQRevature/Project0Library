
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.anthony.library.repository.dao.BookDao;
import org.anthony.library.repository.dao.TitleDao;
import org.anthony.library.repository.dao.TitleDataDao;
import org.anthony.library.repository.entity.BookEntity;
import org.anthony.library.repository.entity.TitleDataEntity;
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.service_layer.model.Book;
import org.anthony.library.service_layer.model.Title;
import org.anthony.library.service_layer.model.TitleData;
import org.anthony.library.service_layer.service.BookService;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
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
        //clean
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

    //ObtainBookInstance
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

    //ObtainBookById
    @Test
    void ObtainBookById_Success() throws SQLException
    {
        //arrange
        Integer testId = 5;
        BookEntity testBook = new BookEntity(testId, 123);
        TitleEntity testTitle = new TitleEntity(testBook.getIsbn(), "TEST");
        when(bookDao.findById(5)).thenReturn(Optional.of(testBook));
        when(titleDao.findById(testBook.getIsbn())).thenReturn(Optional.of(testTitle));

        Book expected = new Book(testBook, new Title(testTitle));

        //act
        var book = bookService.ObtainBookById(testId);

        //assert
        assertTrue(!book.isEmpty());
        assertEquals(expected, book.get());
    }

    //ObtainBookById
    @Test
    void ObtainBookById_Fail() throws SQLException
    {
        //arrange
        Integer testId = 5;
        BookEntity testBook = new BookEntity(testId, 123);
        when(bookDao.findById(5)).thenReturn(Optional.of(testBook));
        when(titleDao.findById(testBook.getIsbn())).thenReturn(Optional.empty());
        //act
        var book = bookService.ObtainBookById(testId);

        //assert
        assertTrue(book.isEmpty());
    }

    //ObtainUnborrowedBookByIsbn
    @Test
    void ObtainUnborrowedBookByIsbn_Success() throws SQLException
    {
        //Arrange
        Integer testInput = 123;
        BookEntity book = new BookEntity(4, testInput);
        TitleEntity title = new TitleEntity(testInput, "TEST");
        when(bookDao.ObtainUnborrowedBookByIsbn(testInput)).thenReturn(Optional.of(book));
        when(titleDao.findById(testInput)).thenReturn(Optional.of(title));

        Book expected = new Book(book, new Title(title));

        //Act
        var res = bookService.ObtainUnborrowedBookByIsbn(testInput);

        //Assert
        assertTrue(!res.isEmpty());
        assertEquals(expected, res.get());
    }

    //ObtainUnborrowedBookByIsbn
    @Test
    void ObtainUnborrowedBookByIsbn_Fail() throws SQLException
    {
        //Arrange
        Integer testInput = 123;
        BookEntity book = new BookEntity(4, testInput);
        TitleEntity title = new TitleEntity(testInput, "TEST");
        when(bookDao.ObtainUnborrowedBookByIsbn(testInput)).thenReturn(Optional.empty());
        when(titleDao.findById(testInput)).thenReturn(Optional.of(title));

        //Act
        var res = bookService.ObtainUnborrowedBookByIsbn(testInput);

        //Assert
        assertTrue(res.isEmpty());
    }

    //RetrieveAllTitles
    @Test
    void RetrieveAllTitles_Success() throws SQLException
    {
        ArrayList<TitleDataEntity> testData = new ArrayList<>();
        testData.add(new TitleDataEntity(123, "Book1", 3, "abc"));
        testData.add(new TitleDataEntity(134, "Book2", 2, "def"));
        ArrayList<TitleData> expected = new ArrayList<>();
        expected.add(new TitleData(3, new Title(123, "Book1"), "abc"));
        expected.add(new TitleData(2, new Title(134, "Book2"), "def"));

        when(titleDataDao.RetrieveAllTitleData()).thenReturn(testData);

        //act
        var res = bookService.RetrieveAllTitles();

        //assert
        assertEquals(expected, res);
    }
}