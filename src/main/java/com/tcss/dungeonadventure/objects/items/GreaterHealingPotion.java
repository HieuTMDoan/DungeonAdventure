package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


/**
 * An upgraded {@link HealingPotion} to restore more health.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public class GreaterHealingPotion extends Item {

    /**
     * The amount healed when used.
     */
    private static final int HEAL_AMOUNT = 60;


    /**
     * Constructs a new Greater Healing Potion.
     */
    public GreaterHealingPotion() {
        super(TileChars.Items.GREATER_HEALING_POTION, ItemTypes.CONSUMABLE);
    }

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
     * @return A string describing the healing potion,
     * including the amount healed and consumption details.
     */
    @Override
    public String getDescription() {
        return "Heals " + HEAL_AMOUNT + ". Consumed upon use.";
    }
}
