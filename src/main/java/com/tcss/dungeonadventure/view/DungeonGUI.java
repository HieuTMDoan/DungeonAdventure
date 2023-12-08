package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
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
     * of the currently discovered rooms in the dungeon.
     */
    private GridPane myGridPane;

    /**
     * The 2D array mimicking the dungeon's underlying
     * structure that contains the discovered {@link Room}s.
     */
    private final Room[][] myDiscoveredRooms;

    /**
     * Initializes a basic dungeon screen with the game's current
     * discovered rooms and a back button to resume the game.
     */
    public DungeonGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        this.myDiscoveredRooms = DungeonAdventure.getInstance().getDiscoveredRooms();

        PCS.addPropertyListener(this);

        locateNodes();
        this.myBackButton.setOnAction(e -> myGUI.resumeGame());
        updateMap();
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
     * Updates the current map of the discovered dungeon.
     */
    private void updateMap() {
        for (int row = 0; row < myDiscoveredRooms.length; row++) {
            for (int col = 0; col < myDiscoveredRooms[0].length; col++) {
                final HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setMaxSize(49, 49);

                if (myDiscoveredRooms[row][col] != null) {
                    final Text text = new Text("discovered");
                    text.setBoundsType(TextBoundsType.VISUAL);
                    text.setStyle("-fx-font-size: 5; " + "-fx-fill: white;");
                    hbox.getChildren().add(text);
                    myGridPane.add(hbox, row, col);
                } else {
                    myGridPane.add(hbox, row, col);
                }
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PCS.valueOf(theEvent.getPropertyName()) == PCS.ROOMS_DISCOVERED) {
            updateMap();
        }
    }
}
