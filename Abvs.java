package ca.ucalgary.cpsc233group.cpsc233projectgui.enums;

import java.util.ArrayList;

public enum Abvs { //Initiate valid month abbreviations Stya and M.H.
    JAN(1),
    FEB(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUG(8),
    SEP(9),
    OCT(10),
    NOV(11),
    DEC(12);
    private final int description;

    // Constructor
    Abvs(int desc) {
        this.description = desc;
    }

    //Getter
    public int getDescription() {
        return description;
    }

    /**
     * A function created to convert ENUM to well-written text
     * (ex. "UTILITIES_AND_BILLS" to "Utilities and bills").
     * M.H. 22.03.25
     */
    private String editEnum (){
        String lowerCase = this.name();
        // Explanation of return
        // 1. We take first letter and make it uppercase
        // 2. We take lower case all the rest of the word except first letter
        // 2. We took index 1 as first letter already was used
        // 3. Replace "_" to " "
        return (Character.toUpperCase(lowerCase.charAt(0)) + lowerCase.substring(1)).replace("_", " ");
    }
}