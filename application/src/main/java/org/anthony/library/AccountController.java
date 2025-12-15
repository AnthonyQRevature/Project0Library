package org.anthony.library;

import org.anthony.library.service_layer.service.MemberService;

public class AccountController {

    MemberService memberService;

    public AccountController(MemberService memberService) {
        this.memberService = memberService;
    }

    public Account LoginGuest()
    {
        return new Account();
    }

    public Account AttemptLogin(int level, int cardNumber, String password)
    {
        if (level != Account.AccountSecurity.LIBRARIAN)
        {
            //verify credentials
            var member = memberService.ValidateAccount(cardNumber, password);
            if (!member.isEmpty())
            {
                return new Account(member.get());
            }
            else
            {
                return null;
            }
        }
        else
        {
            //hardcoded librarian credentials    
            if (cardNumber == 1 && password.equals("password"))
            {
                return new Account();
            }
            else
            {
                return null;
            }
        }
    }
}
