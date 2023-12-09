package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.VisualComponent;

import java.io.Serializable;
import java.util.Objects;

public abstract class Item extends VisualComponent implements Serializable {

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
     * The type of the item.
     */
    private final ItemTypes myItemType;

    public Item(final char theDisplayChar, final ItemTypes theItemType) {
        super(theDisplayChar);
        this.myItemType = theItemType;
    }

    @Override
    public String getDescription() {
        return "Item: " + getClass().getSimpleName();
    }

    public ItemTypes getItemType() {
        return this.myItemType;
    }

    public abstract void useItem(DungeonCharacter theTarget);

    // Add a copy method to create a copy of the Item
    public abstract Item copy();


    /**
     * The Item equals method only compares class.
     *
     * @param theOther The other object to compare.
     * @return
     */
    @Override
    public boolean equals(final Object theOther) {
        if (this == theOther) {
            return true;
        }
        return theOther != null && getClass() == theOther.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getDisplayChar());
    }
}
