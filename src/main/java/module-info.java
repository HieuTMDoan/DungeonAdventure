module com.tcss.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.tcss.dungeonadventure to javafx.fxml;
    exports com.tcss.dungeonadventure;
    exports com.tcss.dungeonadventure.controller;
    opens com.tcss.dungeonadventure.controller to javafx.fxml;
}