import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.dao.BorrowDao;
import org.anthony.library.repository.entity.BookEntity;
import org.anthony.library.repository.entity.BorrowEntity;
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.service_layer.model.Book;
import org.anthony.library.service_layer.model.Borrow;
import org.anthony.library.service_layer.model.Title;
import org.anthony.library.service_layer.service.BookService;
import org.anthony.library.service_layer.service.BorrowService;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
public class BorrowServiceTest {

    @Mock
    BorrowDao dao;
    @Mock
    BookService bs;

    @InjectMocks
    BorrowService borrowService;

    @AfterEach
    void Reset()
    {
        //clean
        Mockito.reset(bs, dao);
    }

    //simple to test but ill try anyways
    @Test
    void AddBorrow_Success() throws SQLException
    {
        int libraryCard = 5;
        int book_id = 4;
        Date checkoutDate = new Date(12345);
        Date dueDate = new Date(67890);

        BorrowEntity expected = new BorrowEntity(book_id, libraryCard, checkoutDate, dueDate);
        
        when(dao.Create(expected)).thenReturn(book_id);
        
        //act
        var res = borrowService.AddBorrow(libraryCard, book_id, checkoutDate, dueDate);

        assertEquals(book_id, res);
    }

    @Test
    void GetBorrowsForMember_Success() throws SQLException
    {
        Integer card_id = 5;
        List<BorrowEntity> lst = new ArrayList<>();
        lst.add(new BorrowEntity(3, card_id, null, null));
        lst.add(new BorrowEntity(4, card_id, null, null));
        Book book1 = new Book(new BookEntity(3, 123), new Title(new TitleEntity(123, "TEST 1")));
        Book book2 = new Book(new BookEntity(4, 456), new Title(new TitleEntity(456, "TEST 2")));


        when(dao.findByLibraryCard(card_id)).thenReturn(lst);
        when(bs.ObtainBookById(3)).thenReturn(Optional.of(book1));
        when(bs.ObtainBookById(4)).thenReturn(Optional.of(book2));

        var expected = new ArrayList<Borrow>();
        expected.add(new Borrow(book1, null, null, 5));
        expected.add(new Borrow(book2, null, null, 5));

        //act
        var ret = borrowService.GetBorrowsForMember(card_id);

        //assert
        assertEquals(expected, ret);
    }
}
