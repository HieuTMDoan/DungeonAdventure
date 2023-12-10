package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.tcss.dungeonadventure.objects.Directions;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
     * The current dungeon.
     */
    private final Dungeon myDungeon;


    /**
     * Initializes a basic dungeon screen with the game's current
     * discovered rooms and a back button to resume the game.
     */
    public DungeonGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        this.myDiscoveredRooms = DungeonAdventure.getInstance().getDiscoveredRooms();
        this.myDungeon = DungeonAdventure.getInstance().getDungeon();

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
            for (int col = 0; col < myDiscoveredRooms[row].length; col++) {
                final HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setMaxSize(45, 45);

                if (myDiscoveredRooms[row][col] != null) {
                    final Room currentDiscoveredRoom = myDiscoveredRooms[row][col];

                    // Adds and displays the room to the grid pane
                    // based on its internal information
                    if (currentDiscoveredRoom.equals(myDungeon.getCurrentRoom())) {
                        displayRoom(currentDiscoveredRoom, new Text("HERE"), row, col, hbox);
                    } else if (currentDiscoveredRoom.equals(myDungeon.getStartingRoom())) {
                        displayRoom(currentDiscoveredRoom, new Text("START"), row, col, hbox);
                    } else if (myDungeon.getRoomAt(row, col).isExitRoom()) {
                        displayRoom(currentDiscoveredRoom, new Text("EXIT"), row, col, hbox);
                    } else {
                        displayRoom(currentDiscoveredRoom, new Text("FOUND"), row, col, hbox);
                    }
                } else {
                    // Adds and displays a blank room if it's not discovered yet
                    myGridPane.add(hbox, row, col);
                }
            }
        }
    }

    /**
     * Displays the room with their doors and their status in the dungeon.
     *
     * @param theRoom     the room to be displayed
     * @param theText     the text to be displayed in the room
     * @param theRow      the row position of the box to be added to the {@link GridPane}
     * @param theColumn   the column position of the box to be added to the {@link GridPane}
     * @param theBox      the GUI component that displays the room
     */
    void displayRoom(final Room theRoom, final Text theText, final int theRow, final int theColumn, final HBox theBox) {
        final double[] borderWidths = {0, 0, 0, 0};
        int i = 0;

        for (Directions.Cardinal dir : Directions.Cardinal.values()) {
            if (theRoom.findDoorOnWall(dir) != null) {
                borderWidths[i++] = 1;
            } else {
                borderWidths[i++] = 0;
            }
        }

        theBox.setBorder(createBorder(borderWidths));
        theText.setBoundsType(TextBoundsType.VISUAL);
        theText.setStyle("-fx-font-size: 10; " + "-fx-fill: white;");
        theBox.getChildren().add(theText);
        myGridPane.add(theBox, theRow, theColumn);
    }

    /**
     * Creates and returns a colored border with the given border widths.
     *
     * @param theBorderWidths the 4 widths of the border
     * @return Returns a colored border with the given border widths.
     */
    Border createBorder(final double[] theBorderWidths) {
        final BorderStroke borderStroke = new BorderStroke(
                Color.rgb(255, 127, 80),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(
                        theBorderWidths[0],
                        theBorderWidths[1],
                        theBorderWidths[2],
                        theBorderWidths[3]
                )
        );

        return new Border(borderStroke);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PCS.valueOf(theEvent.getPropertyName()) == PCS.ROOMS_DISCOVERED) {
            updateMap();
        }
    }
}
