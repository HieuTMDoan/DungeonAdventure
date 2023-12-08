package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import static com.tcss.dungeonadventure.model.Dungeon.MAZE_SIZE;


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
     * Displays the current map of the dungeon.
     */
    private void displayMap() {
        for (int row = 0; row < MAZE_SIZE.height; row++) {
            for (int col = 0; col < MAZE_SIZE.width; col++) {
                final HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setMaxSize(49, 49);

                final Text text = new Text(myCurrentDungeon.getRoomAt(col, row).toString());
                text.setBoundsType(TextBoundsType.VISUAL);
                text.setStyle("-fx-font-size: 5; " + "-fx-fill: white;");

                hbox.getChildren().add(text);
                myGridPane.add(hbox, row, col);
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PCS.valueOf(theEvent.getPropertyName()) == PCS.CHEAT_CODE) {
            displayMap();
        }
    }
}
