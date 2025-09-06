package ca.ucalgary.cpsc233group.cpsc233projectgui;

import ca.ucalgary.cpsc233group.cpsc233projectgui.graphics.Swing;
import ca.ucalgary.cpsc233group.cpsc233projectgui.graphics.HelloApplication;
import ca.ucalgary.cpsc233group.cpsc233projectgui.util.Reader;
import javafx.application.Application;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Do you want to run in Graphical Interface? (y/n)");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                System.out.println("What GUI would you like to use? (JavaFx/Swing)");
                String answer2 = scanner.nextLine();
                if (answer2.equalsIgnoreCase("swing")) {
                    Swing swingMenu = new Swing();
                    swingMenu.JavaSwingUpdates();
                } else if (answer2.equalsIgnoreCase("JavaFx")) {
                    Application.launch(HelloApplication.class);
                } else {
                    System.out.println("The answer is not valid");
                    continue;
                }
            }

            System.out.println("Do you want to load in previous information?(Yes/No)");
            String scan = scanner.nextLine();
            if (scan.equalsIgnoreCase("yes") || scan.equalsIgnoreCase("y")) {
                File file = new File("Storage.csv");
                if (file == null) {
                    System.out.println("Could not find Storage.csv");
                    return;
                }
                else {
                    Menu loaded = Reader.load_saved_menu(file);
                    loaded.menuLoop();
                    System.out.println("Previous information has been loaded in.");}

            } else if (scan.equalsIgnoreCase("no") || scan.equalsIgnoreCase("n")) {
                Menu menu = new Menu();
                menu.menuLoop();
            } else {
                System.out.println("Please restart the application and ensure you input a correct response.");
            }
        }
    }
}