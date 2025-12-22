import java.util.Optional;

import org.anthony.library.repository.dao.MemberDao;
import org.anthony.library.repository.entity.MemberEntity;
import org.anthony.library.service_layer.model.Member;
import org.anthony.library.service_layer.service.MemberService;
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
public class MemberServiceTest {

    @Mock
    MemberDao dao;

    @InjectMocks
    MemberService ms;

    @AfterEach
    void Reset()
    {
        //clean
        Mockito.reset(dao);
    }

    @Test
    void ValidateAccount_Success() throws Exception
    {
        Integer cardNumber = 12345;
        String password = "password";
        MemberEntity entity = new MemberEntity(cardNumber, password, "John Doe");
        Member expected = new Member(entity);

        when(dao.findById(cardNumber)).thenReturn(Optional.of(entity));

        //act
        var res = ms.ValidateAccount(cardNumber, password);

        assertEquals(expected, res.get());
    }

    @Test
    void ValidateAccount_Incorrect() throws Exception
    {
        Integer cardNumber = 12345;
        String password = "Password";
        String wrong_password = "assword";
        MemberEntity entity = new MemberEntity(cardNumber, password, "John Doe");

        when(dao.findById(cardNumber)).thenReturn(Optional.of(entity));

        //act
        var res = ms.ValidateAccount(cardNumber, wrong_password);

        assertEquals(Optional.empty(), res);
    }

    @Test
    void ValidateAccount_Empty() throws Exception
    {
        Integer cardNumber = 12345;
        String password = "Password";

        when(dao.findById(cardNumber)).thenReturn(Optional.empty());

        //act

        var res = ms.ValidateAccount(cardNumber, password);

        assertEquals(Optional.empty(), res);
    }
}
