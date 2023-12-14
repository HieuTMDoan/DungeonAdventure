package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.objects.DungeonCharacter;


/**
 * The skill of the Priestess class to deal heal the player.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class Heal extends Skill {

    /**
     * The amount to heal.
     */
    private static final int HEAL_AMOUNT = 50;

    @Override
    public void activateSkill(final DungeonCharacter theSource,
                              final DungeonCharacter theTarget) {

        final int healAmount = HEAL_AMOUNT;
        theSource.changeHealth(healAmount);

        PCS.firePropertyChanged(PCS.COMBAT_LOG, "Healed for " + healAmount + ".");

    }

    @Override
    public String getDescription() {
        return "Heals the player for " + HEAL_AMOUNT + " health points.";
    }
}