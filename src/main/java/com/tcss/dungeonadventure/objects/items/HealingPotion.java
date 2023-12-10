package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


public class HealingPotion extends Item {

    private static final int HEAL_AMOUNT = 10;

    public HealingPotion() {
        super(TileChars.Items.HEALING_POTION, ItemTypes.CONSUMABLE);
    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // Check if the target character is a valid DungeonCharacter
        if (theTarget != null) {
            // Set the new health for the target character
            theTarget.changeHealth(HEAL_AMOUNT);

            // Print a message to inform the player about the healing action
            System.out.println(theTarget.getName()
                    + " used a Healing Potion and healed for "
                    + HEAL_AMOUNT + " hit points.");
        }
    }
    @Override
    public Item copy() {
        // Create a new instance of HealingPotion with the same display character and item type
        return new HealingPotion();
    }

    @Override
    public String getTileColor() {
        return "blue";
    }
}
