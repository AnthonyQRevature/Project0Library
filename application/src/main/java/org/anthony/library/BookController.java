package org.anthony.library;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.dao.BookDao;
import org.anthony.library.repository.dao.MemberDao;
import org.anthony.library.repository.dao.TitleDao;
import org.anthony.library.repository.dao.TitleDataDao;
import org.anthony.library.service_layer.model.Book;
import org.anthony.library.service_layer.model.TitleData;
import org.anthony.library.service_layer.service.BookService;
import org.anthony.library.service_layer.service.BorrowService;
import org.anthony.library.service_layer.service.MemberService;
import org.anthony.library.util.LibraryLogger;

public class BookController {
    MemberService memberService = new MemberService(new MemberDao());
    BorrowService borrowService = new BorrowService();
    BookService service = new BookService(new BookDao(), new TitleDao(), new TitleDataDao());

    List<TitleData> RetrieveAllTitles()
    {
        return service.RetrieveAllTitles();
    }

    public BookController(BookService service) {
        this.service = service;
    }

    public Optional<Book> RetrieveUnborrowedBook(int isbn) {
        return service.ObtainUnborrowedBookByIsbn(isbn);
    }

    public Optional<Book> RetrieveBook(int book_id)
    {
        return service.ObtainBookById(book_id);
    }

    public boolean BorrowBook(Integer libraryCard, Integer book_id) {
        var acct = memberService.GetMemberById(libraryCard);
        var book = service.ObtainBookById(book_id);

        if (acct.isEmpty() || book.isEmpty())
        {
            return false;
        }
        else
        {
            Date currentDate = Date.valueOf(LocalDate.now());
            //just do 3 weeks
            Date returnDate = Date.valueOf(LocalDate.now().plus(Period.of(0, 0, 21)));

            Integer id = borrowService.AddBorrow(libraryCard, book_id, currentDate, returnDate);
            if (id > 0)
            {
                LibraryLogger.getLogger().info(String.format("Inserted Borrow %d", id));
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    boolean ReturnBook(int libraryCard, Integer book_id) {
        var acct = memberService.GetMemberById(libraryCard);
        var book = service.ObtainBookById(book_id);

        if (acct.isEmpty() || book.isEmpty())
        {
            return false;
        }
        else
        {
            boolean b = borrowService.RemoveBorrow(book_id);
            if (b)
            {
                //success
                LibraryLogger.getLogger().info(String.format("Deleted borrow %d", book_id));
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
