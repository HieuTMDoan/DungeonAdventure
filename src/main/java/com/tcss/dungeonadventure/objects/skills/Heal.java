package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class Heal extends Skill {
    private static final int HEAL_AMOUNT = 15;

//    TODO: Decide how much to heal
//    private static final int DEFAULT_MIN_HEAL = 75;
//    private static final int DEFAULT_MAX_HEAL = 175;

    public Heal() {

    }

    @Override
    public boolean activateSkill(final DungeonCharacter theTarget) {
        if (theTarget != null) {
            int currentHealth = theTarget.getHealth();
            int maxHealth = theTarget.getHealth(); // Assuming you have a method to get max health

            // Ensure that the healing doesn't exceed the maximum health
            int newHealth = Math.min(maxHealth, currentHealth + HEAL_AMOUNT);

            // Set the new health value for the hero
            theTarget.setHealth(newHealth);

            System.out.println(theTarget.getClass().getSimpleName() + " is healed for " + HEAL_AMOUNT + " points.");
            return true;
        } else {
            return false;
        }
    }
}