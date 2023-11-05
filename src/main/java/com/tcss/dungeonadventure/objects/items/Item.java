package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.VisualComponent;

import java.util.Objects;

public abstract class Item implements VisualComponent {

    public enum ItemTypes {

        /**
         * This item type is for all items that can be used, such as potions.
         */
        CONSUMABLE,

        /**
         * This item type is for all pillars.
         */
        PILLAR

    }

    /**
     * The display character of the item.
     */
    private final char myDisplayChar;

    /**
     * The type of the item.
     */
    private final ItemTypes myItemType;

    public Item(final char theDisplayChar, final ItemTypes theItemType) {
        this.myDisplayChar = theDisplayChar;
        this.myItemType = theItemType;
    }

    @Override
    public char getDisplayChar() {
        return myDisplayChar;
    }

    @Override
    public String getDescription() {
        return "Item: " + getClass().getSimpleName();
    }

    public ItemTypes getItemType() {
        return this.myItemType;
    }

    public abstract void useItem(DungeonCharacter theTarget);

    @Override
    public boolean equals(final Object theOther) {
        if (this == theOther) {
            return true;
        }
        if (theOther == null || getClass() != theOther.getClass()) {
            return false;
        }
        final Item item = (Item) theOther;
        return myDisplayChar == item.myDisplayChar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDisplayChar);
    }
}
