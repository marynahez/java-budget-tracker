package ca.ucalgary.cpsc233group.cpsc233projectgui.enums;

import java.util.ArrayList;

public enum Category {
    FOOD(1),
    ENTERTAINMENTS(2),
    UTILITIES_AND_BILLS(3),
    GAS_AND_TRANSPORT(4),
    HEALTHCARE(5),
    CUSTOMIZED_EXPENSES(6),
    SAVINGS(7),
    EARNINGS(8);

    private final int description;

    // Constructor
    Category(int description) {
        this.description = description;
    }


    /**
     * A function created to convert ENUM to well-written text
     * (ex. "UTILITIES_AND_BILLS" to "Utilities and bills").
     * M.H. 22.03.25
     */
    public String editEnum (){
        String lowerCase = this.name();
        // Explanation of return
        // 1. We take first letter and make it uppercase
        // 2. We take lower case all the rest of the word except first letter
        // 2. We took index 1 as first letter already was used
        // 3. Replace "_" to " "
        return (Character.toUpperCase(lowerCase.charAt(0)) + lowerCase.substring(1)).replace("_", " ");
    }
}