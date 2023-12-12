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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
public class DungeonGUI extends GUILayout implements PropertyChangeListener {

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
        super(theGUI);
        this.myDiscoveredRooms = DungeonAdventure.getInstance().getDiscoveredRooms();
        this.myDungeon = DungeonAdventure.getInstance().getDungeon();

        PCS.addPropertyListener(this);

        locateNodes();
        this.myBackButton.setOnAction(e -> getGui().resumeGame());
        updateMap();
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
                final VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                vBox.setMaxSize(45, 45);

                if (myDiscoveredRooms[row][col] != null) {
                    final Room currentDiscoveredRoom = myDiscoveredRooms[row][col];

                    // Adds and displays the room to the grid pane
                    // based on its internal information
                    if (currentDiscoveredRoom.equals(myDungeon.getCurrentRoom())) {
                        displayRoom(currentDiscoveredRoom, new Text("YOU"), row, col, vBox);
                    } else if (currentDiscoveredRoom.equals(myDungeon.getStartingRoom())) {
                        displayRoom(currentDiscoveredRoom, new Text("START"), row, col, vBox);
                    } else if (myDungeon.getRoomAt(row, col).isExitRoom()) {
                        displayRoom(currentDiscoveredRoom, new Text("EXIT"), row, col, vBox);
                    } else {
                        displayRoom(currentDiscoveredRoom, row, col, vBox);
                    }
                } else {
                    // Adds and displays a blank room if it's not discovered yet
                    myGridPane.add(vBox, row, col);
                }
            }
        }
    }

    /**
     * Displays the room with only their doors in the dungeon.
     *
     * @param theRoom     the room to be displayed
     * @param theRow      the row position of the box to be added to the {@link GridPane}
     * @param theColumn   the column position of the box to be added to the {@link GridPane}
     * @param theBox      the GUI component that displays the room
     */
    private void displayRoom(final Room theRoom,
                             final int theRow,
                             final int theColumn,
                             final VBox theBox) {
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

        myGridPane.add(theBox, theRow, theColumn);
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
    void displayRoom(
            final Room theRoom,
            final Text theText,
            final int theRow,
            final int theColumn,
            final VBox theBox) {
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
        theText.setStyle("-fx-font-size: 10; -fx-fill: white;");
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
