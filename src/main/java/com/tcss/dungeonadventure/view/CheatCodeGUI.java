package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;
import static com.tcss.dungeonadventure.model.Dungeon.MAZE_SIZE;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

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
        for (int row = 0; row < MAZE_SIZE.height; row++) {
            for (int col = 0; col < MAZE_SIZE.width; col++) {
                final HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setMaxSize(45, 45);

                final Room currentRoom = myCurrentDungeon.getRoomAt(row, col);
                if (currentRoom.equals(myCurrentDungeon.getCurrentRoom())) {
                    displayRoom(currentRoom, new Text("HERE"), row, col, hbox);
                } else if (currentRoom.equals(myCurrentDungeon.getStartingRoom())) {
                    displayRoom(currentRoom, new Text("START"), row, col, hbox);
                } else if (currentRoom.isExitRoom()) {
                    displayRoom(currentRoom, new Text("EXIT"), row, col, hbox);
                } else if (currentRoom.getPillar() != null) {
                    final String pillarCharacter =
                            String.valueOf(currentRoom.getPillar().getDisplayChar());
                    displayRoom(currentRoom, new Text(pillarCharacter), row, col, hbox);
                } else {
                    displayRoom(currentRoom, new Text(""), row, col, hbox);
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
    void displayRoom(final Room theRoom,
                     final Text theText,
                     final int theRow,
                     final int theColumn,
                     final HBox theBox) {
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
        myGridPane.add(theBox, theColumn, theRow);
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
