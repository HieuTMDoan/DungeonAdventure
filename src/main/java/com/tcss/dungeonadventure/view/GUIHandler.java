package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Main;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

public class GUIHandler extends Application {

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

    @Override
    public void start(final Stage theStage) throws IOException {

        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(HOME_FXML_PATH));

        this.myScene = new Scene(fxmlLoader.load(),
                WINDOW_DIMENSION.getWidth(),
                WINDOW_DIMENSION.getHeight());

        this.myScene.setOnKeyPressed(this::handleKeyPress);

        theStage.setTitle(WINDOW_TITLE);
        theStage.setScene(this.myScene);
        theStage.show();

        Layouts.HOME.setNode(lookup("homePane"));
        Layouts.ADVENTURING.setNode(lookup("adventuringPane"));
        Layouts.MENU.setNode(lookup("pausePane"));

        Layouts.swapLayout(Layouts.HOME);
        new HomeGUI(this);

    }

    public void startNewGame(final String thePlayerName, final Hero theHero) {
        DungeonAdventure.getInstance().startNewGame(thePlayerName, theHero);
        new AdventuringGUI(this);
        Layouts.swapLayout(Layouts.ADVENTURING);

    }

    private void handleKeyPress(final KeyEvent theEvent) {
        if (Layouts.getCurrentLayout() != Layouts.ADVENTURING) {
            return;
        }

        switch (theEvent.getCode()) {
            case UP, W -> DungeonAdventure.getInstance().movePlayer(Directions.Cardinal.NORTH);
            case DOWN, S -> DungeonAdventure.getInstance().movePlayer(Directions.Cardinal.SOUTH);
            case LEFT, A -> DungeonAdventure.getInstance().movePlayer(Directions.Cardinal.WEST);
            case RIGHT, D -> DungeonAdventure.getInstance().movePlayer(Directions.Cardinal.EAST);
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
        MENU;


        /**
         * The current layout.
         */
        private static Layouts CURRENT_LAYOUT;

        /**
         * The layout node stored within each enum.
         */
        private Node myLayoutNode;

        /**
         * Swaps between layouts.
         *
         * @param theLayout The layout to swap to.
         */
        private static void swapLayout(final Layouts theLayout) {
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
