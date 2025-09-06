package ca.ucalgary.cpsc233group.cpsc233projectgui;

import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Category;

import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTips{
    private Scanner scanner = new Scanner(System.in);
    private Menu menu = new Menu();

    public FinancialTips(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void provideFinancialTip(boolean smartTips){
        if (smartTips){
            SmartFinancialTips();
        } else {
            DumbFinancialTips();
        }
    }

    /**
     * Gives financial tips but does analysis and provides betting saving info
     * Still pretty dumb, intertwine more with other functions (Create class to handle logic?)
     * This func still needs work so it can give smarter + more customized tips about spending and earning
     * Might need to add a file saving/save clearing system so i could get enough data to do some of the fancier stuff
     * This big block of text is here to mainly draw attention so stya doesnt forget to work on this at a later point
     * becuase i forget to read comments :>
     */
    public void SmartFinancialTips() {
        /**
         Data.AddExpense("january", "Food", 50.0, "Groceries");
         Data.AddExpense("january", "Entertainments", 20.0, "Movies");
         Data.AddExpense("february", "Food", 60.0, "Restaurant");
         Data.AddExpense("february", "Entertainments", 30.0, "Concert");
         */ //Uncomment these to have some data inputted automatically to test smart tips
        System.out.println("Smart Financial Tips!");
        System.out.println("Here is our analysis of your spending data:");

        //Gets Month data
        ArrayList<MonthData> monthsData = menu.getMonthData();

        //Spending by cat
        System.out.println("Spending by Category:");
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> categoryTotals = new ArrayList<>();

        for (MonthData monthData : monthsData) {
            for (Expenses expense : monthData.getExpenses()){
                String category = expense.getCategory();
                boolean found = false;

                for (int i = 0; i < categories.size(); i++){
                    if (categories.get(i).equals(category)){
                        categoryTotals.set(i,categoryTotals.get(i) + expense.getAmount());

                        found = true;
                        break;
                    }
                }
                if (!found){
                    categories.add(category);
                    categoryTotals.add(expense.getAmount());
                }
            }
        }

        for(int i = 0; i < categories.size(); i++){
            System.out.printf("- %-20s: $%.2f%n", categories.get(i), categoryTotals.get(i));
        }

        double totalSavings = 0;
        for(MonthData monthData : monthsData){
            totalSavings += monthData.getSavings();
        }

        //Comparing to saving goal
        System.out.printf("\nTotal Saving Across All Months: $%.2f%n", totalSavings);
        System.out.print("Enter your savings goal in CAD: ");

        System.out.print("Enter your savings goal in CAD: ");
        double savingsGoal = Double.parseDouble(scanner.nextLine());

        if (totalSavings >= savingsGoal) {
            System.out.println("Congratulations! You've already reached your savings goal.");
        } else {
            double needed = savingsGoal - totalSavings;
            System.out.printf("You need to save $%.2f more to reach your goal.\n", needed);

            //Saving Suggestions
            System.out.println("\nSuggestions to reach your goal faster:");
            for (int i = 0; i < categories.size(); i++) {
                if (categoryTotals.get(i) > 100) {  // Only suggest for significant spending
                    double potential = categoryTotals.get(i) * 0.15;
                    System.out.printf("- Reduce %s spending by 15%% to save $%.2f%n",
                            categories.get(i), potential);
                }
            }
            System.out.printf("- Save $%.2f monthly to reach goal in 6 months%n", needed / 6);
        }
        returnToMenu();
    }

    /**
     * Gives financial tips but doesn't do any analysis or anything and just gives some tips
     */
    public static void DumbFinancialTips() {
        //DUMB FINANCIAL TIPS SYSTEM
        System.out.println("Which category would you like financial tips for?");
        for (Category category : Category.values()) {
            System.out.printf("\n%s Tips:%n", category.editEnum());
            //Switch case for different tip types :/
            switch (category) {
                case FOOD -> System.out.println("""
                        - Meal plan to reduce impulse buys
                        - Buy generic brands when possible
                        - Limit dining out to special occasions""");

                case ENTERTAINMENTS -> System.out.println("""
                        - Set entertainment budget limits
                        - Look for free community events
                        - Cancel unused subscriptions""");

                case UTILITIES_AND_BILLS -> System.out.println("""
                        - Use energy-efficient appliances
                        - Compare utility providers
                        - Fix water leaks promptly""");

                case GAS_AND_TRANSPORT -> System.out.println("""
                        - Carpool or use public transit
                        - Maintain vehicle regularly
                        - Combine errands to reduce trips""");

                case HEALTHCARE -> System.out.println("""
                        - Use generic medications
                        - Take preventive care seriously
                        - Compare medical service prices""");

                default -> System.out.println("- Track your spending in this category");
            }
        }
    }

    private void returnToMenu() {
        System.out.println("\nPress Enter to return to menu...");
        scanner.nextLine();
        menu.menuLoop();
    }
}
