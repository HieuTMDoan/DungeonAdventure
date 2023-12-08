package com.tcss.dungeonadventure.view;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Represents the Help GUI of the game that includes the game's instruction.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class HelpGUI {
    /**
     * The GUI handler.
     */
    private final GUIHandler myGUI;

    /**
     * The back button.
     */
    private Button myBackButton;

    /**
     * Initializes a basic help screen with the game's manual.
     */
    public HelpGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        locateNodes();
        attachEvents();
    }

    /**
     * Using a node ID, you can access nodes in the Help screen's FXML by ID.
     *
     * @return The looked-up node, or null if it isn't found.
     */
    private Node lookup() {
        return this.myGUI.lookup("helpBackButton");
    }

    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void locateNodes() {
        this.myBackButton = (Button) lookup();
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void attachEvents() {
        this.myBackButton.setOnAction(e -> {
            new PauseGUI(myGUI);
            GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.getPreviousLayout());
        });
    }
}
