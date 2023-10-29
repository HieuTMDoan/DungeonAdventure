package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class HealingPotion extends Item {

    public HealingPotion() {
        super(TileChars.Items.HEALING_POTION);
    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Check if the target character is a valid DungeonCharacter
        if (theTarget != null) {
            // Define the healing amount for the potion (you can modify this range)
            int healAmount = 10; // Example: You can adjust this value as needed

            // Get the current health and max health of the target character
            int currentHealth = theTarget.getHealth();
            int maxHealth = theTarget.getMaxHealthPoints();

            // Calculate the new health after healing
            int newHealth = currentHealth + healAmount;

            // Make sure the new health doesn't exceed the max health
            if (newHealth > maxHealth) {
                newHealth = maxHealth;
            }

            // Set the new health for the target character
            theTarget.setHealth(newHealth);

            // Print a message to inform the player about the healing action
            System.out.println(theTarget.getName() + " used a Healing Potion and healed for " + healAmount + " hit points.");
        }
    }
}
