package org.anthony.library;

import org.anthony.library.service_layer.model.Member;

public class Account {
    @SuppressWarnings("unused")
    public static class AccountSecurity
    {
        static final int GUEST = 0;
        static final int MEMBER = 1;
        static final int LIBRARIAN = 2;
    }

    int level = AccountSecurity.GUEST;
    Member member;

    int getLibraryCard() 
    {
        if (member != null)
            return member.getLibraryCard();
        else
            return -1;
    }
    String getAccountname() 
    {
        switch(level)
        {
            case AccountSecurity.MEMBER:
                return member.getMemberName();
            case AccountSecurity.GUEST:
            default:
                return "Guest";
        }
    }

    public Account() { level = AccountSecurity.GUEST; }

    public Account(Member model)
    {
        this.member = model;
        this.level = AccountSecurity.MEMBER;
    }
}
