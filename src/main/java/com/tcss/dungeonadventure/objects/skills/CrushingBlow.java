package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class CrushingBlow extends Skill {

    private static final int DEFAULT_MIN_DAMAGE = 75;
    private static final int DEFAULT_MAX_DAMAGE = 175;
    private static final double DEFAULT_SUCCESS_CHANCE = 0.4;


    public CrushingBlow() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        if (theTarget != null && Math.random() <= DEFAULT_SUCCESS_CHANCE) {
            int damage = DEFAULT_MIN_DAMAGE + (int) (Math.random() * (DEFAULT_MAX_DAMAGE - DEFAULT_MIN_DAMAGE + 1));
            int newHealth = Math.max(theTarget.getHealth() - damage, 0);
            theTarget.setHealth(newHealth);

            System.out.println(theTarget.getClass().getSimpleName() + " takes " + damage + " points of damage from Crushing Blow!");
            return true;
        } else {
            System.out.println("Crushing Blow failed to hit the target.");
            return false;
        }
    }
}



