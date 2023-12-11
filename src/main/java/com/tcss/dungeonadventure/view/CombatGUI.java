package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.items.SkillOrb;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents the GUI of the combat screen whenever the player interacts with a monster.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class CombatGUI implements PropertyChangeListener {

    /**
     * The GUI Handler.
     */
    private final GUIHandler myGUI;

    /**
     * The scroll pane for the logger.
     */
    private ScrollPane myLogScroll;

    /**
     * The combat log box.
     */
    private VBox myMessageBox;

    private Label myDescriptionLabel;

    /* Player Action Nodes */

    /**
     * The button to fire the Attack command.
     */
    private Label myAttackButton;

    /**
     * The button to fire the Skill command.
     */
    private Label myUseSkillButton;

    /**
     * The button to fire the Flee command.
     */
    private Label myFleeButton;

    /**
     * The label of the players' health.
     */
    private Label myPlayerHealthLabel;

    /**
     * The label of the enemies' name.
     */
    private Label myEnemyLabel;
    /**
     * The label of the enemies' health.
     */
    private Label myEnemyHealthLabel;


    public CombatGUI(final GUIHandler theGUIHandler) {
        PCS.addPropertyListener(this);
        this.myGUI = theGUIHandler;

        locateNodes();
        attachEvents();
    }


    /**
     * Using a node ID, you can access nodes in the Combat screen's FXML by ID.
     *
     * @param theNodeID The ID of the node to access.
     * @return The looked-up node, or null if it isn't found.
     */
    Node lookup(final String theNodeID) {
        return this.myGUI.lookup(theNodeID);
    }

    private void locateNodes() {
        this.myLogScroll = (ScrollPane) lookup("combatScrollPane");
        this.myDescriptionLabel = (Label) lookup("combatDescriptionLabel");

        myPlayerHealthLabel = (Label) lookup("combatPlayerHealthLabel");
        myEnemyLabel = (Label) lookup("combatEnemyLabel");
        myEnemyHealthLabel = (Label) lookup("combatEnemyHealthLabel");

        myMessageBox = (VBox) lookup("combatMessageBox");

        myAttackButton = (Label) lookup("combatAttackButton");
        myUseSkillButton = (Label) lookup("combatUseSkillButton");
        myFleeButton = (Label) lookup("combatFleeButton");

    }

    private void attachEvents() {
        myMessageBox.heightProperty().addListener((observable, oldValue, newValue)
                -> myLogScroll.setVvalue((Double) newValue));

        myAttackButton.setOnMouseEntered(e -> {
            final Hero hero = DungeonAdventure.getInstance().getPlayer().getPlayerHero();
            myDescriptionLabel.setText(
                    ("Attack:\nSwing your weapon, dealing %s - %s damage to the enemy. "
                            + "Has a %s%% chance of hitting").
                            formatted(hero.getMinDamage(), hero.getMaxDamage(), hero.getAccuracy() * 100));
        });
        myAttackButton.setOnMouseExited(e -> myDescriptionLabel.setText(""));
        myAttackButton.setOnMouseClicked(e ->
                DungeonAdventure.getInstance().
                        doCombatAction(DungeonAdventure.CombatActions.ATTACK));

        myUseSkillButton.setOnMouseEntered(e -> {
            final Player hero = DungeonAdventure.getInstance().getPlayer();
            final Integer skillCount = hero.getInventory().get(new SkillOrb());

            myDescriptionLabel.setText("Skill: %s\n%s\n\nSkill Orbs remaining: %s".
                    formatted(
                            Helper.camelToSpaced(hero.getPlayerHero().getSkill().getClass().getSimpleName()),
                            hero.getPlayerHero().getSkill().getDescription(),
                            skillCount == null ? 0 : skillCount));
        });

        myUseSkillButton.setOnMouseExited(e -> myDescriptionLabel.setText(""));
        myUseSkillButton.setOnMouseClicked(e ->
                DungeonAdventure.getInstance().
                        doCombatAction(DungeonAdventure.CombatActions.USE_SKILL));


        myFleeButton.setOnMouseExited(e -> myDescriptionLabel.setText(""));
        myFleeButton.setOnMouseEntered(e ->
                myDescriptionLabel.setText(("Flee:\nFlee from combat! %s chance of success, "
                        + "but if failed, your turn will be skipped.").
                        formatted(DungeonAdventure.FLEE_CHANCE)));

        myFleeButton.setOnMouseClicked(e ->
                DungeonAdventure.getInstance().
                        doCombatAction(DungeonAdventure.CombatActions.FLEE));


    }

    private void syncCombat(final Monster theMonster) {
        myEnemyHealthLabel.setText(
                String.format("Health: %s/%s",
                        theMonster.getHealth(), theMonster.getMaxHealth()));

        myEnemyLabel.setStyle("-fx-text-fill: " + theMonster.getTileColor() + ";");
        myEnemyLabel.setText(String.valueOf(theMonster.getDisplayChar()));

        final Hero player = DungeonAdventure.getInstance().getPlayer().getPlayerHero();
        myPlayerHealthLabel.setText(
                String.format("Health: %s/%s", player.getHealth(), player.getMaxHealth()));
    }

    void startCombat(final Monster theMonster) {
        myMessageBox.getChildren().clear();
        syncCombat(theMonster);

    }

    /**
     * Displays a message in the message box, along with a time-stamp.
     *
     * @param theMessage The message to display.
     */
    private void log(final String theMessage) {
        final String time = new SimpleDateFormat("hh:mm:ss").
                format(Calendar.getInstance().getTime());

        final Label label = new Label(String.format("[%s] %s", time, theMessage));
        label.setWrapText(true);
        label.setStyle("-fx-font-fill: white; -fx-font-size: 16;");
        myMessageBox.getChildren().add(label);
    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case BEGIN_COMBAT -> {
                myAttackButton.setVisible(true);
                myUseSkillButton.setVisible(true);
                myFleeButton.setVisible(true);
            }
            case SYNC_COMBAT -> syncCombat((Monster) theEvent.getNewValue());
            case END_COMBAT -> GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.ADVENTURING);
            case COMBAT_LOG -> log((String) theEvent.getNewValue());
            case TOGGLE_COMBAT_LOCK -> {
                final boolean canDoAction = (boolean) theEvent.getNewValue();
                myAttackButton.setVisible(canDoAction);
                myUseSkillButton.setVisible(canDoAction);
                myFleeButton.setVisible(canDoAction);
            }
        }
    }
}
