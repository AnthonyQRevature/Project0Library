package org.anthony.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import org.anthony.library.MenuTree.Entry;
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
import org.anthony.tablePrinter.TablePrinter;

public class Menu 
{
    AccountController accountController = new AccountController(new MemberService(new MemberDao()));
    BookController bookController = new BookController(new BookService(new BookDao(), new TitleDao(), new TitleDataDao()));
    BorrowService borrowService = new BorrowService();

    public class MenuEntry
    {
        MenuTree.Entry entry;

        //virtual
        //returns all accessable menu options
        public Content RetrieveMenu()
        {
            int level = 0;
            return RetrieveMenu(getHeader(), entry.options, level);
        }
        protected Content RetrieveMenu(String header, MenuTree.Entry[] allOptions, int level)
        {
            Content ret = new Content();
            ret.header = header;
            ret.options = new ArrayList<>(Arrays.asList(allOptions));

            if (entry.content_request != null)
            {
                ContentRequest(entry.content_request, ret);
            }

            ret.options.removeIf((Entry e)->e.level != 0 || e.level != level);
            return ret;
        }

        //should not be virtual
        public void PrintMenu(Content body)
        {
            List<Entry> validOptions = body.options;

            System.out.println(body.header);
            System.out.print(body.content);

            for (int i = 0; i < validOptions.size(); i++) {
                var option = validOptions.get(i);
                System.out.printf("%d: %s\n", i+1, option.name);
            }

            String tail_msg;
            if (entry.parent == null && menuStack.size() == 1) tail_msg = "exit";
            else tail_msg = "go back";
            System.out.printf("%d: %s\n", validOptions.size() + 1, tail_msg);
        }
        public String getHeader() {return entry.header;}

        public void HandleRequest(String request, Scanner input)
        {
            String[] args = request.trim().split(" ");
            //hardcoded requests to service layer
            switch (args[0]) {
                case "login":
                {
                    try
                    {
                        int num = Integer.parseInt(args[1]);
                        if (num == 0)
                        {
                            //login guest
                            Account acct = accountController.LoginGuest();
                            menuStack.push(new AccMenuEntry(fullMenu.menu_login, acct));
                            //return;
                        }
                        else
                        {
                            Account acct = PromptLogin(num, input);
                            if (acct != null)
                            {
                                //login successful
                                menuStack.push(new AccMenuEntry(fullMenu.menu_login, acct));
                                //return;
                            }
                            else
                            {
                                //login unsuccessful
                                //return;
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        LibraryLogger.LogException(e);
                    }
                    break;
                }
                case "book_details":
                {
                    try
                    {
                        int isbn = Integer.parseInt(args[1]);
                        AccMenuEntry acctEntry = (AccMenuEntry)this;
                        
                        //obtain book
                        //expect not to fail
                        Book book = bookController.RetrieveUnborrowedBook(isbn).get();
                        PromptBook(book, acctEntry.acct, input);
                    }
                    catch (Exception e)
                    {
                        LibraryLogger.LogException(e);
                    }
                    break;
                }
                case "borrow_details":
                {
                    try
                    {
                        //int card_id = Integer.parseInt(args[1]);
                        //already have account cuz it was pushed onto the stack (the right way)
                        int book_id = Integer.parseInt(args[1]);

                        Book book = bookController.RetrieveBook(book_id).get();
                        AccMenuEntry e = (AccMenuEntry)(this);
                        Account acct = e.acct;
                        PromptReturnBook(book, acct, input);
                    }
                    catch (Exception e)
                    {
                        LibraryLogger.LogException(e);
                    }
                }
                default:
                    //return;
            }
        }

        MenuEntry(MenuTree.Entry e) {this.entry = e;}
    }
    public class AccMenuEntry extends MenuEntry
    {
        Account acct;

        public AccMenuEntry(Entry e, Account acct) {
            super(e);
            this.acct = acct;
        }

        @Override
        public Content RetrieveMenu()
        {
            var ret = RetrieveMenu(getHeader(), entry.options, acct.level);
            
            //should use replaceAll?
            ret.header = ret.header.replace("{NAME}", acct.getAccountname());
            for (var option : ret.options) 
            {
                //should use replaceAll?
                if (option.content_request != null)
                    option.content_request = option.content_request.replace("{CARD_ID}", Integer.toString(acct.getLibraryCard()));
            }
            return ret;
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
                    //continue;
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
                //continue;
            }
        }
        return null;
    }
    protected static class Content
    {
        String header;
        String content = "";
        List<MenuTree.Entry> options = new ArrayList<>();
    }
    private void ContentRequest(String request, Content ret)
    {
        //more hardcoded requests to the service layer
        var options = ret.options;
        String[] args = request.split(" ");
        switch (args[0])
        {
            case "display_books":
                var titles = bookController.RetrieveAllTitles();

                ret.content = TablePrinter.MakeTable(titles, TitleData.class);
                ret.options = new ArrayList<>();

                for (var title : titles)
                {
                    Entry e = new Entry();
                    e.name = title.getTitle();
                    e.level = 1;
                    e.service_request = String.format("book_details %d", title.getIsbn());
                    ret.options.add(e);
                }
                return;
            case "display_borrows":
                try
                {
                    int card_id = Integer.parseInt(args[1]);
                    var borrows = borrowService.GetBorrowsForMember(card_id);

                    ret.options = new ArrayList<>();

                    for (var borrow : borrows)
                    {
                        Entry e = new Entry();
                        e.name = borrow.get_title();
                        e.level = 1;
                        e.service_request = String.format("borrow_details %d", borrow.get_book_id());
                        ret.options.add(e);
                    }
                    return;
                }
                catch (Exception e)
                {
                    LibraryLogger.LogException(e);
                    //undo changes
                    ret.options = options;
                    return;
                }
            default:
                //unneccessary
                ret.options = options;
                //return;
        }
    }

