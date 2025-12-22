/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.anthony.library;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.anthony.library.repository.ConnectionManager;
import org.anthony.library.util.LibraryLogger;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author antho
 */
public class Application {
    public static final InputStream MENUFILE = Application.class.getResourceAsStream("/options.json");
    
    public static void main(String[] args) {

        //check connection
        try
        {
            ConnectionManager.getConnection();
        }
        catch (ExceptionInInitializerError e)
        {
            System.out.println("Could not establish a connection to server. Stopping.");
            LibraryLogger.LogException(e);
            System.exit(1);
        }

        Scanner input = new Scanner(System.in);
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            var menu = mapper.readValue(MENUFILE, MenuTree.class);
            //MenuState state = new MenuState(menu);
            /*
            while (true) {
                PromptMenu(state, input);
            }
            */
            Menu mainMenu = new Menu(menu);
            while (true) {
                mainMenu.PromptMenu(input);
            }
        }
        catch (JsonMappingException e)
        {
            LibraryLogger.getLogger().error("Problem with options.json");
            LibraryLogger.LogException(e);
            System.out.println("System Error");
        }
        catch (IOException e) 
        {
            LibraryLogger.LogException(e);
        }
    }
}
