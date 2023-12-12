package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.Dungeon;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.VisualComponent;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import com.tcss.dungeonadventure.objects.tiles.PitTile;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents the GUI of the end screen when the game is over.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class EndGameGUI extends GUILayout {


    private VBox myDefeatPanel;
    private VBox myVictoryPanel;

    /**
     * The title label, either "Game Over" or "Victory".
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
        super(theGUI);
        locateNodes();
        attachEvents();
    }


    private void locateNodes() {
        this.myStatsLabel = (Label) lookup("EGstatsLabel");
        this.myTitleLabel = (Label) lookup("EGtitleLabel");
        this.myMainMenuLabel = (Label) lookup("EGmainMenuLabel");

        this.myDefeatedByChar = (Label) lookup("EGdefeatedChar");
        this.myDefeatedByName = (Label) lookup("EGdefeatedName");
        this.myDefeatedByHealth = (Label) lookup("EGdefeatedHealth");

        this.myDefeatPanel = (VBox) lookup("EGdefeatPanel");
        this.myVictoryPanel = (VBox) lookup("EGvictoryPanel");
    }


    private void attachEvents() {
        this.myMainMenuLabel.setOnMouseClicked(e ->
                GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HOME)
        );
    }

    void show(final boolean theVictory) {
        myTitleLabel.setText(theVictory ? "Victory!" : "Game Over");


        myVictoryPanel.setVisible(theVictory);
        myDefeatPanel.setVisible(!theVictory);


        final long exploredRooms =
                Arrays.stream(DungeonAdventure.getInstance().getDiscoveredRooms()).
                flatMap(Arrays::stream).
                filter(Objects::nonNull).
                count();

        final Player player = DungeonAdventure.getInstance().getPlayer();
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
                player.getStat(Player.Fields.MOVES),
                exploredRooms,
                Dungeon.MAZE_SIZE.height * Dungeon.MAZE_SIZE.width,
                player.getStat(Player.Fields.MISSED_ATTACKS),
                player.getStat(Player.Fields.DAMAGE_DEALT),
                player.getStat(Player.Fields.MONSTERS_ENCOUNTERED),
                player.getStat(Player.Fields.MONSTERS_DEFEATED),
                player.getStat(Player.Fields.ITEMS_USED),
                player.getStat(Player.Fields.ITEMS_COLLECTED)));

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
