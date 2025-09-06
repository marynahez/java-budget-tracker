package ca.ucalgary.cpsc233group.cpsc233projectgui.graphics;

import ca.ucalgary.cpsc233group.cpsc233projectgui.Menu;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Category;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.MainMenu;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Months;

import javax.swing.*;
import java.awt.*;

/**
 * This file is still being worked on BUT the classes are set up in a semi-chronological order based on the order the classes are called
 */
public class Swing extends Menu {
   String optMessage = """
            Welcome to the Budget Tracker! Here you can easily track your expenses, set savings targets, and increase your financial opportunities.
            
            To start, please, choose an option you would like to use. Please, enter the number of the option:
            """;

    //Set up frame - Stya
    private JFrame frame;  // Creates a frame
    private JTextArea textArea;  //Create text area to display messages
    private JPanel buttonPanel; //Area for the buttons :>

    //Setting up a java window to pop up 16:9 res
    final private static int SCREEN_WIDTH = 960;
    final private static int SCREEN_HEIGHT = 540;

    private final MainMenu[] menuOptions = MainMenu.values(); //Gets main menu value stuff
    private final Months[] validMonths = Months.values(); //Gets all the valid months
    private final Category[] inputCategories = Category.values(); //Gets valid categories to put money into

    private String selectedCategory = "";
    private String selectedMonth = "";

    /** Things i used while writing this function to understand java swing
     * These links helped me learn how java swing worked not all of them are used in the final code
     * if this counts as plagiarism in some way then only stya worked on these functions
     *
     * I used a demo project to get better at learning how to switch between different look and feels made by oracle
     *
     * https://stackoverflow.com/questions/36164588/displaying-many-buttons-in-swing
     * https://docs.oracle.com/javase/tutorial/uiswing/components/
     * https://www.geeksforgeeks.org/java-awt-borderlayout-class/
     * https://docs.oracle.com/javase/7/docs/api/javax/swing/JScrollPane.html
     * Look and Feel https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     * https://stackoverflow.com/questions/67356715/how-will-i-make-the-buttons-to-be-dynamic-depending-on-the-number-of-buttons
     * https://stackoverflow.com/questions/5621338/how-to-add-jtable-in-jpanel-with-null-layout/5630271#5630271
     */

