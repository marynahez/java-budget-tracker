package ca.ucalgary.cpsc233group.cpsc233projectgui;

import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Abvs;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Category;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.MainMenu;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Months;
import ca.ucalgary.cpsc233group.cpsc233projectgui.util.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private String optMessage;
    private String selectedMonth;
    private String selectedCategory;
    private String monthToCheck;
    private static final Scanner scanner = new Scanner(System.in);

    private ArrayList<MonthData> months; //All months-objects with all data

    /**
     * Constructor for menu class
     */
    public Menu(){
        this.months= new ArrayList<>();
        this.selectedMonth = "";
        this.selectedCategory = "";
        this.monthToCheck = "";
    }

    /**
     * Add month to the recorded months
     * @param monthData
     */
    public void addMonth(MonthData monthData){
        this.months.add(monthData);
    }

    /**
     * MenuLoop.
     * Run the loop until the user chooses needed option. Looping just though main menu.
     * Stops looping and shows the message of goodbye to the user if they chose "exit".
     * M.H., T11, 2025.02.25
     */
    public void menuLoop() {
        //Welcome message for displaying to the user when the programme starts to work. - M.H., T11, 2025.02.20
        optMessage = """
                                Welcome to the Budget Tracker!
                   Here you can easily track your expenses, set savings targets,
                       and increase your financial opportunities.
            
                To start, please, choose an option you would like to use. Please, enter the number of the option:
            """;

        StringBuilder sb = new StringBuilder();
        sb.append(optMessage);

        //Add mainMenu lines from MainMenu class
        //Ordinal means 0, 1, 2...
        //GetDescription is getter that gives you line of mainMenu
        for (MainMenu menu : MainMenu.values()) {
            sb.append(String.format("\t%d. %s\n", menu.ordinal() + 1, menu.getDescription()));
        }

        System.out.println(sb);

        String choice = scanner.nextLine();

        try {
            int chosenOption = Integer.parseInt(choice);

            // Check if the input is valid
            if (chosenOption < 1 || chosenOption > MainMenu.values().length) {
                System.out.println("Invalid option. Please enter a valid number.");
                menuLoop(); // Restart the menu if invalid
                return;
            }
            System.out.printf("Selected option %d. %s.%n", chosenOption, MainMenu.values()[chosenOption - 1].getDescription());
            switch (chosenOption) {
                case 3:
                    ShowFinancialState();
                    break;
                case 2:
                    ManageRecentExpensesMenu();
                    break;
                case 4:
                    GetFinancialTips();
                    break;
                case 1:
                    System.out.printf("Thank you for using the Budget Tracker!%nHave a wonderful day!%n");
                    System.exit(0);
                    break;
                case 5:
                    try {
                        new FileWriter("ca/ucalgary/cpsc233group/cpsc233projectgui/Storage.csv", false).close();
                        File file = new File("ca/ucalgary/cpsc233group/cpsc233projectgui/Storage.csv");
                        Logger logger = new Logger(file);
                        for(MonthData monthData: this.months){
                            StringBuilder str = new StringBuilder();
                            str.append(String.format("%s,%.2f,%.2f,",monthData.getMonth(),monthData.getSavings(),monthData.getEarnings()));
                            for(Expenses expenses : monthData.getExpenses()){
                                str.append(String.format("%s,%.2f,%s,",expenses.getCategory(), expenses.getAmount(), expenses.getDescription()));
                            }
                            str.append("Done");
                            logger.log(str);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Something went wrong with the file.");
                    }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            menuLoop(); // Restart the menu if invalid input
        }
    }

    private void ManageRecentExpensesMenu() {

        /** Ask a user to write a month for which they want to manage expenses. Check if it is valid.
         * Provide all categories where user can make changes.
         * Depending on what category a user chose go to next function to manage it.
         * M.H., T11, 2025.02.25
          */

        System.out.println("For what month would you like to manage expenses for? ");
        String inputM = scanner.nextLine();


        boolean validMatch = false;

        //Check if month is valid though enum Months
        for (Months month : Months.values()) {
            if (month.name().equalsIgnoreCase(inputM.trim())){
                inputM = inputM.toLowerCase();
                this.selectedMonth = inputM;
                validMatch = true;
            }
        }
        for (Abvs abvs : Abvs.values()){
            if (abvs.name().equalsIgnoreCase(inputM.trim())){
                validMatch = true;
                for (Months month : Months.values()) {
                    if (month.getDescription() == abvs.getDescription()){
                        this.selectedMonth = month.name().toLowerCase();
                            break;
                        }
                    }
            }
        }

            if (validMatch) {
                StringBuilder sb = new StringBuilder();
                System.out.println("Which category you would like to manage expenses for? Please, enter the number of the category.");

                int count = 1;
                for (Category category : Category.values()) {
                    sb.append(String.format("\t%d. %s\n", count, category.editEnum()));
                    count++;
                }

                System.out.println(sb);

                while (true) {
                    String input = scanner.nextLine();
                    int inputC = -1;

                    try {
                        inputC = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue;
                    }

                    switch (inputC) {
                        case 1:
                            this.selectedCategory = "Food";
                            GeneralManage();
                            return;
                        case 2:
                            this.selectedCategory = "Entertainments";
                            GeneralManage();
                            return;
                        case 3:
                            this.selectedCategory = "Utilities";
                            GeneralManage();
                            return;
                        case 4:
                            this.selectedCategory = "Gas/Transport";
                            GeneralManage();
                            return;
                        case 5:
                            this.selectedCategory = "Healthcare";
                            GeneralManage();
                            return;
                        case 6:
                            this.selectedCategory = "Customized expenses";
                            GeneralManage();
                            return;
                        case 7:
                            Savings();
                            return;
                        case 8:
                            Earnings();
                            return;
                        default:
                            System.out.print("Option not recognized. Please enter a valid number. ");
                    }
                }
            }
            else {
                System.out.println("Invalid month. Please try again.");
                ManageRecentExpensesMenu();
            }
    }

    /** GeneralManage function is for:
     * taking input how much money the user spend for different categories;
     * for what items the user spend money;
     * save it in Data.SCheckAndStoreFoodExpense;
     * be able to add multiple times expenses or not;
     * be able to exit the app right away;
     * be able to go back in the main menu.
     * - M.H., T11, 2025.02.22
     * E.E, Tut11
     */
    private void GeneralManage() {
        System.out.println("What thing did you spend money on in your chosen category? ");
        String addItem = scanner.nextLine();
        boolean validname = false;
        while(!validname){
            if(addItem.matches(".*[[a-zA-Z]].*")){//Checks if addItem has at least one letter E.E, Tut
                validname = true;
            }
            else{
                System.out.print("Please put in a name with at least one letter");
                addItem = scanner.nextLine();
        }
        }
        System.out.println("Enter the amount of money you spent in CAD? ");
        String spendMoneyOnCategory0 = scanner.nextLine().trim();
        while (!spendMoneyOnCategory0.matches("\\d+(\\.\\d+)?")) { // "\\." means dot not any symbol, just "." means any symbol. Means can be eiter an integer or double
            System.out.println("Invalid amount. Please write an integer or decimal number (e.g., 520.00).");
            spendMoneyOnCategory0 = scanner.nextLine().trim();
        }
        Double spendMoneyOnCategory1 = Double.parseDouble(spendMoneyOnCategory0);
        System.out.printf("You've assigned %.2f CAD to %s expense.%n", spendMoneyOnCategory1, addItem.trim());
        System.out.printf("You added %s to your expenses.%n", addItem.trim());

        if (this.selectedMonth == null || this.selectedCategory == null){
            System.out.println("Month or category not selected. Return to menu");
            menuLoop();
            return;
        }

        //Adds expenses to data
        Expenses newExpense = new Expenses(this.selectedCategory, spendMoneyOnCategory1, addItem);
        boolean coorectmonth = false;
        for(MonthData monthData: this.months){
            if(this.selectedMonth.equals(monthData.getMonth())){
                coorectmonth = true;
                monthData.addExpenses(newExpense);
            }
        }
        if(!coorectmonth){
            MonthData month = new MonthData(this.selectedMonth);
            month.addExpenses(newExpense);
            this.months.add(month);
        }
        System.out.println("Do you want to add more expenses? ('Yes'/'No')");
        while (true) {
            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase("Yes") || (userInput.equalsIgnoreCase("Y"))) {
                ManageRecentExpensesMenu();
                return;
            } else if (userInput.equalsIgnoreCase("No") || userInput.equalsIgnoreCase("N")) {
                System.out.println("Thank you for entering some expenses.");
                System.out.println("Do you want to see the menu again or you want to exit? Please write 'Menu' or 'Exit')");

                //While loop until the user provide a valid answer
                while (true) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Exit")) {
                        System.exit(0);
                        return;
                    } else if (input.equalsIgnoreCase("Menu")) {
                        menuLoop();
                        return;
                    } else {
                        System.out.println("Invalid answer. Please try again.");
                    }
                }
            }
            else{
                System.out.println("Please say Yes or No");
            }
        }
    }

    /**
     * The savings method takes input from the user about how much money they saved each month and
     * stores that information to an ArrayList of hashmaps in the data class.
     * E.E, Tut
     */
    private void Savings() {
        System.out.println("How much money did you save this month in CAD? ");
        String saving_string = scanner.nextLine();
        while((!saving_string.matches("\\d+(\\.\\d+)?"))){
                System.out.println("Invalid amount. Please write an integer or decimal number (e.g., 520.00).");
                saving_string = scanner.nextLine();
        }
        double saving = Math.round(Double.parseDouble(saving_string) * 100.0)/100.0;//Ensures the double is rounded to 2 decimal places
        System.out.printf("You have saved %.2f in CAD for the month %s%n", saving, this.selectedMonth);
        boolean valid = false;
        for(MonthData monthData: this.months){
            if(this.selectedMonth.equals(monthData.getMonth())){
                monthData.addSavings(saving);
                valid = true;
            }
        }
        if(!valid){
            MonthData month = new MonthData(this.selectedMonth);
            month.addSavings(saving);
            this.months.add(month);
        }
        System.out.println("Do you want to see the menu again or you want to exit? Please write 'Menu' or 'Exit')");
        //While loop until the user provide a valid answer
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Exit")) {
                System.out.println("Thank you for using the budget tracker");
                System.exit(0);
                return;
            } else if (input.equalsIgnoreCase("Menu")) {
                menuLoop();
                return;
            } else {
                System.out.println("Invalid answer. Please input Menu or Exit");
            }
        }
    }

    /**
     * The savings method takes input from the user about how much money they earned each month and
     * stores that information to an ArrayList of hashmaps in the data class.
     * E.E, Tut
     */
    private void Earnings() {System.out.println("How much money did you earn this month in CAD? ");
        String earning_string = scanner.nextLine();
        while(!earning_string.matches("\\d+(\\.\\d+)?")){ // "\\." means dot not any symbol, just "." means any symbol. Means can be eiter an integer or double
                System.out.println("Invalid amount. Please write an integer or decimal number (e.g., 520.00).");
                earning_string = scanner.nextLine();
        }
        double earning = Math.round(Double.parseDouble(earning_string) * 100.0)/100.0; //Ensures the double is rounded to 2 decimal place
        System.out.printf("You have earned %.2f in CAD for the month %s%n", earning, this.selectedMonth);
        boolean valid = false;
        for(MonthData monthData: this.months){
            if(this.selectedMonth.equals(monthData.getMonth())){
                monthData.addEarnings(earning);
                valid = true;
            }
        }
        if(!valid){
            MonthData month = new MonthData(this.selectedMonth);
            month.addEarnings(earning);
            this.months.add(month);
        }

        System.out.println("Do you want to see the menu again or you want to exit? Please write 'Menu' or 'Exit')");
        //While loop until the user provide a valid answer
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Exit")) {
                System.out.println("Thank you for using the budget tracker");
                System.exit(0);
                return;
            } else if (input.equalsIgnoreCase("Menu")) {
                menuLoop();
                return;
            } else {
                System.out.println("Invalid answer. Please input Menu or Exit");
            }
        }
    }

    private void ShowFinancialState() {
        System.out.println("What about your financial status would you like to know?");
        String[] financeoptions = {"Show total expenses for a month", "Show total earnings for a month", "Show total savings for a month",
                "Show total expenses for all months", "Show total earnings for all months", "Show total savings for all months", "Show how long it'll take to achieve" +
                " a savings goal", "Show a breakdown of spending in a category in a month", "Show a breakdown of spending in a category in all months"};
        for(int i = 1; i <= financeoptions.length; i++){
            System.out.println(i + "." + financeoptions[i-1]);
        }
        while (true) {
            String input = scanner.nextLine();
            int inputS = -1;
            try {
                inputS = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number. ");
                continue;
            }
            switch (inputS){
                case 1:
                    ShowAllSpentMoneyPerMonth();
                    return;
                case 2:
                    Showmonthearnings();
                    return;
                case 3:
                    Showmonthsavings();
                    return;
                case 4:
                    Showspendingforallmonths();
                    return;
                case 5:
                    Showsavingsforallmonths();
                    return;
                case 6:
                    Showearningsforallmonths();
                    return;
                case 7:
                    Savingsgoal();
                    return;
                case 8:
                    ShowexpenseonCategoryperMonth();
                    return;
                case 9:
                    ShowTotalCategoricalSpending();
                default:
                    System.out.print("Option not recognized. Please enter a valid number. ");
            }
        }
    }

    /** Function to show an alert showing how much money was spent in a particular month.
     * E.E, Tut11
     */
    private void ShowAllSpentMoneyPerMonth() {
        if (!isGuiCall) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All money spent in the month");
        alert.setHeaderText(String.format("The amount of money spent in the month of %s is:", this.selectedMonth));
        boolean exist_month = false;
        for (MonthData month : this.months) {
            if (month.getMonth().equalsIgnoreCase(this.selectedMonth)) {
                alert.setContentText(String.format("$%.2f", month.SumAllSpentMoneyPerMonth()));
                alert.showAndWait();
                exist_month = true;
            }
        }
        if(!exist_month){
            alert.setContentText("$0.00");
            alert.showAndWait();
        }
    }

    /**
     * Function to show an alert to show earnings for a specific month
     * E.E, Tut11
     */
    private void Showmonthearnings(){
        if (!isGuiCall) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All money earned in the month");
        alert.setHeaderText(String.format("The amount of money earned in the month of %s is:", this.selectedMonth));
        boolean exist_month = false;
        for (MonthData month : this.months) {
            if (month.getMonth().equalsIgnoreCase(this.selectedMonth)) {
                alert.setContentText(String.format("$%.2f", month.getEarnings()));
                alert.showAndWait();
                exist_month = true;
            }
        }
        if(!exist_month){
            alert.setContentText("$0.00");
            alert.showAndWait();
        }
    }

    /**
     * Function to show an alert to show savings for a specific month
     * E.E, Tut 11
     */
    private void Showmonthsavings(){
        if (!isGuiCall) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All money saved in the month");
        alert.setHeaderText(String.format("The amount of money saved in the month of %s is:", this.selectedMonth));
        boolean exist_month = false;
        for (MonthData month : this.months) {
            if (month.getMonth().equalsIgnoreCase(this.selectedMonth)) {
                alert.setContentText(String.format("$%.2f", month.getSavings()));
                alert.showAndWait();
                exist_month = true;
            }
        }
        if(!exist_month){
            alert.setContentText("$0.00");
            alert.showAndWait();
        }
    }

    /**
     * Function to show an alert to show savings for all months
     * E.E, Tut 11
     */
    private void Showsavingsforallmonths(){
        if (!isGuiCall) return;
        double sum = 0.0;
        for(MonthData monthData: this.months){
            sum += monthData.getSavings();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All money saved in all recorded months");
        alert.setHeaderText("The amount of money saved in all recorded months is:");
        alert.setContentText(String.format("$%.2f", sum));
        alert.showAndWait();
    }

    /**
     * Function to show an alert to show earnings for all months
     * E.E, Tut 11
     */
    private void Showearningsforallmonths(){
        double sum = 0.0;
        for(MonthData monthData: this.months){
            sum += monthData.getEarnings();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All money earned in all recorded months");
        alert.setHeaderText("The amount of money earned in all recorded months is:");
        alert.setContentText(String.format("$%.2f", sum));
        alert.showAndWait();
    }

    /**
     * Function to show an alert to show spending for all months
     * E.E, Tut 11
     */
    private void Showspendingforallmonths(){
        if (!isGuiCall) return;
        double sum = 0.0;
        for(MonthData monthData: this.months){
            for(Expenses expenses: monthData.getExpenses()){
                sum += expenses.getAmount();
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All money spent in all recorded months");
        alert.setHeaderText("The amount of money spent in all recorded months is:");
        alert.setContentText(String.format("$%.2f", sum));
        alert.showAndWait();
    }

    /**
     * This method uses a specific month's savings it will take to reach a savings goal without interest
     * E.E, Tut 11
     */
    private void Savingsgoal(){
        if (!isGuiCall) return;
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(20));
        box.setSpacing(10);
        stage.setTitle("Savings goal calculator");
        TextField textField = new TextField();
        textField.setPromptText("What is the desired saving goal?");
        Button button = new Button("Calculate");
        button.setOnAction(event -> {
            int months_needed;
            for (MonthData month : this.months) {
                if(month.getMonth().equalsIgnoreCase(this.selectedMonth)){
                    if(month.getSavings() == 0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Saving goal");
                        alert.setContentText("You have non money saved this month.");
                        alert.showAndWait();
                    }
                    else {
                        try {
                            months_needed = (int) Math.ceil(Double.parseDouble(textField.getText()) / month.getSavings());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Saving goal");
                            alert.setHeaderText(String.format("The number of months you need to save to hit your goal ignoring interest using %s's saavings is:", this.selectedMonth));
                            alert.setContentText(String.format("%d months", months_needed));
                            alert.showAndWait();
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Please ensure that your input is in fact a number");
                            alert.setTitle("Issues with calculation");
                            alert.showAndWait();
                            Savingsgoal();
                        }
                    }
                }
            }
        });
        box.getChildren().addAll(textField,button);
        Scene choiceScene = new Scene(box, 450, 250);
        stage.setTitle("Monthly Category Expense Checker");
        stage.setScene(choiceScene);
        stage.show();

    }

    /**
     * Shows money spent on a category in a single month via an alert
     * E.E, Tut 11
     */
    public void ShowexpenseonCategoryperMonth() {
        if (!isGuiCall) return;
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(20));
        box.setSpacing(10);
        ChoiceBox<String> categories = new ChoiceBox<>();
        categories.setValue("Categories");
        ArrayList<String> list = new ArrayList<>();
        for (Category category : Category.values()) {
            list.add(category.editEnum());
        }
        categories.setItems(FXCollections.observableArrayList(list));
        categories.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.selectedCategory = newVal;
        });
        Button b = new Button("Find the money spent on this category in this month.");
        b.setPrefWidth(350);
        b.setPrefHeight(30);

        b.setOnAction(event -> {
            double sum = 0.0;
            for (MonthData monthData : this.months) {
                if (monthData.getMonth().equalsIgnoreCase(this.selectedMonth)) {
                    for (Expenses expenses : monthData.getExpenses()) {
                        if (expenses.getCategory().equalsIgnoreCase(this.selectedCategory)) {
                            sum += expenses.getAmount();
                        }
                    }
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total expenses in a category in a month");
            alert.setHeaderText(String.format("The money spent on the category %s in the month %s is:", this.selectedCategory, this.selectedMonth));
            alert.setContentText(String.format("$%.2f", sum));
            alert.showAndWait();
        });

        box.getChildren().addAll(categories, b);

        Scene choiceScene = new Scene(box, 450, 250);
        stage.setTitle("Monthly Category Expense Checker");
        stage.setScene(choiceScene);
        stage.show();
    }

    /**
     *Shows money spent on a category in all available months via an alert
     * E.E, Tut 11
     */
    public void ShowTotalCategoricalSpending(){
        if (!isGuiCall) return;
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(20));
        box.setSpacing(10);
        ChoiceBox<String> categories = new ChoiceBox<>();
        categories.setValue("Categories");
        ArrayList<String> list = new ArrayList<>();
        for (Category category : Category.values()) {
            list.add(category.editEnum());
        }
        categories.setItems(FXCollections.observableArrayList(list));
        categories.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.selectedCategory = newVal;
        });
        Button b = new Button("Find the money spent on this category");
        b.setPrefWidth(250);
        b.setPrefHeight(30);
        b.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total expenses in a category in a month");
            alert.setHeaderText(String.format("The money spent on the category %s in all months is:", this.selectedCategory));
            alert.setContentText(String.format("%.2f", getTotalCategoricalSpending(this.selectedCategory)));
            alert.showAndWait();
        });
        box.getChildren().addAll(categories, b);
        Scene choiceScene = new Scene(box, 350, 180);
        stage.setTitle("Monthly Category Expense Checker");
        stage.setScene(choiceScene);
        stage.show();
    }

    public double getTotalCategoricalSpending(String category){
        if (!isGuiCall) return 0.0;
        Double sum = 0.0;
        for(MonthData monthData : this.months){
            for(Expenses expenses: monthData.getExpenses()){
                if(expenses.getCategory().equals(category)){
                    sum += expenses.getAmount();
                }
            }
        }
        return sum;
    }

    /**
     * Gives you either just dumb basic financial tips that just give suggestions how to save money
     * or looks indepth at your spending and tells you how much you could save if you cut a,b,c
     */
    private void GetFinancialTips() {
        if (!isGuiCall) return;
        System.out.println("Choose tip mode:\n1. Basic tips\n2. Smart analysis");
        String choice = scanner.nextLine();
        FinancialTips tips = new FinancialTips(scanner, this);
        tips.provideFinancialTip(choice.equals("2"));
    }


    /**
     * Accessers to use to private information from GUI
     * @return void
     * M.H. T11, 04.12.25
     */
    public void guiShowFinancialState() {
        if (!isGuiCall) {
            return;
        }

        String[] options = {
                "1. Show total expenses for the month",
                "2. Show total earnings for the month",
                "3. Show total savings for the month",
                "4. Show total expenses for all months",
                "5. Show total earnings for all months",
                "6. Show total savings for all months",
                "7. Show how long it'll take to achieve a savings goal",
                "8. Show a breakdown of spending in a category in the month",
                "9. Show a breakdown of spending in a category in all months"
        };

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options[0], options);
        dialog.setTitle("Financial State Options");
        dialog.setHeaderText("Select what you would like to view:");
        dialog.setContentText("Choose option:");

        dialog.showAndWait().ifPresent(selected -> {
            int choice = Integer.parseInt(selected.substring(0, 1));
            switch (choice) {
                case 1 -> ShowAllSpentMoneyPerMonth();
                case 2 -> Showmonthearnings();
                case 3 -> Showmonthsavings();
                case 4 -> Showspendingforallmonths();
                case 5 -> Showearningsforallmonths();
                case 6 -> Showsavingsforallmonths();
                case 7 -> Savingsgoal();
                case 8 -> ShowexpenseonCategoryperMonth();
                case 9 -> ShowTotalCategoricalSpending();
            }
        });
    }

    /**
     * Method to add and edit data in every month via the GUI
     * E.E Tut 11
     * M.H. Tut 11 04.14.2025
     */
    public void guiManageRecentExpensesMenu() {
        if (!isGuiCall) {
            return;
        }

        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        vBox.getChildren().clear();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));


        for(MonthData month : this.months){
            if(month.getMonth().equalsIgnoreCase(this.selectedMonth)){

                Label labelSetSavings = new Label(String.format("Set month savings (skip ✓)"));
                CheckBox checkBoxSetSavings = new CheckBox();
                checkBoxSetSavings.setTooltip(new Tooltip(String.format("Skip setting savings")));
                HBox labelAndCheckBox = new HBox(labelSetSavings, checkBoxSetSavings);
                labelAndCheckBox.setSpacing(10);
                labelAndCheckBox.setAlignment(Pos.CENTER);
                gridPane.add(labelAndCheckBox, 0, 0);
                TextField set_savings = new TextField();
                gridPane.add(set_savings, 0, 1);
                set_savings.setPrefWidth(100);
                set_savings.setPromptText(String.format("Current: $%.2f", month.getSavings()));

                Label labelSetEarnings = new Label(String.format("Set month earnings (skip ✓):"));
                CheckBox checkBoxSetEarnings = new CheckBox();
                checkBoxSetEarnings.setTooltip(new Tooltip(String.format("Skip setting earnings")));
                HBox labelAndCheckBox2 = new HBox(labelSetEarnings, checkBoxSetEarnings);
                labelAndCheckBox2.setSpacing(10);
                labelAndCheckBox2.setAlignment(Pos.CENTER);
                gridPane.add(labelAndCheckBox2, 0, 2);
                TextField set_earning = new TextField();
                gridPane.add(set_earning, 0, 3);
                set_earning.setPrefWidth(100);
                set_earning.setPromptText(String.format("Current: $%.2f", month.getEarnings()));

                Label labelAddSavings = new Label(String.format("Add to savings (skip ✓)"));
                CheckBox checkBoxAddSavings = new CheckBox();
                checkBoxAddSavings.setTooltip(new Tooltip(String.format("Skip setting savings:")));
                HBox labelAndCheckBox3 = new HBox(labelAddSavings, checkBoxAddSavings);
                labelAndCheckBox3.setSpacing(10);
                labelAndCheckBox3.setAlignment(Pos.CENTER);
                gridPane.add(labelAndCheckBox3, 1, 0);
                TextField add_savings = new TextField();
                gridPane.add(add_savings, 1, 1);
                add_savings.setPrefWidth(100);
                add_savings.setPromptText(String.format("Current: $%.2f", month.getSavings()));

                Label labelAddEarnings = new Label(String.format("Add to earnings: (skip ✓)"));
                CheckBox checkBoxAddEarnings = new CheckBox();
                checkBoxAddEarnings.setTooltip(new Tooltip(String.format("Skip setting earnings")));
                HBox labelAndCheckBox4 = new HBox(labelAddEarnings, checkBoxAddEarnings);
                labelAndCheckBox4.setSpacing(10);
                labelAndCheckBox4.setAlignment(Pos.CENTER);
                gridPane.add(labelAndCheckBox4, 1, 2);
                TextField add_earnings = new TextField();
                gridPane.add(add_earnings, 1, 3);
                add_earnings.setPrefWidth(100);
                add_earnings.setPromptText(String.format("Current: $%.2f", month.getEarnings()));

                ChoiceBox<String> categories = new ChoiceBox<>();
                categories.setValue("Categories for expense:");
                ArrayList<String> list = new ArrayList<>();
                for (Category category : Category.values()) {
                    list.add(category.editEnum());
                }
                list.removeLast();
                list.removeLast();

                categories.setItems(FXCollections.observableArrayList(list));
                categories.valueProperty().addListener((obs, oldVal, newVal) -> {
                    this.selectedCategory = newVal;
                });

                Label labelDescExpenses = new Label(String.format("Description of expense:"));
                TextField desc_exp = new TextField();
                desc_exp.setPromptText(String.format("Description of expense:"));

                Label labelExpAmount = new Label("Expense amount:");
                TextField exp_amount = new TextField();
                exp_amount.setPromptText("Expense amount:");

                Button button = new Button(String.format("Edit month %s", month.getMonth()));
                button.setPrefHeight(35);
                button.setPrefWidth(200);
                button.setOnAction(event->{
                    Alert numerror = new Alert(Alert.AlertType.ERROR);
                    numerror.setHeaderText("Error due to wrong data inputs");
                    numerror.setContentText("Please ensure that everything that is supposed to be a number is a number and required textfields are filled.");

                    boolean validForUpdates = false;
                    if(!checkBoxSetSavings.isSelected()) {
                        try{
                            month.setSavings(Double.parseDouble(set_savings.getText()));
                            validForUpdates = true;
                            if (checkBoxSetSavings.isSelected()) {
                                validForUpdates = true;
                            }
                        } catch (NumberFormatException e) {
                            numerror.showAndWait();
                            return;
                        }
                    }
                    if(!checkBoxAddSavings.isSelected()) {
                        try{
                            month.addSavings(Double.parseDouble(add_savings.getText()));
                            validForUpdates = true;
                            if (checkBoxAddSavings.isSelected()) {
                                validForUpdates = true; }
                        } catch (NumberFormatException e) {
                            numerror.showAndWait();
                            return;
                        }
                    }
                    if(!checkBoxSetEarnings.isSelected()) {
                        try{
                            month.setEarnings(Double.parseDouble(set_earning.getText()));
                            validForUpdates = true;
                            if (checkBoxSetEarnings.isSelected()) {
                                validForUpdates = true; }
                        }
                        catch (NumberFormatException e){
                            numerror.showAndWait();
                            return;
                        }
                    }
                    if(!checkBoxAddEarnings.isSelected()) {
                        try{
                            month.addEarnings(Double.parseDouble(add_earnings.getText()));
                            validForUpdates = true;
                            if (checkBoxAddSavings.isSelected()) {
                                validForUpdates = true; }
                        } catch (NumberFormatException e) {
                            numerror.showAndWait();
                            return;
                        }
                    }
                    if(!this.selectedCategory.isEmpty() && !desc_exp.getText().isEmpty() && !exp_amount.getText().isEmpty()){
                        for(Category category: Category.values()){
                            if(category.name().equalsIgnoreCase(this.selectedCategory)){
                                try{
                                    month.addExpenses(new Expenses(this.selectedCategory, Double.parseDouble(exp_amount.getText()), desc_exp.getText()));
                                    validForUpdates = true;
                                } catch (NumberFormatException e) {
                                    numerror.showAndWait();
                                    return;
                                }
                            }
                        }
                        if (validForUpdates) {
                            Alert validConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                            validConfirm.setTitle("Success");
                            validConfirm.setHeaderText("Data successfully updated!");
                            validConfirm.showAndWait();
                        }
                    }
                });

                //If CheckBox is selected, not allowed to edit Text Field
                checkBoxSetSavings.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    set_savings.setDisable(newVal);
                    if (newVal) { set_savings.setText("0.0"); }
                    return;
                });
                checkBoxAddSavings.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    add_savings.setDisable(newVal);
                    if (newVal) { add_savings.setText("0.0"); }
                    return;
                });
                checkBoxSetEarnings.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    set_earning.setDisable(newVal);
                    if (newVal) { set_earning.setText("0.0"); }
                    return;
                });
                checkBoxAddEarnings.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    add_earnings.setDisable(newVal);
                    if (newVal) { add_earnings.setText("0.0"); }
                    return;
                });

                Button goBack = new Button("Go back");
                goBack.setOnAction(event->{
                    stage.close();
                });


                Label heading = new Label(String.format("Editing data for %s", month.getMonth()));
                heading.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
                heading.setMaxWidth(Double.MAX_VALUE);
                HBox hbox = new HBox(heading);
                hbox.setPadding(new Insets(2, 2, 2, 2));
                hbox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(hbox);

                vBox.getChildren().addAll(gridPane);
                vBox.getChildren().addAll(categories,labelDescExpenses,desc_exp,labelExpAmount,exp_amount, button, goBack);
                Scene scene = new Scene(vBox,500,500);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    //runs the code for financial tips
    public void guiGetFinancialTips() {
        Stage stage = new Stage();
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        Label title = new Label("Financial Tips"); //Makes a title

        //two buttons for tip type
        Button smartTipsButton = new Button("Smart Tips");
        Button simpleTipsButton = new Button("Simple Tips");

        smartTipsButton.setOnAction(e -> showSmartTips(stage));
        simpleTipsButton.setOnAction(e -> showSimpleTips(stage));

        vBox.getChildren().addAll(title, smartTipsButton, simpleTipsButton);

        Scene scene = new Scene(vBox, 300, 200);
        stage.setTitle("Financial Tips");
        stage.setScene(scene);
        stage.show();
    }

    private void showSmartTips(Stage parentStage) {
        Stage stage = new Stage();
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(20));

        Label title = new Label("Smart Financial Tips");

        Label analysisLabel = new Label("Analysis of your spending data:");

        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> categoryTotals = new ArrayList<>();

        for (MonthData monthData : months) {
            for (Expenses expense : monthData.getExpenses()) {
                String category = expense.getCategory();
                boolean found = false;

                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).equals(category)) {
                        categoryTotals.set(i, categoryTotals.get(i) + expense.getAmount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    categories.add(category);
                    categoryTotals.add(expense.getAmount());
                }
            }
        }

        VBox spendingBox = new VBox(5);
        spendingBox.getChildren().add(new Label("Spending by Category:"));

        for (int i = 0; i < categories.size(); i++) {
            spendingBox.getChildren().add(new Label(
                    String.format("- %s: $%.2f", categories.get(i), categoryTotals.get(i))
            ));
        }
        //calculate total savings
        double totalSavings = 0;
        for (MonthData monthData : months) {
            totalSavings += monthData.getSavings();
        }

        Label savingsLabel = new Label(
                String.format("\nTotal Savings: $%.2f", totalSavings)
        );

        //savings goal input
        Label goalLabel = new Label("Enter your savings goal:");
        TextField goalField = new TextField();
        goalField.setPromptText("Amount in CAD");

        Button calculateButton = new Button("Calculate Suggestions");
        VBox suggestionsBox = new VBox(5);

        double finalTotalSavings = totalSavings;
        calculateButton.setOnAction(e -> {
            try {
                double savingsGoal = Double.parseDouble(goalField.getText());
                suggestionsBox.getChildren().clear();

                if (finalTotalSavings >= savingsGoal) {
                    suggestionsBox.getChildren().add(new Label(
                            "Congratulations! You've reached your savings goal."
                    ));
                } else {
                    double needed = savingsGoal - finalTotalSavings;
                    suggestionsBox.getChildren().add(new Label(
                            String.format("You need to save $%.2f more", needed)
                    ));

                    suggestionsBox.getChildren().add(new Label("\nSuggestions:"));

                    for (int i = 0; i < categories.size(); i++) {
                        if (categoryTotals.get(i) > 100) {
                            double potential = categoryTotals.get(i) * 0.15;
                            suggestionsBox.getChildren().add(new Label(
                                    String.format("- Reduce %s by 15%% to save $%.2f",
                                            categories.get(i), potential)
                            ));
                        }
                    }

                    suggestionsBox.getChildren().add(new Label(
                            String.format("- Save $%.2f monthly to reach goal in 6 months", needed / 6)
                    ));
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a valid number");
                alert.showAndWait();
            }
        });

        vBox.getChildren().addAll(
                title, analysisLabel, spendingBox, savingsLabel,
                goalLabel, goalField, calculateButton, suggestionsBox
        );

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 450, 400);
        stage.setTitle("Smart Financial Tips");
        stage.setScene(scene);
        stage.show();
    }

    private void showSimpleTips(Stage parentStage) {
        Stage stage = new Stage();
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(20));

        //title
        Label title = new Label("Simple Financial Tips");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //category selection
        Label categoryLabel = new Label("Select a category:");
        ChoiceBox<String> categoryChoice = new ChoiceBox<>();

        for (Category category : Category.values()) {
            categoryChoice.getItems().add(category.editEnum());
        }
        categoryChoice.setValue(Category.FOOD.editEnum());

        //tips display
        TextArea tipsArea = new TextArea();
        tipsArea.setEditable(false);
        tipsArea.setWrapText(true);
        tipsArea.setPrefHeight(250);

        //show tips button
        Button showTipsButton = new Button("Show Tips");
        showTipsButton.setOnAction(e -> {
            String selected = categoryChoice.getValue();
            tipsArea.setText(getTipsForCategory(selected));
        });

        vBox.getChildren().addAll(
                title, categoryLabel, categoryChoice, showTipsButton, tipsArea
        );

        Scene scene = new Scene(vBox, 400, 400);
        stage.setTitle("Simple Financial Tips");
        stage.setScene(scene);
        stage.show();
    }

    private String getTipsForCategory(String category) { //used ai for some suggestions since i dont wanna just say "spend less" for each one
        return switch (category) {
            case "FOOD" -> """
                    - Meal plan to reduce impulse buys
                    - Buy generic brands when possible
                    - Limit dining out to special occasions
                    - Use coupons and loyalty programs
                    - Buy in bulk for frequently used items""";
            case "ENTERTAINMENTS" -> """
                    - Set entertainment budget limits
                    - Look for free community events
                    - Cancel unused subscriptions
                    - Wait for movies to leave theaters
                    - Use student/senior discounts when available""";
            case "UTILITIES_AND_BILLS" -> """
                    - Use energy-efficient appliances
                    - Compare utility providers
                    - Fix water leaks promptly
                    - Lower thermostat in winter
                    - Unplug devices when not in use""";
            case "GAS_AND_TRANSPORT" -> """
                    - Carpool or use public transit
                    - Maintain vehicle regularly
                    - Combine errands to reduce trips
                    - Use apps to find cheapest gas
                    - Consider biking for short distances""";
            case "HEALTHCARE" -> """
                    - Use generic medications
                    - Take preventive care seriously
                    - Compare medical service prices
                    - Use telehealth when appropriate
                    - Ask about payment plans for large bills""";
            default -> """
                    - Track your spending in this category
                    - Set a monthly budget limit
                    - Review expenses weekly
                    - Look for cheaper alternatives
                    - Prioritize needs over wants""";
        };
    }


    public void setGuiCall(boolean guiCall) {
        this.isGuiCall = guiCall;
    }



    //______________________________ GETTER METHODS/SETTER METHODS ______________________________
    public String getOptMessage(){return optMessage;}

    public void setSelectedMonth(String month){selectedMonth = month;}
    public String getSelectedMonth(){return selectedMonth;}
    public void setSelectedCategory(String category){selectedCategory = category;}
    public String getSelectedCategory(){return selectedCategory;}
    private boolean isGuiCall = false;

    public ArrayList <MonthData> getMonthData() { //Get all month objects for checks in MonthData
        return this.months;
    }
}