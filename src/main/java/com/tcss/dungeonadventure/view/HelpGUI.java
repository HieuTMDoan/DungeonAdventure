package com.tcss.dungeonadventure.view;

import javafx.scene.control.Button;

/**
 * Represents the Help GUI of the game that includes the game's instruction.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class HelpGUI extends GUILayout {

    /**
     * The back button.
     */
    private Button myBackButton;

    /**
     * Initializes a basic help screen with the game's manual.
     */
    public HelpGUI(final GUIHandler theGUI) {
        super(theGUI);
        locateNodes();
        attachEvents();
    }


    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void locateNodes() {
        this.myBackButton = (Button) lookup("");
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void attachEvents() {
        this.myBackButton.setOnAction(e -> {
            if (GUIHandler.Layouts.getPreviousLayout() == GUIHandler.Layouts.PAUSE) {
                new PauseGUI(getGui());
            }

            GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.getPreviousLayout());
        });
    }
}
