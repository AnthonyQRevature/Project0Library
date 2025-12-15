/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.anthony.library;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.anthony.library.util.LibraryLogger;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author antho
 */
public class Application {
    public static final File MENUFILE = new File("options.json");
    
    public static void main(String[] args) {
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
