package org.anthony.library.service_layer.service;

import java.sql.SQLException;
import java.util.Optional;

import org.anthony.library.repository.dao.DaoInterface;
import org.anthony.library.repository.entity.MemberEntity;
import org.anthony.library.service_layer.model.Member;

public class MemberService {
    private static Member convert(MemberEntity e)
    {
        return new Member(e);
    }

    DaoInterface<MemberEntity, Integer> dao;

    //UNTESTED
    public Optional<Member> ValidateAccount(Integer cardNumber, String password)
    {
        try
        {
            var account = dao.findById(cardNumber);
            if (account.isEmpty())
            {
                return Optional.empty();
            }
            if (account.get().getMemberPassword().equals(password))
            {
                return Optional.of(convert(account.get()));
            }
            else
            {
                return Optional.empty();
            }
        }
        catch (SQLException e)
        {
            return Optional.empty();
        }
    }

    //dependency injection
    public MemberService(DaoInterface<MemberEntity, Integer> dao) {
        this.dao = dao;
    }
}
