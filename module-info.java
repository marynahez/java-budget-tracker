module ca.ucalgary.cpsc233group.cpsc233projectgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ca.ucalgary.cpsc233group.cpsc233projectgui to javafx.fxml;
    exports ca.ucalgary.cpsc233group.cpsc233projectgui;
    exports ca.ucalgary.cpsc233group.cpsc233projectgui.util;
    opens ca.ucalgary.cpsc233group.cpsc233projectgui.util to javafx.fxml;
    exports ca.ucalgary.cpsc233group.cpsc233projectgui.graphics;
    opens ca.ucalgary.cpsc233group.cpsc233projectgui.graphics to javafx.fxml;
}