package com.tcss.dungeonadventure.view;

import javafx.scene.Node;


/**
 * This abstract class to make creating new GUI layouts easier.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class GUILayout {

    /**
     * The GUI Handler.
     */
    private final GUIHandler myGUI;

    /**
     * Constructs a new GUILayout.
     *
     * @param theGUIHandler The parent GUI Handler.
     */
    GUILayout(final GUIHandler theGUIHandler) {
        this.myGUI = theGUIHandler;
    }


    /**
     * Using a node ID, you can access nodes in the Adventuring screen's FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID);
    }

    /**
     * @return The GUI Handler.
     */
    GUIHandler getGui() {
        return this.myGUI;
    }




}
