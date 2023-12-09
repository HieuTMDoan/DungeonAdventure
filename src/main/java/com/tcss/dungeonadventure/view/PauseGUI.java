package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.DungeonAdventureMemento;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("savedGame.ser"))) {

            // Serialize the game state (DungeonAdventureMemento instance) to the file
            objectOutputStream.writeObject(DungeonAdventure.getInstance().createMemento());

            System.out.println("Game saved successfully!");

        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Load the game state from a serialized file.
     */
    private void loadGame() {
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream("savedGame.ser"))) {

            // Deserialize the game state from the file
            final Object loadedObject = objectInputStream.readObject();

            // Check if the loaded object is an instance of DungeonAdventureMemento
            if (loadedObject instanceof final DungeonAdventureMemento loadedMemento) {

                // Restore the game state from the loaded memento
                DungeonAdventure.getInstance().restoreFromMemento(loadedMemento);

                System.out.println("Game loaded successfully!");

                // Resume the game or update the GUI accordingly
                myGUI.resumeGame();
            } else {
                System.out.println("Invalid saved game file!");
            }

        } catch (final FileNotFoundException ex) {
            System.out.println("Saved game file not found!");
            ex.printStackTrace();
        } catch (final IOException ex) {
            System.out.println("Error reading saved game file!");
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            System.out.println("Class not found during deserialization!");
            ex.printStackTrace();
        }
    }



    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void attachEvents() {
        this.myResumeButton.setOnAction(e -> myGUI.resumeGame());

        this.mySaveGameButton.setOnAction(e -> saveGame());

        // Load the game when the "Load Game" button is clicked
        this.myHelpButton.setOnAction(e -> {
            loadGame();
            new HelpGUI(myGUI);
            GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HELP);
        });
    }
}