    /**
     * Is the initial call for graphics and sets up a frame. This frame gets added onto but simply speaking all this does is setup the UI system and the window
     */
    public void JavaSwingUpdates() {
        //Changes the theme of our graphics automatically to one that is easier on the eyes
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Setting up the main frame values
        frame = new JFrame("Budget Tracker"); //This creates the initial frame aka the very outside with the X and minimize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits when X is pressed
        frame.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //Screen width set to premade sizes.
        frame.setLayout(new BorderLayout(5,5)); //No gaps between components
        frame.setLocationRelativeTo(null); //Center of screen
        frame.setResizable(false); //Makes the main window unable to be resized so people cannot mess with locations of items


        welcomeScreen();//Runs the "Welcome Screen" this is a different setup to our normal gui system so it remains seperate

        frame.pack();//Resizes window to the minimum possible (restrained by setMinimumSize)
        frame.setVisible(true);// Make the frame visible

    }
    /**The first thing to run and creates a 'Welcome Screen' This is where the user can select settings, quit, or continue.
    */
    private void welcomeScreen() {//TODO: add some color. We dont like no bland boring grey in this group
        //Creates a JPanel object that is using a GridBagLayout -> Similar to GridLayout but it doesnt restrict to one size and doesnt auto-fit things into the width/height
        JPanel centerPanel = new JPanel(new GridBagLayout());

        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,-150,0)); //Negative padding moves it up a bit without a lot of code

        JPanel welcomeButtonPanel = new JPanel(new FlowLayout()); //Creates a button panel that uses a FlowLayout which just stacks things side by side for the width

        JLabel welcomeText = new JLabel("Budget Tracker"); //Text :/

        Font welcomeButtonFont = new Font("Serif", Font.PLAIN, 30); //Creates a font for buttons
        Font welcomeTextFont = new Font("Serif", Font.BOLD, 40); //Creates a font for the meny text
        Insets buttonMargins = new Insets(10, 60, 10, 60); //Creates the margins i use for all 3 buttons

        //Set text font and add the text into the panel that is currently centered on our frame
        welcomeText.setFont(welcomeTextFont);
        centerPanel.add(welcomeText);

        //Create 3 button objects
        JButton continueButton = new JButton("Continue");
        JButton settingsButton = new JButton("Settings");
        JButton quitButton = new JButton("Quit");

        //Set font for buttons and then sets the margins so that they are sized correctly
        continueButton.setFont(welcomeButtonFont);
        continueButton.setMargin(buttonMargins);

        quitButton.setFont(welcomeButtonFont);
        quitButton.setMargin(buttonMargins);

        settingsButton.setFont(welcomeButtonFont);
        settingsButton.setMargin(buttonMargins);

        //adds buttons after editing to button panel
        welcomeButtonPanel.add(continueButton);
        welcomeButtonPanel.add(quitButton);
        welcomeButtonPanel.add(settingsButton);

        welcomeButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,200,0));//This just moved the button panel to the center -> prob a better way exists

        //Adds everything to the fram
        frame.add(centerPanel);
        frame.add(welcomeButtonPanel, BorderLayout.SOUTH);

        continueButton.addActionListener(e -> { //Continue button event handler
            initMainProj(); //Go to initMainProj for explanation of what its use is
            mainMenu(); //Runs the main menu func
        });

        quitButton.addActionListener(e -> System.exit(0)); //Quit button event handler


        frame.revalidate(); // Refresh entire frame
        frame.repaint();
    }

    /** This function is relatively simple. Its use is to just reset the frame back to default and then initialize it in a way/setup
     * that we are able to get use out of/will use for the rest of the thing.
     * AKA reset and recreate
     */
    private void initMainProj(){ //TODO: Clean up the main menu look so that it looks more professional
        frame.getContentPane().removeAll(); //Clears

        JPanel mainPanel = new JPanel(new BorderLayout()); //https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html <- logic of this
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        mainPanel.setLayout(new BorderLayout(0,5));
        mainPanel.setBackground(new Color(204, 204, 255));//changes background to a purpleish

        textArea = new JTextArea(); //Create text area to display messages


        textArea.setEditable(false); //Sets the text area to an uneditable object
        textArea.setFocusable(false); //Removes the "|" from the text area
        textArea.setLineWrap(true); //adds text wrapping
        textArea.setWrapStyleWord(true); //wraps wrods instead of letters
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));//https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html
        textArea.setBackground(new Color(230, 230, 255));

        //adds a scrollable pane in case we need it
        JScrollPane scrollPane = new JScrollPane(textArea); //adds a scrollable pane in case we need it
        mainPanel.add(scrollPane, BorderLayout.CENTER); //Puts scroll pane into the frame

        buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5)); //Creates an area for input
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //Adds margins to the button panel
        buttonPanel.setBackground(new Color(204, 204, 255));

        //adds main panel and button to frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    /** Creates the main menu scene with text and button action events
     */
    private void mainMenu() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        buttonPanel.removeAll();//Clears button panel for new set
        textArea.setText(""); //Clears text
        textArea.revalidate();// Validating and repainting makes it so that the frames and components 'reset' and change to the new info/look
        textArea.repaint();
        frame.remove(inputPanel);

        textArea.setText(optMessage);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Serif", Font.PLAIN, 30));

        //Loops for the length of menu options and then it creats a button with a string value of that menu option
        for(int i = 0; i < menuOptions.length; i++){
            JButton button = new JButton(String.valueOf(menuOptions[i].getDescription()));
            button.setFocusPainted(false); //https://stackoverflow.com/questions/16157120/setfocuspainted-used-for-jbuttons

            int option = i; //not too sure why this was required honestly but intellij yells at me when i dont have this :(
            //Adds action listener to each individual button
            button.addActionListener(e -> { //takes the button clicked prints something and then goes to user choice with the passed info
                textArea.append("Selected option " + option + ". " + menuOptions[option].getDescription() + "\n");

                userChoice(option, textArea);
            });

            buttonPanel.add(button);// adds button to a buttonPanel using Grid layout which formats the buttons automatically
        }
        frame.revalidate();
        frame.repaint();

    }

    /**Based on the user choice go to the corresponding class
     */ //TODO: make the other options in other classes use this same system as it works much better overall and can go backwards easier (i think)
    private void userChoice(int value, JTextArea textArea){
        switch (value) {
            case 0:
                System.out.printf("Thank you for using the Budget Tracker!%nHave a wonderful day!\n");
                System.exit(0);
            case 1:
                manageExpenses();
                return;
            case 2:

                return;
            case 3:

                return;
        }
    }

    /** Runs show months selection script from main terminal code BUT for swing
     */
    private void manageExpenses() { //TODO: check with group to make sure this actually still works after demo 2 changes

        buttonPanel.removeAll();
        textArea.setText("");
        textArea.validate();// Validating and repainting makes it so that the frames and components 'reset' and change to the new info/look
        textArea.repaint();

        textArea.append("For what month would you like to manage expenses for? \n");

        for(int i = 0; i < validMonths.length; i++){
            JButton button = new JButton(String.valueOf(validMonths[i]));

            int option = i; //not too sure why this was required honestly but intellij yells at me when i dont have this :(
            button.addActionListener(e -> {
                setSelectedMonth(String.valueOf(validMonths[option]));
                selectedMonth = getSelectedMonth();
                textArea.append("Selected month: " + selectedMonth + "\n");
                showCategoryOptions();
            });

            buttonPanel.add(button);
        }
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu());
        buttonPanel.add(backButton);
        frame.revalidate();
        frame.repaint();

    }

    /**
     * Runs category options after a category is selected. This will probably be worked around so it works with code better.
     */
    private void showCategoryOptions() {
        buttonPanel.removeAll();

        textArea.append("Which category would you like to manage expenses for?\n");
        for (int i = 0; i < inputCategories.length; i++) {
            JButton button = new JButton(String.valueOf(inputCategories[i].editEnum()));
            int option = i;
            button.addActionListener(e -> {
                setSelectedCategory(String.valueOf(inputCategories[option].editEnum()));
                selectedCategory = getSelectedCategory();
                textArea.append("Selected category: " + selectedCategory + "\n");
                getSwingCategory(selectedCategory);
            });
            buttonPanel.add(button);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> manageExpenses());
        buttonPanel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private void getSwingCategory(String selectedCategory) {
        switch (selectedCategory) {
            case "FOOD" -> manageExpensesCategory(selectedCategory);
            case "ENTERTAINMENTS" -> manageExpensesCategory(selectedCategory);
            case "UTILITIES_AND_BILLS" -> manageExpensesCategory(selectedCategory);
            case "GAS_AND_TRANSPORT" -> manageExpensesCategory(selectedCategory);
            case "HEALTHCARE" -> manageExpensesCategory(selectedCategory);
        };
    }

    private void manageExpensesCategory(String selectedCategory) {
        buttonPanel.removeAll();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel itemLabel = new JLabel("What did you spend money on?");
        JTextField itemField = new JTextField(20);
        itemField.setFont(new Font("Serif", Font.PLAIN, 16));

        JLabel amountLabel = new JLabel("Amount spent (CAD):");
        JTextField amountField = new JTextField(10);
        amountField.setFont(new Font("Serif", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(itemLabel, gbc);

        gbc.gridy = 1;
        inputPanel.add(itemField, gbc);

        gbc.gridy = 2;
        inputPanel.add(amountLabel, gbc);

        gbc.gridy = 3;
        inputPanel.add(amountField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Serif", Font.BOLD, 16));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showCategoryOptions());

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navPanel.add(submitButton);
        navPanel.add(backButton);

        submitButton.addActionListener(e -> {
                    String itemName = itemField.getText().trim();
                    String amountStr = amountField.getText().trim();

                    // Validate item name contains at least one letter
                    if (!itemName.matches(".*[a-zA-Z].*")) {
                        JOptionPane.showMessageDialog(frame,
                                "Please enter a name with at least one letter",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate amount is a number
                    if (!amountStr.matches("\\d+(\\.\\d+)?")) {
                        JOptionPane.showMessageDialog(frame,
                                "Please enter a valid amount (e.g., 50 or 25.99)",
                                "Invalid Amount",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double amount = Double.parseDouble(amountStr);

                    textArea.append(String.format("Added %.2f CAD for %s to %s%n",
                            amount, itemName, selectedCategory));
            int option = JOptionPane.showConfirmDialog(frame,
                    "Do you want to add more expenses?",
                    "Add More Expenses",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // Clear fields for new entry
                itemField.setText("");
                amountField.setText("");
                itemField.requestFocus();
            } else {
                // Ask if user wants to return to menu or exit
                option = JOptionPane.showConfirmDialog(frame,
                        "Return to main menu? (No will exit)",
                        "Navigation",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    mainMenu();
                } else {
                    System.exit(0);
                }
            }
        });

        // Add components to frame
        frame.getContentPane().removeAll();
        frame.add(new JScrollPane(textArea), BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();

    }


    /**
     private void showSpendingInput() {
     buttonPanel.removeAll();

     textArea.append("What thing did you spend money on in your chosen category?\n");
     //Drop down with past options | New item button -> text box and submit button
     ArrayList <String> itemList = new ArrayList<>();
     itemList = Data.getAddItem();
     JComboBox dropDownMenu = new JComboBox(itemList);

   frame.revalidate();
    frame.repaint();
    }
    */
}
