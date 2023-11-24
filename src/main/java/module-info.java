module com.tcss.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;

    exports com.tcss.dungeonadventure.objects;
    exports com.tcss.dungeonadventure.objects.heroes;
    exports com.tcss.dungeonadventure.view;
    opens com.tcss.dungeonadventure.view to javafx.fxml;
}