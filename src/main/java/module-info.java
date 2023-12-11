module com.tcss.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires junit;

    exports com.tcss.dungeonadventure.model;
    exports com.tcss.dungeonadventure.model.factories;
    exports com.tcss.dungeonadventure.objects;
    exports com.tcss.dungeonadventure.objects.heroes;
    exports com.tcss.dungeonadventure.objects.items;
    exports com.tcss.dungeonadventure.objects.skills;
    exports com.tcss.dungeonadventure.objects.monsters;
    exports com.tcss.dungeonadventure.objects.tiles;
    exports com.tcss.dungeonadventure.view;
    exports com.tcss.dungeonadventure;

    opens com.tcss.dungeonadventure.view to javafx.fxml;
    exports com.tcss.dungeonadventure.model.memento;
}