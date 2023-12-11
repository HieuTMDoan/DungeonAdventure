package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.VisualComponent;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class Item extends VisualComponent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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


    /**
     * The description of the item.
     */
    private String myDescription;

    public Item(final char theDisplayChar, final ItemTypes theItemType) {
        super(theDisplayChar);
        this.myItemType = theItemType;
    }

    @Override
    public String getDescription() {
        // Use the stored description or the default if not set
        return (myDescription != null) ? myDescription : "Item: " + getClass().getSimpleName();
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
     * @return True if the other object is the same item class, false otherwise.
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
