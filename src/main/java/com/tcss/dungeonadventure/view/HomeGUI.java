package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.DungeonAdventureMemento;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Represents the GUI of the home screen
 * before starting a new or saved game.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class HomeGUI implements PropertyChangeListener {

    /**
     * The GUI handler.
     */
    private final GUIHandler myGUI;

    /**
     * The currently selected class. Set to warrior by default.
     */
    private Helper.Characters mySelectedClass = Helper.Characters.WARRIOR;

    /**
     * The new game button.
     */
    private Button myNewGameButton;

    /**
     * The load game button.
     */
    private Button myLoadGameButton;

    /**
     * The help button.
     */
    private Button myHelpButton;

    /**
     * The text input for hero name.
     */
    private TextField myHeroNameTextField;

    public HomeGUI(final GUIHandler theGUI) {
        this.myGUI = theGUI;
        PCS.addPropertyListener(this);

        locateNodes();
        attachEvents();

    }

    /**
     * Using a node ID, you can access nodes in the Home screen's FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID);
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void locateNodes() {
        this.myNewGameButton = (Button) lookup("homeNewGameButton");
        this.myLoadGameButton = (Button) lookup("homeLoadGameButton");
        this.myHelpButton = (Button) lookup("homeHelpButton");
        this.myHeroNameTextField = (TextField) lookup("homeHeroNameTextField");
    }

    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void attachEvents() {
        this.myNewGameButton.setOnAction(e -> myGUI.startNewGame(
                getHeroName(myHeroNameTextField),
                (Hero) SQLiteDB.getCharacterByName(mySelectedClass))
        );

        this.myLoadGameButton.setOnAction(e -> {
            try {
                loadGameFromFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.myHelpButton.setOnAction(e -> {
            new HelpGUI(myGUI);
            GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HELP);
        });

        // Toggle groups make it so class selection is mutually exclusive
        final ToggleGroup classGroup = new ToggleGroup();
        final ToggleButton warriorRadioButton =
                (ToggleButton) lookup("homeWarriorRadioButton");
        warriorRadioButton.setToggleGroup(classGroup);
        warriorRadioButton.setOnAction(e -> mySelectedClass = Helper.Characters.WARRIOR);
        warriorRadioButton.setSelected(true);

        final ToggleButton priestessRadioButton =
                (ToggleButton) lookup("homePriestessRadioButton");
        priestessRadioButton.setToggleGroup(classGroup);
        priestessRadioButton.setOnAction(e -> mySelectedClass = Helper.Characters.PRIESTESS);

        final ToggleButton thiefRadioButton =
                (ToggleButton) lookup("homeThiefRadioButton");
        thiefRadioButton.setToggleGroup(classGroup);
        thiefRadioButton.setOnAction(e -> mySelectedClass = Helper.Characters.THIEF);
    }

    /**
     * Returns the hero name inputted in the text field as-is,
     * or "nameless" if there's no input.
     *
     * @param theTextField the text field containing the hero name
     * @return Hero name.
     */
    private String getHeroName(final TextField theTextField) {
        final String heroName = theTextField.getText();
        return heroName.isEmpty() ? "nameless" : heroName;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

    }

    private void loadGameFromFile() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("savedGame.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            // Deserialize game state from the file
            final Object loadedObject = objectInputStream.readObject();

            // Check if the loaded object is an instance of DungeonAdventureMemento
            if (loadedObject instanceof DungeonAdventureMemento loadedMemento) {

                // Create a new instance of DungeonAdventure and restore from the loaded memento
                final DungeonAdventure loadedGame = DungeonAdventure.getInstance();
                loadedGame.restoreFromMemento(loadedMemento);

                System.out.println("Game loaded successfully!");

                // Resume the game or update the GUI accordingly
                myGUI.resumeGame();
            } else {
                throw new IOException("Invalid saved game file!");
            }

        } catch (final FileNotFoundException ex) {
            // file not found
            throw new FileNotFoundException("Saved game file not found!");
        } catch (final IOException | ClassNotFoundException ex) {
            // I/O exceptions or loaded class not found
            throw new IOException("Error reading saved game file: " + ex.getMessage());
        }
    }
}


