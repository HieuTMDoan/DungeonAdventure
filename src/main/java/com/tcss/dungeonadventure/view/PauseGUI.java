package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Represents the pause menu GUI of the program.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class PauseGUI {
    /**
     * The GUI handler.
     */
    private final GUIHandler myGUI;

    /**
     * The resume button.
     */
    private Button myResumeButton;

    /**
     * The save game button.
     */
    private Button mySaveGameButton;

    /**
     * The help button.
     */
    private Button myHelpButton;

    /**
     * Initializes a basic pause menu screen with 3 menu options.
     */
    public PauseGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        locateNodes();
        attachEvents();
    }

    /**
     * Using a node ID, you can access nodes in the Pause screen's FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    private Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID);
    }

    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void locateNodes() {
        this.myResumeButton = (Button) lookup("menuResumeButton");
        this.mySaveGameButton = (Button) lookup("menuSaveButton");
        this.myHelpButton = (Button) lookup("menuHelpButton");
    }

    /**
     * Save the game state to a file using serialization.
     */
    private void saveGame() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("savedGame.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            // Serialize the game state (DungeonAdventure instance) to the file
            objectOutputStream.writeObject(DungeonAdventure.getInstance());

            // Display a message or perform any other necessary actions after saving
            System.out.println("Game saved successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void attachEvents() {
        this.myResumeButton.setOnAction(e -> myGUI.resumeGame());

        this.mySaveGameButton.setOnAction(e -> saveGame());

        this.myHelpButton.setOnAction(e -> {
            new HelpGUI(myGUI);
            GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HELP);
        });
    }
}
