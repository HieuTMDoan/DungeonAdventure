package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class Heal extends Skill {

    /**
     * The amount to heal.
     */
    private static final int HEAL_AMOUNT = 50;

    public Heal() {

    }

    @Override
    public Integer activateSkill(final DungeonCharacter theSource,
                                 final DungeonCharacter theTarget) {

        final int healAmount = HEAL_AMOUNT;
        theSource.changeHealth(healAmount);

        PCS.firePropertyChanged(PCS.COMBAT_LOG, "Healed for " + healAmount + ".");

        return healAmount;
    }

    @Override
    public String getDescription() {
        return "Heals the player for " + HEAL_AMOUNT + " health points.";
    }
}