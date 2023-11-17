package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class Heal extends Skill {
    /**
     * The amount to heal.
     */
    private static final int HEAL_AMOUNT = 15;

//    TODO: Decide how much to heal
//    private static final int DEFAULT_MIN_HEAL = 75;
//    private static final int DEFAULT_MAX_HEAL = 175;

    public Heal() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        if (theTarget != null) {
            // Set the new health value for the hero
            theTarget.changeHealth(HEAL_AMOUNT);

            System.out.println(
                    theTarget.getClass().getSimpleName() + " is healed for "
                            + HEAL_AMOUNT + " points.");
            return true;
        } else {
            return false;
        }
    }
}