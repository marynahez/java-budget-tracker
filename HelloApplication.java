package ca.ucalgary.cpsc233group.cpsc233projectgui.graphics;

import ca.ucalgary.cpsc233group.cpsc233projectgui.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Start the app though JavaFX
 * M.H. T11, 04.16.2025
 */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("/ca/ucalgary/cpsc233group/cpsc233projectgui/hello-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);


        Scene scene = new Scene(fxmlLoader.load(), 700, 350);
        stage.setTitle("Welcome to the Budget Tracker!");

        HelloController controller = fxmlLoader.getController();
        Menu menu = new Menu();

            controller.setMenu(menu);
            stage.setScene(scene);
            stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}