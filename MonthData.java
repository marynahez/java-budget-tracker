package ca.ucalgary.cpsc233group.cpsc233projectgui;

import java.util.ArrayList;

/**
 * Class to store and access information on every month that will be recorded
 * E.E, Tut 11
 */
public class MonthData {

    private String month;
    private Double savings;
    private Double earnings;
    private ArrayList<Expenses> allExpensesPerMonth;

    public MonthData(String month) {
        this.month = month;
        this.savings = 0.0;
        this.earnings = 0.0;
        this.allExpensesPerMonth = new ArrayList<>();
    }

    /**
     * Overloaded constructor in case we do not have savings and earnings equal 0
     * @param month
     * @param savings
     * @param earnings
     */
    public MonthData(String month, Double savings, Double earnings){
        this.month = month;
        this.savings = savings;
        this.earnings = earnings;
        this.allExpensesPerMonth = new ArrayList<>();
    }

    public String getMonth() {
        return month;
    }

    public double getSavings() {
        return this.savings;
    }

    public double getEarnings() {
        return this.earnings;
    }

    public ArrayList<Expenses> getExpenses(){return this.allExpensesPerMonth;}

    @Override
    public String toString() {
        return String.format("Month: %s\nSavings: %.2f\nEarnings: %.2f\nExpenses: %s", month, savings, earnings, allExpensesPerMonth);
    }

    /**
     * Function to add Expenses
     * @param expense
     * M.H., T11, 2025.03.22
     * E.E, Tut 11
     */
    public void addExpenses(Expenses expense){
        this.allExpensesPerMonth.add(expense);
    }

    /**
     * Function to show sum all expenses per month and show it.
     * It is static to make it easie, it doesn't change an object but makes sum math and print the result
     * M.H., T11, 2025.03.22
     */
    public double SumAllSpentMoneyPerMonth() {
        double sum = 0.0;
        for (Expenses expenses : this.allExpensesPerMonth) {
            sum += expenses.getAmount();
        }
        return Math.round(sum * 100.0) / 100.0; //Rounds to 2 decimals
    }

    /**
     * addSavings and addEarnings add money to the object data
     * @param saving
     */

    public void addSavings(double saving) {
        this.savings += saving;
    }

    public void addEarnings(double earning) {
        this.earnings += earning;
    }

    /**
     *Function to show savings for a specific month
     * E.E and M.H.
     */
    public double Getmonthsavings(){
        System.out.printf("Savings in %s: %.2f СAD.%n", this.month, this.savings);
        return Math.round(this.savings*100.0)/100.0;
    }

    /**
     * To sum all earnings per all months
     * E.E and M.H.
     */
    public double GetMonthEarnings() {
        System.out.printf("Earnings in %s: %.2f CAD.%n", this.month, this.earnings);
        return Math.round(this.earnings * 100.0) / 100.0;
    }

    public double getTotalSpendingForCategory(String category) {
        double sum = 0.0;
        for (Expenses exp : allExpensesPerMonth) {
            if (exp.getCategory().equalsIgnoreCase(category)) {
                sum += exp.getAmount();
            }
        }
        return sum;
    }

    public void setSavings(double saving){
        this.savings = saving;
    }

    public void setEarnings(double earning){
        this.earnings = earning;
    }

}