    public void PromptReturnBook(Book book, Account borrower, Scanner input)
    {
        System.out.printf("Do you want to return %s \n", book.getTitle());
        System.out.println("1: yes");
        System.out.println("2: no");

        try
        {
            int selection = Integer.parseInt(input.nextLine());
            if (selection == 1)
            {
                boolean b = bookController.ReturnBook(borrower.getLibraryCard(), book.getBookId());
                if (b)
                {
                    System.out.println("Operation successful");
                }
                else
                {
                    System.out.println("System Error");
                }
            }
        }
        catch(NumberFormatException e)
        {
            //ignore improper input and select no
        }
    }
    public void PromptBook(Book book, Account borrower, Scanner input)
    {
        //check if they already have that book borrowed
        if (bookController.borrowService.IsBorrowed(book.getIsbn(), borrower.getLibraryCard()))
        {
            //already have the book borroed 
            System.out.printf("You have already borrowed %s\n", book.getTitle());
            System.out.println("1: return");
            
            input.nextLine();
            return;
        }

        System.out.printf("Do you want to borrow %s\n", book.getTitle());
        System.out.println("1: yes");
        System.out.println("2: no");

        try 
        {
            int selection = Integer.parseInt(input.nextLine());
            if (selection == 1)
            {
                boolean b = bookController.BorrowBook(borrower.getLibraryCard(), book.getBookId());
                if (b)
                {
                    System.out.println("Operation successful");
                }
                else
                {
                    System.out.println("System Error");
                }
            }
        }
        catch(NumberFormatException e)
        {
            //ignore improper input and select no
        }
        return;
    }
    public void PromptMenu(Scanner input)
    {
        var menuEntry = menuStack.peek();
        var body = menuEntry.RetrieveMenu();
        var validOptions = body.options;

        menuEntry.PrintMenu(body);

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
                    menuEntry.HandleRequest(newEntry.service_request, input);
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
