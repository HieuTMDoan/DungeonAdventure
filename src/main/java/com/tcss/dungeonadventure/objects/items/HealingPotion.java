package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


public class HealingPotion extends Item {

    /**
     * The amount healed when used.
     */
    private static final int HEAL_AMOUNT = 35;

    /**
     * Constructs a new healing potion item.
     */
    public HealingPotion() {
        super(TileChars.Items.HEALING_POTION, ItemTypes.CONSUMABLE);
    }

    /**
     * Uses the healing potion to restore health to the specified target dungeon character.
     *
     * @param theTarget The dungeon character to whom the healing potion is applied.
     */
    @Override
    public void useItem(final DungeonCharacter theTarget) {
        if (theTarget != null) {
            theTarget.changeHealth(HEAL_AMOUNT);
        }
    }

    /**
     * Gets the tile color associated with the healing potion.
     *
     * @return The color of the healing potion's tile, represented as a string.
     */
    @Override
    public String getTileColor() {
        return "blue";
    }


    /**
     * Gets the description of the healing potion.
     *
     * @return A string describing the healing potion, including the amount healed and consumption details.
     */
    @Override
    public String getDescription() {
        return "Heals " + HEAL_AMOUNT + ". Consumed upon use.";
    }
}


