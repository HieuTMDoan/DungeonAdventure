package com.tcss.dungeonadventure.objects.items;

import com.tcss.dungeonadventure.objects.DungeonCharacter;
import com.tcss.dungeonadventure.objects.VisualComponent;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract class representing an item in the game.
 * Items have a display character and can be of different types,
 * such as pillars or consumables.
 * Subclasses must implement the useItem method to define the item's behavior when used.
 *
 * @see VisualComponent
 * @see Serializable
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class Item extends VisualComponent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum ItemTypes {

        /**
         * This item type is for all pillars.
         */
        PILLAR,

        /**
         * This item type is for all items that can be used, such as potions.
         */
        CONSUMABLE,

    }

    /**
     * The type of the item.
     */
    private final ItemTypes myItemType;


    /**
     * Constructs a new item with the specified display character and item type.
     *
     * @param theDisplayChar The character used to display the item.
     * @param theItemType    The type of the item.
     */
    public Item(final char theDisplayChar, final ItemTypes theItemType) {
        super(theDisplayChar);
        this.myItemType = theItemType;
    }

    /**
     * Gets the description of the item.
     *
     * @return The description of the item, or a default description if not set.
     */
    @Override
    public String getDescription() {
        return "Item: " + getClass().getSimpleName();
    }

    /**
     * Gets the type of the item.
     *
     * @return The type of the item.
     */
    public ItemTypes getItemType() {
        return this.myItemType;
    }

    /**
     * Abstract method to be implemented by subclasses to define the item's behavior when used.
     *
     * @param theTarget The target of the item's use, typically a dungeon character.
     */
    public abstract void useItem(DungeonCharacter theTarget);


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

    /**
     * Generates a hash code for the item based on its display character.
     *
     * @return The hash code for the item.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getDisplayChar());
    }
}
