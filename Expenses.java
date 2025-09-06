package ca.ucalgary.cpsc233group.cpsc233projectgui;

/**
 * Class to store and access information on every month that will be recorded
 * E.E, Tut 11
 */
public class Expenses {
    private String category;
    private Double amount;
    private String description;

    /**
     * Constructor.  Save each expense as an object
     *
     * @param aCategory    (which category this expense is from)
     * @param aAmount      (how much the user spent for this expense)
     * @param dDescription (what exactly the user bought)
     * @author - M.H., T11, 2025.03.22
     */
    public Expenses(String aCategory, Double aAmount, String dDescription) {
        this.category = aCategory;
        this.amount = aAmount;
        this.description = dDescription;

    }

    /**
     * Getter to be able to use private data in other functions
     * @author - M.H., T11, 2025.03.22
     */
    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("enums.Category: %s, Amount: %s, Description: %s", category, amount, description);
    }
}