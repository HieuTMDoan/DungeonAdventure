package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CombatGUI implements PropertyChangeListener {

    private final GUIHandler myGUI;

    /* Player Action Nodes */
    private Label myAttackButton;
    private Label myUseSkillButton;
    private Label myUseItemButton;
    private Label myFleeButton;


    private Label myPlayerHealthLabel;
    private Label myEnemyLabel;
    private Label myEnemyHealthLabel;

    private VBox myMessageBox;



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
        myPlayerHealthLabel = (Label) lookup("combatPlayerHealthLabel");
        myEnemyLabel = (Label) lookup("combatEnemyLabel");
        myEnemyHealthLabel = (Label) lookup("combatEnemyHealthLabel");

        myMessageBox = (VBox) lookup("combatMessageBox");

        myAttackButton = (Label) lookup("combatAttackButton");
        myUseSkillButton = (Label) lookup("combatUseSkillButton");
        myUseItemButton = (Label) lookup("combatUseItemButton");
        myFleeButton = (Label) lookup("combatFleeButton");

    }

    private void attachEvents() {
        myAttackButton.setOnMouseClicked(e -> {
            System.out.println("Attack button pressed");
        });
        myUseSkillButton.setOnMouseClicked(e -> {
            System.out.println("Use skill button pressed");
        });
        myUseItemButton.setOnMouseClicked(e -> {
            System.out.println("Use item button pressed");
        });
        myFleeButton.setOnMouseClicked(e -> {
            System.out.println("Flee button pressed");
        });
    }

    private void syncCombat(final Hero thePlayer, final Monster theMonster) {
        myPlayerHealthLabel.setText(String.format("HP: %s/%s", thePlayer.getHealth(), thePlayer.getMaxHealth()));


        myEnemyLabel.setText(String.valueOf(theMonster.getDisplayChar()));
        myEnemyHealthLabel.setText(String.format("HP: %s/%s", theMonster.getHealth(), theMonster.getMaxHealth()));

    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case BEGIN_COMBAT -> {
                final Monster opponent = (Monster) theEvent.getNewValue();

                syncCombat(DungeonAdventure.getInstance().getPlayer().getPlayerHero(), opponent);
            }
            case SYNC_COMBAT -> {
                final DungeonCharacter[] payload = (DungeonCharacter[]) theEvent.getNewValue();
                final Hero player = (Hero) payload[0];
                final Monster opponent = (Monster) payload[1];

                syncCombat(player, opponent);
            }
            case END_COMBAT -> {
                GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.ADVENTURING);
            }
        }
    }
}
