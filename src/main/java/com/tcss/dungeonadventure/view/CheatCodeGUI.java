package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
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
import javafx.util.Pair;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static com.tcss.dungeonadventure.model.Dungeon.MAZE_SIZE;
import static com.tcss.dungeonadventure.objects.Directions.Cardinal;

/**
 * Represents the GUI of the current dungeon
 * that which the entirety of it is visible.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class CheatCodeGUI implements PropertyChangeListener {
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
        this.myGUI = theGUI;
        this.myCurrentDungeon = DungeonAdventure.getInstance().getDungeon();

        PCS.addPropertyListener(this);
        locateNodes();
        this.myBackButton.setOnAction(e -> myGUI.resumeGame());
        displayMap();
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
        myBackButton = (Button) lookup("cheatBackButton");
        myGridPane = (GridPane) lookup("cheatGridPane");
    }

    /**
     * Displays the current visible map of the dungeon.
     */
    private void displayMap() {
        Room nextRoomToVictory = myCurrentDungeon.getStartingRoom();
        int pathIndex = 0;
        Pair<VBox, Room> vBoxRoomPair;

        for (int row = 0; row < MAZE_SIZE.height; row++) {
            for (int col = 0; col < MAZE_SIZE.width; col++) {
                final Room currentRoom = myCurrentDungeon.getRoomAt(row, col);

                VBox vBox = new VBox();  // Create a new VBox for each room
                vBox.setAlignment(Pos.CENTER);
                vBox.setMaxSize(45, 45);

                if (currentRoom.equals(nextRoomToVictory)
                        && !currentRoom.isExitRoom()) {
                    vBoxRoomPair =
                            getBoxWithDirectionAndNewRoomToVictory(pathIndex++, vBox, currentRoom);
                    vBox = vBoxRoomPair.getKey();
                    nextRoomToVictory = vBoxRoomPair.getValue();
                }

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
                borderWidths[i++] = 1;
            } else {
                borderWidths[i++] = 0;
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
                borderWidths[i++] = 1;
            } else {
                borderWidths[i++] = 0;
            }
        }
        theBox.setBorder(createBorder(borderWidths));

        theText.setBoundsType(TextBoundsType.VISUAL);
        theText.setStyle("-fx-font-size: 10; " + "-fx-fill: white;");
        theBox.getChildren().add(theText);

        myGridPane.add(theBox, theColumn, theRow);
    }

    /**
     * Adds a direction character to the room's GUI component,
     * updates to the next room that the path to victory is pointing to,
     * and returns both.
     *
     * @param thePathIndex the current index of the path to victory's list
     * @param theBox the GUI component of the room
     * @param theNextRoom the next room to victory to be updated
     * @return The room's GUI component with a direction character,
     * and the next room to victory.
     */
    private Pair<VBox, Room> getBoxWithDirectionAndNewRoomToVictory(final int thePathIndex,
                                                                    final VBox theBox,
                                                                    final Room theNextRoom) {
        final List<Cardinal> path = myCurrentDungeon.getPath();
        final Text directionCharacter = switch (path.get(thePathIndex)) {
            case NORTH ->  new Text("↑");
            case EAST -> new Text("→");
            case SOUTH -> new Text("↓");
            case WEST -> new Text("←");
        };

        directionCharacter.setBoundsType(TextBoundsType.VISUAL);
        directionCharacter.setStyle("-fx-font-size: 15; " + "-fx-fill: rgb(255,127,80);");
        theBox.getChildren().add(directionCharacter);

        final Room nextRoomByDirection =
                theNextRoom.getAdjacentRoomByDirection(path.get(thePathIndex));

        return new Pair<>(theBox, nextRoomByDirection);
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
        if (PCS.valueOf(theEvent.getPropertyName()) == PCS.CHEAT_CODE) {
            displayMap();
        }
    }
}
