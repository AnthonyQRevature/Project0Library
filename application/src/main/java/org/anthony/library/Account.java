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

    String accountName;
    int level = AccountSecurity.GUEST;

    public Account() { accountName = "Guest"; }

    public Account(String name, int level)
    {
        this.accountName = name;
        this.level = level;
    }

    public Account(Member model)
    {
        this.accountName = model.getMemberName();
        this.level = AccountSecurity.MEMBER;
    }
}
