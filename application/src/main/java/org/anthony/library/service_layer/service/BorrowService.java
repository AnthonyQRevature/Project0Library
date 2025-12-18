package org.anthony.library.service_layer.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.anthony.library.repository.dao.BookDao;
import org.anthony.library.repository.dao.BorrowDao;
import org.anthony.library.repository.dao.TitleDao;
import org.anthony.library.repository.dao.TitleDataDao;
import org.anthony.library.repository.entity.BorrowEntity;
import org.anthony.library.service_layer.model.Book;
import org.anthony.library.service_layer.model.Borrow;
import org.anthony.library.util.LibraryLogger;

public class BorrowService {
    BorrowDao borrowDao = new BorrowDao();
    BookService bs = new BookService(new BookDao(), new TitleDao(), new TitleDataDao());

    Borrow Convert(int card_id, BorrowEntity e, Book b)
    {
        return new Borrow(b, e.getCheckout_date(), e.getDue_date(), card_id);
    }

    //UNTESTED
    public List<Borrow> GetBorrowsForMember(int card_id)
    {
        try
        {
            ArrayList<Borrow> ret = new ArrayList<>();
            var borrows = borrowDao.findByLibraryCard(card_id);

            for (var borrow : borrows) 
            {
                var book = bs.ObtainBookById(borrow.getBook_id());
                ret.add(Convert(card_id, borrow, book.get()));
            }

            return ret;
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
            return new ArrayList<>();
        }
    }

    //UNTESTED
    public int AddBorrow(Integer libraryCard, Integer book_id, Date checkoutDate, Date dueDate) {
        try
        {
            return borrowDao.Create(new BorrowEntity(book_id, libraryCard, checkoutDate, dueDate));
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
            return -1;
        }
    }
}
