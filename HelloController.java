package ca.ucalgary.cpsc233group.cpsc233projectgui.graphics;



import ca.ucalgary.cpsc233group.cpsc233projectgui.Expenses;
import ca.ucalgary.cpsc233group.cpsc233projectgui.Menu;
import ca.ucalgary.cpsc233group.cpsc233projectgui.MonthData;
import ca.ucalgary.cpsc233group.cpsc233projectgui.enums.Months;
import ca.ucalgary.cpsc233group.cpsc233projectgui.util.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class HelloController {
    @FXML private AnchorPane Anchorpanemain;
    private ca.ucalgary.cpsc233group.cpsc233projectgui.Menu menu;



    /**
     * Initialize menu staff when the app starts running
     * M.H. 04.12.2025
     */
    @FXML
    public void initialize() {
        showMonths();
    }


    /**
     * Accessor to create an object of mnu
     * @param menu
     * M.H. 04.11.2025
     */
    @FXML
    public void setMenu(Menu menu){
        this.menu = menu;
    }

    /**
     * Save all changes that the user made in csv.
     * It is suggested to choose csv file but user can use any other one.
     * M.H. T11 04.11.2025
     */
    @FXML
    public void onSaveAsButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file to save data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Budget tracker files (*csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file, false);
                fileWriter.close();
                Logger logger = new Logger(file);

                for (MonthData monthData : menu.getMonthData()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.format("%s,%.2f,%.2f,", monthData.getMonth(), monthData.getSavings(), monthData.getEarnings()));
                    for (Expenses expenses : monthData.getExpenses()) {
                        stringBuilder.append(String.format("%s,%.2f,%s,Done", expenses.getCategory(), expenses.getAmount(), expenses.getDescription()));
                    }
                    logger.log(stringBuilder.toString());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("All new financial data has been saved!");
                    alert.showAndWait();
                }
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Error while saving data!");
                alert.showAndWait();
            }

        }
    }

    /**
     * Save in a default file all data "Storage.csv"
     * M.H. T11, 04.16.2025
     */
    @FXML
    public void onSaveButtonClick() {
        File fileCsv = new File("Storage.csv");
        try {
            FileWriter fileWriter = new FileWriter(fileCsv, false);
            fileWriter.close();
            Logger logger = new Logger(fileCsv);
            for (MonthData monthData : menu.getMonthData()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("%s,%.2f,%.2f,", monthData.getMonth(), monthData.getSavings(), monthData.getEarnings()));
                for (Expenses expenses : monthData.getExpenses()) {
                    stringBuilder.append(String.format("%s,%.2f,%s,Done", expenses.getCategory(), expenses.getAmount(), expenses.getDescription()));
                }
                logger.log(stringBuilder.toString());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("All new financial data has been saved!");
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error while saving data!");
            alert.showAndWait();
        }
    }

    /**
     * Load all saved data of user from a file of their choosing
     * The csv files are suggested but other choices are available
     * M.H. T11 04.11.2025
     * E.E Tut 11
     */
    @FXML
    public void onLoadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file to save data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Budget tracker files (*csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String month;
                while ((month = bufferedReader.readLine()) != null) {
                    String[] data = month.split(",");
                    MonthData monthData = new MonthData(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
                    int i = 6;
                    while (!data[i].equals("Done")) {
                        Expenses expense = new Expenses(data[i-3], Double.parseDouble(data[i-2]), data[i-1]);
                        monthData.addExpenses(expense);
                        i = 6;
                    }
                    menu.addMonth(monthData);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("All financial data has been loaded!");
                    alert.showAndWait();
                    showMonths();
                    return;

                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Error while loading data!");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File has no information");
            alert.showAndWait();
        }
    }

    /**
     * Quit the application
     * M.H. T11 04.11.2025
     */
    @FXML
    public void onExitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Thank you for using the Budget Tracker! \n" +
                "Have a wonderful day!");
        alert.showAndWait();
        System.exit(0);

    }

    /**
     * Shows information about project
     * E.E Tut 11
     */
    @FXML
    public void AboutbuttonClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("""
                Project members: Ebosetale Esekheilu, Maryna Hez & Stya Indriliunas
                Emails: ebose.esekheilu@ucalgary.ca, maryna.hez@ucalgary.ca, stya.indriliunas@ucalgary.ca
                Tutorial: Tut 11
                This is a budget tracker that takes different months of the year and you can add and edit expenses, savings and earnings
                for each month and you can view and have some information as you please depending on your needs.""");
        alert.show();
    }

    /**
     * Show usable month buttons at the start of the application
     * E.E Tut 11
     * M.H. T11 04.11.2025
     */
    @FXML
    public void showMonths() {
        int row = 0;
        int column = 0;
        for (Months month : Months.values()) {
            Button monthb = new Button(month.name());
            monthb.setPrefHeight(35.0);
            monthb.setPrefWidth(150.0);
            monthb.setOnAction(actionEvent -> {
                boolean correctmonth = false;
                menu.setSelectedMonth(month.name());
                for(MonthData monthData: menu.getMonthData()){
                    if(menu.getSelectedMonth().equals(monthData.getMonth())){
                        correctmonth = true;
                    }
                }
                if(!correctmonth){
                    menu.addMonth(new MonthData(menu.getSelectedMonth()));
                }
            Stage stage = new Stage();
            VBox box = new VBox();
            box.setPadding(new Insets(20));
            box.setSpacing(10);

            Label choiseQuetion = new Label("What would you like to do?");
            Button showFinState = new Button("Show Financial State");
            Button saveFinState = new Button("Manage Recent Expenses");
            Button financialTips = new Button("See Financial Tips");
            Button goBack = new Button("Go Back");

                showFinState.setOnAction(event -> {
                    menu.setSelectedMonth(month.name().toLowerCase());
                    menu.setGuiCall(true);
                    menu.guiShowFinancialState();
                    menu.setGuiCall(false);
                    stage.close();
                });
                saveFinState.setOnAction(event -> {
                    menu.setSelectedMonth(month.name().toLowerCase());
                    menu.setGuiCall(true);
                    menu.guiManageRecentExpensesMenu();
                    menu.setGuiCall(false);
                    stage.close();
                });
                financialTips.setOnAction(event -> {
                    menu.setSelectedMonth(month.name().toLowerCase());
                    menu.setGuiCall(true);
                    menu.guiGetFinancialTips();
                    menu.setGuiCall(false);
                    stage.close();
                });
                goBack.setOnAction(event -> {
                    stage.close();
                });
                box.getChildren().addAll(choiseQuetion, showFinState, saveFinState, financialTips, goBack);
                Scene choiceScene = new Scene(box, 300, 200);
                stage.setScene(choiceScene);
                stage.setTitle("Choose action for " + month.name());
                stage.show();
            });
            AnchorPane.setTopAnchor(monthb, 100.0 + (row * 50.0));
            AnchorPane.setLeftAnchor(monthb, 75 + (column * 200.0));
            Anchorpanemain.getChildren().add(monthb);
            column++;
            if(column == 3){
                row += 1;
                column = 0;
            }
        }
    }
}