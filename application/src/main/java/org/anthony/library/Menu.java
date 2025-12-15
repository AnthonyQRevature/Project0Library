package org.anthony.library;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import org.anthony.library.MenuTree.Entry;
import org.anthony.library.repository.dao.MemberDao;
import org.anthony.library.service_layer.service.MemberService;

public class Menu 
{
    AccountController accountController = new AccountController(new MemberService(new MemberDao()));

    public static class MenuEntry
    {
        MenuTree.Entry entry;

        //virtual
        //returns all accessable menu options
        public List<MenuTree.Entry> RetrieveMenu()
        {
            int level = 0;
            return RetrieveMenu(entry.options, level);
        }
        protected List<MenuTree.Entry> RetrieveMenu(MenuTree.Entry[] allOptions, int level)
        {
            ArrayList<MenuTree.Entry> validEntries = new ArrayList<>();

            for (var e : allOptions) 
            {
                if (e.level == 0 || e.level == level)
                {
                    validEntries.add(e);
                }
            }

            return validEntries;
        }

        //virtual
        public void PrintMenu(boolean top)
        {
            List<Entry> validOptions = RetrieveMenu();

            String header = entry.getHeader();

            //if (state.account != null) {header = header.replace("{NAME}", state.account.accountName);}
            top = entry.parent == null && top;
            PrintMenu(header, validOptions, top);
        }
        protected void PrintMenu(String header, List<Entry> options, boolean top)
        {
            System.out.println(header);

            if (entry.content_request != null)
            {

            }

            for (int i = 0; i < options.size(); i++) {
                var option = options.get(i);
                System.out.printf("%d: %s\n", i+1, option.name);
            }
            if (top) System.out.printf("%d: exit\n", options.size() + 1);
            else System.out.printf("%d: go back\n", options.size() + 1);
        }

        public String getHeader() {return entry.header;}

        MenuEntry(MenuTree.Entry e) {this.entry = e;}
    }
    public static class AccMenuEntry extends MenuEntry
    {
        Account acct;

        public AccMenuEntry(Entry e, Account acct) {
            super(e);
            this.acct = acct;
        }

        @Override
        public List<MenuTree.Entry> RetrieveMenu()
        {
            return RetrieveMenu(entry.options, acct.level);
        }

        @Override
        public void PrintMenu(boolean top)
        {
            List<Entry> validOptions = RetrieveMenu();
            String header = entry.getHeader();

            header = header.replace("{NAME}", acct.accountName);
            top = entry.parent == null && top;
            PrintMenu(header, validOptions, top);
        }
    }

    MenuTree fullMenu;
    Stack<MenuEntry> menuStack;

    public Account PromptLogin(int level, Scanner input)
    {
        for (int attempts = 0; attempts < 3; attempts++) 
        {
            int number = 0;
            while (number == 0)
            {
                System.out.println("Library Card # (leave blank to go back): ");
                String _number = input.nextLine();
    
                if (_number.length() == 0) return null;
                try{
                    number = Integer.parseInt(_number);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("invalid number");
                    continue;
                }
            }
    
            System.out.println("password: ");
            String pw = input.nextLine();
    
            Account acct = accountController.AttemptLogin(level, number, pw);
            if (acct != null)
            {
                return acct;
            }
            else
            {
                //incorrect credentials try again
                continue;
            }
        }
        return null;
    }
    public static void ContentRequest(String request)
    {
        //more hardcoded requests to the service layer
    }
    public void HandleRequest(String request, Scanner input)
    {
        //hardcoded requests to service layer
        switch (request) {
            case "login0":
            {
                //login successful
                Account acct = accountController.LoginGuest();
                //currentState.account = acct;
                //currentState.currentMenu = currentState.fullMenu.menu_login;
                //return currentState;
                menuStack.push(new AccMenuEntry(fullMenu.menu_login, acct));
                return;
            }
            case "login1":
            {
                Account acct = PromptLogin(1, input);
                if (acct != null)
                {
                    //login successful
                    menuStack.push(new AccMenuEntry(fullMenu.menu_login, acct));
                    return;
                }
                else
                {
                    //login unsuccessful
                    return;
                }
            }
            case "login2":
            {
                Account acct = PromptLogin(2, input);
                if (acct != null)
                {
                    //login successful
                    menuStack.push(new AccMenuEntry(fullMenu.menu_login, acct));
                    return;
                }
                else
                {
                    //login unsuccessful
                    return;
                }
            }
            default:
                return;
        }
    }

    public void PromptMenu(Scanner input)
    {
        var menuEntry = menuStack.peek();
        var validOptions = menuEntry.RetrieveMenu();

        menuEntry.PrintMenu(menuStack.size() == 1);

        //loop until valid input
        while (true) { 

            var entry = menuEntry.entry;
            int selection = 0;
            try
            {
                String ln = input.nextLine();
                selection = Integer.parseInt(ln);
            }
            catch (NumberFormatException e)
            {
                continue;
            }
            catch (NoSuchElementException e)
            {
                //exit the program at end of input character ^z
                System.exit(0);
            }

            if (0 < selection && selection <= validOptions.size())
            {
                var newEntry = validOptions.get(selection - 1);
                
                //if selection has a service request, by default you dont change the menu state
                if (newEntry.service_request != null)
                {
                    //state = HandleRequest(newEntry.header, input, state);
                    HandleRequest(newEntry.service_request, input);
                    break;
                }
                else
                {
                    //change the menu state
                    menuEntry.entry = newEntry;
                    break;
                }
            }
            else if (selection == validOptions.size() + 1)
            {
                //determine if we are traversing the tree or the stack
                if (entry.parent == null)
                {
                    if (menuStack.size() > 1)
                    {
                        //traverse the stack
                        menuStack.pop();
                    }
                    else
                    {
                        //exit if this is the last stack entry
                        System.exit(0);
                    }
                }
                else
                {
                    //traverse the tree
                    menuEntry.entry = entry.parent;
                }
                break;
            }
            else
            {
                //invalid selection
            }
        }
    }

    public Menu(MenuTree fullMenu) 
    {
        this.fullMenu = fullMenu;
        menuStack = new Stack<>();
        menuStack.push(new MenuEntry(fullMenu.menu));
    }
}
