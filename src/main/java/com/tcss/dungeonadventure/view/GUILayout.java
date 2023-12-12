package com.tcss.dungeonadventure.view;

import javafx.scene.Node;

public abstract class GUILayout {


    private final GUIHandler myGUI;

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



    GUIHandler getGui() {
        return this.myGUI;
    }







}
