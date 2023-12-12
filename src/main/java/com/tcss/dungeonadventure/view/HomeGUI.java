package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.TimedSequence;
import com.tcss.dungeonadventure.model.factories.HeroFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javafx.scene.Node;
import javafx.scene.control.*;


/**
 * Represents the GUI of the home screen
 * before starting a new or saved game.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class HomeGUI extends GUILayout implements PropertyChangeListener {


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


    /**
     * The load label;
     */
    private Label myLoadLabel;

    public HomeGUI(final GUIHandler theGUI) {
        super(theGUI);
        PCS.addPropertyListener(this);

        locateNodes();
        attachEvents();




    }


    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void locateNodes() {
        this.myNewGameButton = (Button) lookup("homeNewGameButton");
        this.myLoadGameButton = (Button) lookup("homeLoadGameButton");
        this.myHelpButton = (Button) lookup("homeHelpButton");
        this.myHeroNameTextField = (TextField) lookup("homeHeroNameTextField");
        this.myLoadLabel = (Label) lookup("homeLoadStatusLabel");
    }

    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void attachEvents() {
        this.myNewGameButton.setOnAction(e -> {
            // Start a new game and save the state to a file
            final String name = myHeroNameTextField.getText();
            getGui().startNewGame(name.isEmpty() ? "nameless" : name,
                    HeroFactory.createCharacter(mySelectedClass));
        });

        this.myLoadGameButton.setOnAction(e -> {
            if (!new File(DungeonAdventure.SAVE_NAME).exists()) {
                new TimedSequence().afterDo(0, () -> {
                    myLoadLabel.setVisible(true);
                    return true;
                }).afterDo(1, () -> {
                    myLoadLabel.setVisible(false);
                    return true;
                }).start();


                return;
            }

            if (DungeonAdventure.getInstance().loadGameState()) {
                getGui().loadGame();
            }
        });

        this.myHelpButton.setOnAction(e -> {
            new HelpGUI(getGui());
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


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

    }

}
