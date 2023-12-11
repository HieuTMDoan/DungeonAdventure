package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.VisualComponent;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.PitTile;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents the GUI of the end screen when the game is over.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class EndGameGUI {

    /**
     * The GUI handler.
     */
    private final GUIHandler myGUI;


    /**
     * The title label, either "Game Over" or "Victory"
     */
    private Label myTitleLabel;

    /**
     * The label to return to the main menu.
     */
    private Label myMainMenuLabel;

    /**
     * The label to display all stats.
     */
    private Label myStatsLabel;

    /**
     * The label of the object that defeated the player.
     */
    private Label myDefeatedByChar;

    /**
     * The name of the object that defeated the player.
     */
    private Label myDefeatedByName;

    /**
     * The health of the object that defeated the player.
     */
    private Label myDefeatedByHealth;


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

        this.myDefeatedByChar = (Label) lookup("EGdefeatedChar");
        this.myDefeatedByName = (Label) lookup("EGdefeatedName");
        this.myDefeatedByHealth = (Label) lookup("EGdefeatedHealth");
    }


    private void attachEvents() {
        this.myMainMenuLabel.setOnMouseClicked(e ->
                GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HOME)
        );
    }

    void show(final boolean theVictory) {
        myTitleLabel.setText(theVictory ? "Victory!" : "Game Over");

        final long exploredRooms =
                Arrays.stream(DungeonAdventure.getInstance().getDiscoveredRooms()).
                flatMap(Arrays::stream).
                filter(Objects::nonNull).
                count();


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
                exploredRooms,
                Dungeon.MAZE_SIZE.height * Dungeon.MAZE_SIZE.width,
                Player.Stats.MISSED_ATTACKS.getCounter(),
                Player.Stats.DAMAGE_DEALT.getCounter(),
                Player.Stats.MONSTERS_ENCOUNTERED.getCounter(),
                Player.Stats.MONSTERS_DEFEATED.getCounter(),
                Player.Stats.ITEMS_USED.getCounter(),
                Player.Stats.ITEMS_COLLECTED.getCounter()));

        final Object lastDamageSource =
                DungeonAdventure.getInstance().getPlayer().
                        getPlayerHero().getLastDamageSource();

        if (lastDamageSource instanceof final VisualComponent v) {
            myDefeatedByChar.setStyle("-fx-text-fill: " + v.getTileColor() + ";");
            myDefeatedByChar.setText(String.valueOf(v.getDisplayChar()));
        }

        if (lastDamageSource instanceof Monster m) {
            myDefeatedByName.setText(m.getName());
            myDefeatedByHealth.setText(
                    String.format("Health: %s/%s", m.getHealth(), m.getMaxHealth()));
        } else if (lastDamageSource instanceof PitTile) {
            myDefeatedByName.setText("Pit");
            myDefeatedByHealth.setText("Really? A pit?");
        }


    }


}
