module com.tcss.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;


    exports com.tcss.dungeonadventure.view;
    opens com.tcss.dungeonadventure.view to javafx.fxml;
}