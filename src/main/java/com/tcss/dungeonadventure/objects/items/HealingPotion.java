package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;


public class HealingPotion extends Item {

    /**
     * The amount healed when used.
     */
    private static final int HEAL_AMOUNT = 35;

    public HealingPotion() {
        super(TileChars.Items.HEALING_POTION, ItemTypes.CONSUMABLE);
    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        if (theTarget != null) {
            theTarget.changeHealth(HEAL_AMOUNT);
        }
    }

    @Override
    public String getDescription() {
        return "Heals " + HEAL_AMOUNT + ". Consumed upon use.";
    }


    @Override
    public String getTileColor() {
        return "blue";
    }
}
