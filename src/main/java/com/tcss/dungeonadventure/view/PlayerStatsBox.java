package com.tcss.dungeonadventure.view;

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

    public PlayerStatsBox(final Hero playerHero) {
        this.setAlignment(Pos.CENTER_LEFT);

        nameLabel = new Label("Name: " + playerHero.getName());
        healthLabel = new Label("Health: " + playerHero.getHealth() + "/" + playerHero.getMaxHealthPoints());
        damageLabel = new Label("Damage Range: " + playerHero.getMinDamage() + " - " + playerHero.getMaxDamage());
        attackSpeedLabel = new Label("Attack Speed: " + playerHero.getAttackSpeed());
        accuracyLabel = new Label("Accuracy: " + playerHero.getAccuracy());

        this.getChildren().addAll(nameLabel, healthLabel, damageLabel, attackSpeedLabel, accuracyLabel);
    }

    // Update the displayed stats with the given Hero
    public void updateStats(final Hero theHero) {
        nameLabel.setText("Name: " + theHero.getName());
        healthLabel.setText("Health: " + theHero.getHealth() + "/" + theHero.getMaxHealthPoints());
        damageLabel.setText("Damage Range: "
                + theHero.getMinDamage() + " - " + theHero.getMaxDamage());
        attackSpeedLabel.setText("Attack Speed: " + theHero.getAttackSpeed());
        accuracyLabel.setText("Accuracy: " + theHero.getAccuracy());
    }
}
