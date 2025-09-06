package ca.ucalgary.cpsc233group.cpsc233projectgui;

import   static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DataTest {

    /**
     * Tests for AddExpense function from MonthData to see last added expense, including:
     * category, price, item description.
     * E.E tut 11
     */
    @org.junit.jupiter.api.Test
    void testAddExpense1() {
        MonthData January = new MonthData("January");
        January.addExpenses(new Expenses("Entertainment", 54.63,"Movies"));
        Expenses checkExpense = new Expenses("Entertainment", 54.63,"Movies");
        assertEquals(checkExpense.toString(),January.getExpenses().get(0).toString());
    }

    @org.junit.jupiter.api.Test
    void testAddExpense2() {
        MonthData may = new MonthData("May");
        may.addExpenses(new Expenses("Utilities", 55.55, "LLL"));
        Expenses checkExpense = new Expenses("Utilities", 55.55, "LLL");
        assertEquals(checkExpense.toString(), may.getExpenses().get(0).toString());
    }

    /**
     * Tests to check if the function "sumAllSpentMoneyPerMonth" correctly sum all expenses per month for all categories.
     * E.E tut 11
     */

    @org.junit.jupiter.api.Test
    void testSumAllSpentMoneyPerMonth() {
        MonthData December = new MonthData("December");
        December.addExpenses(new Expenses("Gas/Transport", 100.00, "C-Train tickets"));
        December.addExpenses(new Expenses("Food", 400.80, "Brother's birthday"));
        December.addExpenses(new Expenses("Entertainment", 1000.19, "Trip to Vancouver"));

        assertEquals(1500.99, December.SumAllSpentMoneyPerMonth());
        }

    @Test
    void testSumAllSpentMoneyPerMonth2() {
        MonthData December = new MonthData("June");
        December.addExpenses(new Expenses("Utilities", 200.30, "Water bill"));
        December.addExpenses(new Expenses("Healthcare", 500.80, "Birth control"));
        December.addExpenses(new Expenses("Entertainment", 200.19, "Opera house"));

        assertEquals(901.29, December.SumAllSpentMoneyPerMonth());
    }

    /**
     * Tests for Addsavings() function in data to check the last added saving
     * E.E
     */
    @Test
    void testAddsavings1(){
        MonthData January = new MonthData("January");
        January.addSavings(350.45);
        January.addSavings(40.00);
        assertEquals(390.45,January.getSavings());
    }

    @Test
    void testAddsavings2(){
        MonthData January = new MonthData("January");
        January.addSavings(300.45);
        January.addSavings(140.00);
        assertEquals(440.45,January.getSavings());
    }


    /**
     * Tests for Addsearnings() function in data to check the last added saving
     * E.E
     */
    @Test
    void testAddearnings(){
        MonthData January = new MonthData("January");
        January.addEarnings(350.45);
        January.addEarnings(40.00);
        assertEquals(390.45,January.getEarnings());
    }

    @Test
    void testAddearnings2(){
        MonthData January = new MonthData("January");
        January.addEarnings(2350.45);
        January.addEarnings(2140.00);
        assertEquals(4490.45,January.getEarnings());
    }

    /**
     * Tests for Getmonthearnings() to see if it provides the correct value
     * E.E
     */

    @Test
    void testGetmonthearnings(){
        MonthData January = new MonthData("January");
        January.addEarnings(350.45);
        January.addEarnings(40.00);
        assertEquals(390.45, January.GetMonthEarnings());
    }

    @Test
    void testGetmonthearnings2(){
        MonthData January = new MonthData("January");
        January.addEarnings(2350.45);
        January.addEarnings(2140.00);
        assertEquals(4490.45, January.GetMonthEarnings());
    }

    /**
     * Tests for Getmonthsavings() to see if it provides the correct value
     * E.E
     */
    @Test
    void testGetmonthsavings(){
        MonthData January = new MonthData("January");
        January.addSavings(350.45);
        January.addSavings(40.00);
        assertEquals(390.45, January.Getmonthsavings());
    }

    @Test
    void testGetmonthsavings2(){
        MonthData January = new MonthData("January");
        January.addSavings(300.45);
        January.addSavings(140.00);
        assertEquals(440.45, January.Getmonthsavings());
    }

    /**
     * Tests for getTotalSpendingForCategory to see if it provides the correct value
     * E.E
     */
    @Test
    void getTotalSpendingForCategory(){
        MonthData December = new MonthData("December");
        December.addExpenses(new Expenses("Gas/Transport", 100.00, "C-Train tickets"));
        December.addExpenses(new Expenses("Food", 400.80, "Brother's birthday"));
        December.addExpenses(new Expenses("Entertainment", 1000.19, "Trip to Vancouver"));
        assertEquals(100.0,December.getTotalSpendingForCategory("Gas/Transport"));
    }

    @Test
    void getTotalSpendingForCategory2(){
        MonthData June = new MonthData("June");
        June.addExpenses(new Expenses("Gas/Transport", 100.00, "C-Train tickets"));
        June.addExpenses(new Expenses("Food", 400.80, "Brother's birthday"));
        June.addExpenses(new Expenses("Entertainment", 1000.19, "Trip to Vancouver"));
        assertEquals(1000.19,June.getTotalSpendingForCategory("Entertainment"));
    }

    @Test
    void addMultipleExpenses(){
        MonthData May = new MonthData("May");
        May.addExpenses(new Expenses("Gas/Transport", 120.50, "Taxi"));
        May.addExpenses(new Expenses("Gas/Transport", 400.00, "Rented a car"));
        assertEquals(520.50, May.getTotalSpendingForCategory("Gas/Transport"));
    }

}