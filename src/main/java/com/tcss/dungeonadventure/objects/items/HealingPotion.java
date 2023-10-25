package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.TileChars;

public class HealingPotion extends Item {

    public HealingPotion() {
        super(TileChars.Items.HEALING_POTION);

    }

    @Override
    public void useItem(final DungeonCharacter theTarget) {
        // TODO: Heal target
    }




}
