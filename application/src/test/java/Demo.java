
import org.anthony.library.repository.dao.BookDao;
import org.anthony.library.repository.dao.CategoryDao;
import org.anthony.library.repository.dao.MemberDao;
import org.anthony.library.repository.dao.TitleDao;
import org.anthony.library.repository.dao.TitleDataDao;
import org.anthony.library.repository.entity.Categorization;
import org.anthony.library.repository.entity.MemberEntity;
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.service_layer.model.TitleData;
import org.anthony.library.service_layer.service.BookService;
import org.anthony.tablePrinter.TablePrinter;


public class Demo {

    private static void PrintTable() throws Exception
    {
        MemberDao dao = new MemberDao();
        var list = dao.findAll();

        TablePrinter.PrintTable(list, MemberEntity.class);
    }   

    public static void main(String[] args) throws Exception
    {
        System.out.println();
        BookService bs = new BookService(new BookDao(), new TitleDao(), new TitleDataDao());
        TablePrinter.PrintTable(bs.RetrieveAllTitles(), TitleData.class);
    }
}
