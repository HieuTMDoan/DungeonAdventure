package com.tcss.dungeonadventure.view;

import static com.tcss.dungeonadventure.model.Dungeon.MAZE_SIZE;
import static com.tcss.dungeonadventure.objects.Directions.Cardinal;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
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
 * Represents the GUI of the current dungeon
 * that which the entirety of it is visible.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class CheatCodeGUI extends GUILayout implements PropertyChangeListener {

    /**
     * The back button.
     */
    private Button myBackButton;

    /**
     * The grid pane that is the layout
     * of the current dungeon that which the entirety of it is visible.
     */
    private GridPane myGridPane;

    /**
     * The current dungeon that which the entirety of it is visible.
     */
    private final Dungeon myCurrentDungeon;


    /**
     * Initializes a basic dungeon screen with the game's current
     * discovered rooms and a back button to resume the game.
     */
    public CheatCodeGUI(final GUIHandler theGUI) {
        super(theGUI);
        this.myCurrentDungeon = DungeonAdventure.getInstance().getDungeon();

        PCS.addPropertyListener(this);
        locateNodes();
        this.myBackButton.setOnAction(e -> getGui().resumeGame());
        displayMap();
    }


    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void locateNodes() {
        myBackButton = (Button) lookup("cheatBackButton");
        myGridPane = (GridPane) lookup("cheatGridPane");
    }

    /**
     * Displays the current visible map of the dungeon.
     */
    private void displayMap() {
        // Initializes lists of pre-defined rooms to victory
        // and directions to victory
        final List<Room> roomsToVictory = getRoomsToVictory();
        final List<Cardinal> pathToVictory = myCurrentDungeon.getPath();


        // Marks entrance, exit, pillar, and current rooms.
        // Marks direction to victory if available
        for (int row = 0; row < MAZE_SIZE.height; row++) {
            for (int col = 0; col < MAZE_SIZE.width; col++) {
                final Room currentRoom = myCurrentDungeon.getRoomAt(row, col);

                final VBox vBox = new VBox();  // Create a new VBox for each room
                vBox.setAlignment(Pos.CENTER);
                vBox.setMaxSize(45, 45);
                vBox.setSpacing(2);

                // Marks direction in the room with an arrow if available
                if (roomsToVictory.contains(currentRoom)) {
                    final int pathIndex = roomsToVictory.indexOf(currentRoom);

                    final Text directionCharacter = switch (pathToVictory.get(pathIndex)) {
                        case NORTH ->  new Text("↑");
                        case EAST -> new Text("→");
                        case SOUTH -> new Text("↓");
                        case WEST -> new Text("←");
                    };

                    directionCharacter.setBoundsType(TextBoundsType.VISUAL);
                    directionCharacter.setStyle("-fx-font-size: 15; "
                            + "-fx-fill: rgb(255,127,80);");
                    vBox.getChildren().add(directionCharacter);
                }

                // Marks entrance, exit, pillar, current rooms,
                // and all other non-essential rooms.
                if (currentRoom.equals(myCurrentDungeon.getCurrentRoom())) {
                    displayRoom(currentRoom, new Text("YOU"), row, col, vBox);

                } else if (currentRoom.equals(myCurrentDungeon.getStartingRoom())) {
                    displayRoom(currentRoom, new Text("START"), row, col, vBox);

                } else if (currentRoom.isExitRoom()) {
                    displayRoom(currentRoom, new Text("EXIT"), row, col, vBox);

                } else if (currentRoom.getPillar() != null) {
                    final String pillarCharacter =
                            String.valueOf(currentRoom.getPillar().getDisplayChar());
                    displayRoom(currentRoom, new Text(pillarCharacter), row, col, vBox);

                } else {
                    displayRoom(currentRoom, row, col, vBox);
                }
            }
        }
    }

    /**
     * Returns a list of rooms that leads to victory.
     *
     * @return The list of rooms that leads to victory.
     */
    private List<Room> getRoomsToVictory() {
        final List<Room> roomsToVictory = new ArrayList<>();
        final List<Cardinal> pathToVictory = myCurrentDungeon.getPath();
        Room currentRoomToVictory = myCurrentDungeon.getStartingRoom();

        for (Cardinal cardinal : pathToVictory) {
            roomsToVictory.add(currentRoomToVictory);
            currentRoomToVictory =
                    currentRoomToVictory.getAdjacentRoomByDirection(cardinal);
        }

        return roomsToVictory;
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

        for (Cardinal dir : Cardinal.values()) {
            if (theRoom.findDoorOnWall(dir) != null) {
                borderWidths[i++] = 0;
            } else {
                borderWidths[i++] = 1;
            }
        }
        theBox.setBorder(createBorder(borderWidths));

        myGridPane.add(theBox, theColumn, theRow);
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
    void displayRoom(final Room theRoom,
                     final Text theText,
                     final int theRow,
                     final int theColumn,
                     final VBox theBox) {
        final double[] borderWidths = {0, 0, 0, 0};
        int i = 0;

        for (Cardinal dir : Cardinal.values()) {
            if (theRoom.findDoorOnWall(dir) != null) {
                borderWidths[i++] = 0;
            } else {
                borderWidths[i++] = 1;
            }
        }
        theBox.setBorder(createBorder(borderWidths));

        theText.setBoundsType(TextBoundsType.VISUAL);
        theText.setStyle("-fx-font-size: 15; -fx-fill: white; -fx-font-weight: bold;");
        theBox.getChildren().add(theText);

        myGridPane.add(theBox, theColumn, theRow);
    }

    /**
     * Creates and returns colored borders with the given border widths.
     * The borders that represent non-door walls in a room are colored and vice versa.
     *
     * @param theBorderWidths the 4 widths of the border
     * @return Returns colored borders with the given border widths.
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
        if (PCS.valueOf(theEvent.getPropertyName()) == PCS.CHEAT_CODE) {
            displayMap();
        }
    }

}
