package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class Heal extends Skill {

    /**
     * The amount to heal.
     */
    private static final int HEAL_AMOUNT = 50;

//    TODO: Decide how much to heal
//    private static final int DEFAULT_MIN_HEAL = 75;
//    private static final int DEFAULT_MAX_HEAL = 175;

    public Heal() {

    }

    @Override
    public Integer activateSkill(final DungeonCharacter theTarget) {

        final int healAmount = HEAL_AMOUNT;
        theTarget.changeHealth(healAmount);
        return healAmount;
    }
}