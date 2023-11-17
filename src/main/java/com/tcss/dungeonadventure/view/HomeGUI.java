package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
     * Using a node ID, you can access nodes in the FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void locateNodes() {
        this.myNewGameButton = (Button) lookup("newGameButton");
        this.myLoadGameButton = (Button) lookup("loadGameButton");
        this.myHelpButton = (Button) lookup("helpButton");
        this.myHeroNameTextField = (TextField) lookup("heroNameTextField");
    }

    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void attachEvents() {
        this.myNewGameButton.setOnAction(e -> myGUI.startNewGame(
                myHeroNameTextField.getText(),
                (Hero) SQLiteDB.getCharacterByName(mySelectedClass))
        );


        this.myLoadGameButton.setOnAction(e -> System.out.println("Load button pressed"));

        this.myHelpButton.setOnAction(e -> System.out.println("Help button pressed"));


        // Toggle groups make it so class selection is mutually exclusive
        final ToggleGroup classGroup = new ToggleGroup();
        final ToggleButton warriorRadioButton =
                (ToggleButton) lookup("warriorRadioButton");
        warriorRadioButton.setToggleGroup(classGroup);
        warriorRadioButton.setOnAction(e -> mySelectedClass = Helper.Characters.WARRIOR);
        warriorRadioButton.setSelected(true);


        final ToggleButton priestessRadioButton =
                (ToggleButton) lookup("priestessRadioButton");
        priestessRadioButton.setToggleGroup(classGroup);
        priestessRadioButton.setOnAction(e -> mySelectedClass = Helper.Characters.PRIESTESS);


        final ToggleButton thiefRadioButton =
                (ToggleButton) lookup("thiefRadioButton");
        thiefRadioButton.setToggleGroup(classGroup);
        thiefRadioButton.setOnAction(e -> mySelectedClass = Helper.Characters.THIEF);

    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {

            default -> {

            }
        }


    }
}