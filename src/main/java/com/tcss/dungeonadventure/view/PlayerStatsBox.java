package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlayerStatsBox extends VBox {

    private final Label nameLabel;
    private final Label healthLabel;
    private final Label damageLabel;
    private final Label attackSpeedLabel;
    private final Label accuracyLabel;

    public PlayerStatsBox(final Hero thePlayerHero) {
        this.setAlignment(Pos.CENTER_LEFT);

        nameLabel = new Label("Name: " + thePlayerHero.getName());
        healthLabel = new Label("Health: " + thePlayerHero.getHealth()
                + "/" + thePlayerHero.getMaxHealth());
        damageLabel = new Label("Damage Range: "
                + thePlayerHero.getMinDamage() + " - " + thePlayerHero.getMaxDamage());
        attackSpeedLabel = new Label("Attack Speed: " + thePlayerHero.getAttackSpeed());
        accuracyLabel = new Label("Accuracy: " + thePlayerHero.getAccuracy());

        this.getChildren().addAll(nameLabel, healthLabel, damageLabel,
                attackSpeedLabel, accuracyLabel);
    }

    // Update the displayed stats with the given Hero
    public void updateStats(final Hero theHero) {
        nameLabel.setText("Name: "
                + DungeonAdventure.getInstance().getPlayer().getPlayerName());
        healthLabel.setText("Health: "
                + theHero.getHealth() + "/" + theHero.getMaxHealth());
        damageLabel.setText("Damage Range: "
                + theHero.getMinDamage() + " - " + theHero.getMaxDamage());
        attackSpeedLabel.setText("Attack Speed: " + theHero.getAttackSpeed());
        accuracyLabel.setText("Accuracy: " + theHero.getAccuracy());
    }
}
