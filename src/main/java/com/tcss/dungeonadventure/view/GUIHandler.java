package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Main;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.monsters.Monster;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Represents the GUI manager for the program.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class GUIHandler extends Application implements PropertyChangeListener {
    /**
     * The title of the window.
     */
    private static final String WINDOW_TITLE = "Dungeon Adventure";

    /**
     * The dimensions of the window.
     */
    private static final Dimension WINDOW_DIMENSION =
            new Dimension(900, 600);

    /**
     * The path for the FXML file for the home screen.
     */
    private static final String HOME_FXML_PATH = "fxml/dungeon-home-screen.fxml";

    /**
     * The current scene.
     */
    private Scene myScene;

    /**
     * The combat GUI.
     */
    private CombatGUI myCombatGui;

    @Override
    public void start(final Stage theStage) throws IOException {
        PCS.addPropertyListener(this);

        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(HOME_FXML_PATH));

        this.myScene = new Scene(fxmlLoader.load(),
                WINDOW_DIMENSION.getWidth(),
                WINDOW_DIMENSION.getHeight());

        this.myScene.setOnKeyPressed(this::handleKeyPress);

        theStage.setTitle(WINDOW_TITLE);
        theStage.setResizable(false);
        theStage.setScene(this.myScene);
        theStage.show();

        setLayoutNodes();
        myCombatGui = new CombatGUI(this);

        Layouts.swapLayout(Layouts.HOME);
        new HomeGUI(this);

    }

    /**
     * Set all layout nodes.
     */
    private void setLayoutNodes() {
        Layouts.HOME.setNode(lookup("homePane"));
        Layouts.ADVENTURING.setNode(lookup("adventuringPane"));
        Layouts.MENU.setNode(lookup("pausePane"));
        Layouts.HELP.setNode(lookup("helpPane"));
        Layouts.COMBAT.setNode(lookup("combatPane"));
        Layouts.END.setNode(lookup("endPane"));
        Layouts.DUNGEON.setNode(lookup("dungeonPane"));
        Layouts.CHEAT_CODE.setNode(lookup("cheatPane"));
        Layouts.END_SCREEN.setNode(lookup("endPane"));
    }

    /**
     * Starts a new game.
     *
     * @param thePlayerName The player name for the new game
     * @param theHero       The chosen hero character for the new game
     */
    public void startNewGame(final String thePlayerName, final Hero theHero) {
        DungeonAdventure.getInstance().startNewGame(thePlayerName, theHero);
        new AdventuringGUI(this);
        Layouts.swapLayout(Layouts.ADVENTURING);
    }

    /**
     * Resumes the current game.
     */
    public void resumeGame() {
        Layouts.swapLayout(Layouts.ADVENTURING);
    }

    /**
     * Saves the current game.
     */
    public void saveGame() {
        DungeonAdventure.getInstance().saveGameState();
    }

    /**
     * Loads the most recent saved game.
     */
    public void loadGame() {
        DungeonAdventure.getInstance().loadGameState();
    }

    /**
     * Handles a key press event.
     *
     * @param theEvent the key press event
     */
    private void handleKeyPress(final KeyEvent theEvent) {
        if (Layouts.getCurrentLayout() != Layouts.ADVENTURING) {
            return;
        }

        switch (theEvent.getCode()) {
            case UP, W -> DungeonAdventure.getInstance().
                    movePlayer(Directions.Cardinal.NORTH);

            case DOWN, S -> DungeonAdventure.getInstance().
                    movePlayer(Directions.Cardinal.SOUTH);

            case LEFT, A -> DungeonAdventure.getInstance().
                    movePlayer(Directions.Cardinal.WEST);

            case RIGHT, D -> DungeonAdventure.getInstance().
                    movePlayer(Directions.Cardinal.EAST);

            case P, ESCAPE -> {
                new PauseGUI(this);
                Layouts.swapLayout(Layouts.MENU);
            }

            case M -> {
                new DungeonGUI(this);
                Layouts.swapLayout(Layouts.DUNGEON);
            }

            case PERIOD -> {
                new CheatCodeGUI(this);
                Layouts.swapLayout(Layouts.CHEAT_CODE);
            }

            default -> {
            }
        }
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

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case BEGIN_COMBAT -> {
                myCombatGui.startCombat((Monster) theEvent.getNewValue());
                Layouts.swapLayout(Layouts.COMBAT);
            }
            case GAME_END -> {
                new EndGameGUI(this).show((boolean) theEvent.getNewValue());
                Layouts.swapLayout(Layouts.END_SCREEN);
            }
            default -> {
            }
        }
    }


    public enum Layouts {
        /**
         * A layout enum for the home screen.
         * Its corresponding layout node should be the
         * root pane of the home screen.
         */
        HOME,

        /**
         * A layout enum for the adventuring screen.
         * Its corresponding layout node should be the
         * root pane of the adventuring screen.
         */
        ADVENTURING,

        /**
         * A layout enum for the pause screen.
         * Its corresponding layout node should be the
         * root pane of the pause screen.
         */
        MENU,

        /**
         * A layout enum for the help screen.
         * Its corresponding layout node should be the
         * root pane of the help screen.
         */
        HELP,

        /**
         * A layout enum for the combat screen.
         * Its corresponding layout node should be the
         * root pane of the combat screen.
         */
        COMBAT,

        /**
         * A layout enum for the end game screen.
         * Its corresponding layout node should be the
         * root pane of the end game screen.
         */
        END,

        /**
         * A layout enum for the dungeon screen.
         * Its corresponding layout node should be the
         * root pane of the dungeon screen.
         */
        DUNGEON,

        /**
         * A layout enum for the end screen.
         * Its corresponding layout node should be the
         * root pane of the end screen.
         */
        END_SCREEN,

        /**
         * A layout enum for the cheat code screen.
         * Its corresponding layout node should be the
         * root pane of the cheat code screen.
         */
        CHEAT_CODE;

        /**
         * The current layout.
         */
        private static Layouts CURRENT_LAYOUT;

        /**
         * The previous layout.
         */
        private static Layouts PREVIOUS_LAYOUT;

        /**
         * The layout node stored within each enum.
         */
        private Node myLayoutNode;

        /**
         * Swaps between layouts.
         *
         * @param theLayout The layout to swap to.
         */
        static void swapLayout(final Layouts theLayout) {
            PREVIOUS_LAYOUT = CURRENT_LAYOUT;

            for (final Layouts layout : Layouts.values()) {
                layout.getNode().setVisible(false);
            }

            theLayout.getNode().setVisible(true);
            CURRENT_LAYOUT = theLayout;
        }

        /**
         * @return The current layout.
         */
        private static Layouts getCurrentLayout() {
            return CURRENT_LAYOUT;
        }

        /**
         * @return The previous layout.
         */
        static Layouts getPreviousLayout() {
            return PREVIOUS_LAYOUT;
        }

        /**
         * @return The node corresponding to the Layout enum.
         */
        private Node getNode() {
            return myLayoutNode;
        }

        /**
         * Binds a node to a Layout enum.
         *
         * @param theNode The node to bind to.
         */
        private void setNode(final Node theNode) {
            if (theNode == null) {
                throw new IllegalArgumentException(
                        "Node argument for " + getClass().getSimpleName() + " was null.");
            }
            myLayoutNode = theNode;
        }

    }
}
