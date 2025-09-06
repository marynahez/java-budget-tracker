package ca.ucalgary.cpsc233group.cpsc233projectgui.util;

import ca.ucalgary.cpsc233group.cpsc233projectgui.Expenses;
import ca.ucalgary.cpsc233group.cpsc233projectgui.Menu;
import ca.ucalgary.cpsc233group.cpsc233projectgui.MonthData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Assists in reading files and producing a workable menu
 *E.E, Tut 11
 */
public class Reader {
    public static Menu load_saved_menu(File file){
        Menu menu = new Menu();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file));){
            String month;
            while ((month = bufferedReader.readLine()) != null){
                String[] data = month.split(",");
                MonthData monthinplay = new MonthData(data[0],Double.parseDouble(data[1]),Double.parseDouble(data[2]));
                if(data.length > 3){
                    int i = 3;
                    while(!data[i].equals("Done")){
                        Expenses expense = new Expenses(data[i],Double.parseDouble(data[i+1]),data[i+2]);
                        monthinplay.addExpenses(expense);
                        i += 3;
                    }
                }
                menu.addMonth(monthinplay);
            }
        }
        catch (IOException e){System.err.println("Something went wrong reading the file.");}
        return menu;
    }
}