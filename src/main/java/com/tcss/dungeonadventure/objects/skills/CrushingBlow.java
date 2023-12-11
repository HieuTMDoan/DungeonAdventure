package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class CrushingBlow extends Skill {

    /**
     * The minimum damage.
     */
    private static final int DEFAULT_MIN_DAMAGE = 75;

    /**
     * The maximum damage.
     */
    private static final int DEFAULT_MAX_DAMAGE = 175;

    /**
     * The chance of success.
     */
    private static final double DEFAULT_SUCCESS_CHANCE = 0.4;


    public CrushingBlow() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        if (theTarget != null && Math.random() <= DEFAULT_SUCCESS_CHANCE) {
            final int damage =
                    Helper.getRandomIntBetween(DEFAULT_MIN_DAMAGE, DEFAULT_MAX_DAMAGE);
            theTarget.changeHealth(-damage);

            System.out.println(
                    theTarget.getClass().getSimpleName() + " takes " + damage
                            + " points of damage from Crushing Blow!");
            return true;
        } else {
            System.out.println("Crushing Blow failed to hit the target.");
            return false;
        }
    }
}



