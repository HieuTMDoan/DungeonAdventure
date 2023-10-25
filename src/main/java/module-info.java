module com.tcss.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.tcss.dungeonadventure to javafx.fxml;
    exports com.tcss.dungeonadventure;
}