package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;


/**
 * Represents the GUI of the current discovered rooms
 * in the dungeon.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class DungeonGUI implements PropertyChangeListener {
    /**
     * The GUI handler.
     */
    private final GUIHandler myGUI;

    /**
     * The back button.
     */
    private Button myBackButton;

    /**
     * The grid pane that is the layout
     * of the currently discovered dungeon.
     */
    private GridPane myGridPane;

    /**
     * Initializes a basic help screen with the game's manual.
     */
    public DungeonGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;

        PCS.addPropertyListener(this);
        locateNodes();
        this.myBackButton.setOnAction(e -> myGUI.resumeGame());

        final Room entrance = DungeonAdventure.getInstance().getDungeon().getStartingRoom();
        createMap(entrance);
    }

    /**
     * Using a node ID, you can access nodes in the Help screen's FXML by ID.
     *
     * @return The looked-up node, or null if it isn't found.
     */
    private Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID);
    }

    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void locateNodes() {
        myBackButton = (Button) lookup("dungeonBackButton");
        myGridPane = (GridPane) lookup("dungeonGridPane");
    }

    /**
     * Creates the initial dungeon map GUI with the starting room.
     *
     * @param theStartingRoom the starting room.
     */
    private void createMap(final Room theStartingRoom) {
        final Point entrancePos = theStartingRoom.getDungeonLocation();

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        final Text text = new Text();
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-font-size: 5; " + "-fx-fill: white;");

        hbox.getChildren().add(text);
        myGridPane.add(hbox, entrancePos.y, entrancePos.x);
    }

    /**
     * Updates the current map of the discovered dungeon.
     */
    private void updateMap() {

    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PCS.valueOf(theEvent.getPropertyName()) == PCS.ROOMS_DISCOVERED) {
            updateMap();
        }
    }
}
