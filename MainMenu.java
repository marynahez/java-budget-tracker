package ca.ucalgary.cpsc233group.cpsc233projectgui.enums;

public enum MainMenu {
    EXIT("Exit the program"),
    MANAGE_RECENT_EXPENSES("Manage recent expenses"),
    FINANCIAL_STATE("View financial state"),
    FINANCIAL_TIPS("Display financial tips"),
    SAVE_DATA("Save data (You automatically exit after this step)");

    private final String description;

    // Constructor
    MainMenu(String description) {
        this.description = description;
    }

    //Getter
    public String getDescription() {
        return description;
    }
}