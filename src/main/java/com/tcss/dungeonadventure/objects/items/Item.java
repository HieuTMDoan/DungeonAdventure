package com.tcss.dungeonadventure.objects.items;


import com.tcss.dungeonadventure.objects.DungeonCharacter;

import java.util.Objects;

public abstract class Item {

    private final char myDisplayChar;

    public Item(final char theDisplayChar) {
        this.myDisplayChar = theDisplayChar;
    }

    public char getDisplayChar() {
        return myDisplayChar;
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
