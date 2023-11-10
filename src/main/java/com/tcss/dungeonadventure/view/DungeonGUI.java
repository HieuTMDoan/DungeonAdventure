package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.SQLiteDB;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DungeonGUI extends Application implements PropertyChangeListener {

    /**
     * The width of the window.
     */
    private static final double WINDOW_WIDTH = 900;

    /**
     * The height of the window.
     */
    private static final double WINDOW_HEIGHT = 600;

    /**
     * The path for the FXML file for the home screen.
     */
    private static final String HOME_FXML_PATH =
            "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-home-screen.fxml";

    /**
     * The title of the window.
     */
    private static final String WINDOW_TITLE = "Dungeon Adventure";

    /**
     * The current scene.
     */
    private Scene myScene;

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

    @Override
    public void start(final Stage theStage) throws IOException {
        PCS.addPropertyListener(this);

        final FXMLLoader fxmlLoader = new FXMLLoader(
                new File(HOME_FXML_PATH).toURI().toURL());

        myScene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        theStage.setTitle(WINDOW_TITLE);
        theStage.setScene(myScene);
        theStage.show();

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
        return this.myScene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
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
        this.myNewGameButton.setOnAction(e -> {
            try {
                new AdventuringGUI(myScene);
            } catch (final IOException exception) {
                System.err.println("Error loading the adventuring GUI: ");
                exception.printStackTrace();
            }

            PCS.firePropertyChanged(PCS.START_NEW_GAME,
                    new Object[] {myHeroNameTextField.getText(), SQLiteDB.getCharacterByName(mySelectedClass)});
        });



        this.myLoadGameButton.setOnAction(e -> {
            System.out.println("Load button pressed");
        });

        this.myHelpButton.setOnAction(e -> {
            System.out.println("Help button pressed");
        });


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
            case START_NEW_GAME -> {


            }


        }


    }
}