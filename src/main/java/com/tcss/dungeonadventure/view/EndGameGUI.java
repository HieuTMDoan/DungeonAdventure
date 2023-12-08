package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class EndGameGUI {

    private final GUIHandler myGUI;


    private Label myTitleLabel;
    private Label myMainMenuLabel;
    private Label myStatsLabel;


    /**
     * Initializes a basic pause menu screen with 3 menu options.
     */
    public EndGameGUI(final GUIHandler theGUI) {
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

    private void locateNodes() {
        this.myStatsLabel = (Label) lookup("EGstatsLabel");
        this.myTitleLabel = (Label) lookup("EGtitleLabel");
        this.myMainMenuLabel = (Label) lookup("EGmainMenuLabel");
    }


    private void attachEvents() {
        this.myMainMenuLabel.setOnMouseClicked(e -> GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HOME));

    }

    void show(final boolean theVictory) {
        myTitleLabel.setText(theVictory ? "Victory!" : "Game Over");

        myStatsLabel.setText(String.format("""
                Moves: %s
                Explored Rooms: %s/%s
                Attacks Missed: %s
                Damage Dealt: %s
                Monsters Encountered: %s
                Monsters Defeated: %s
                Items Used: %s
                Items Collected: %s
                """,
                Player.Stats.MOVES.getCounter(),
                Player.Stats.EXPLORED_ROOMS.getCounter(),
                Dungeon.MAZE_SIZE.height * Dungeon.MAZE_SIZE.width,
                Player.Stats.MISSED_ATTACKS.getCounter(),
                Player.Stats.DAMAGE_DEALT.getCounter(),
                Player.Stats.MONSTERS_ENCOUNTERED.getCounter(),
                Player.Stats.MONSTERS_DEFEATED.getCounter(),
                Player.Stats.ITEMS_USED.getCounter(),
                Player.Stats.ITEMS_COLLECTED.getCounter()));
    }


}
